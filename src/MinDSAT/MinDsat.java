package MinDSAT;

import Graph.Graph;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class MinDsat {
    private Graph g;
    private int minDegree; // Stores the index of the V w min degree
    private LinkedList<Integer> uncolored;// Stores the uncolored V's
    private int[] saturationDeg;

    public MinDsat(Graph g) {
        this.g = g;
        g.startTimer();
        this.uncolored = new LinkedList<>();
        this.saturationDeg = new int[g.getV()];
        initUncolored();// Set all V's to uncolored
        setMinDegree();// Determine which V has the minDegree
        solve();// Starts the main algorithm
    }

    private void solve() {
        int nextV;
        nextV = color(minDegree); // Color V with min degree with the first color
        for (int i = 1; i < g.getV(); i++) {
            nextV = color(nextV);
        }

    }

    //  Colors the vertex, also returns the next vertex to be colored
    private int color(int vertex) {
        LinkedList<Integer> valid = getValidAdjacentsOf(vertex);
        int numOfValidNeighbors = valid.size();
        int nextVertex;
        nextVertex = -99;
        if (valid.size() > 0) {
            //  First coloring and vertex has one degree
            if (uncolored.size() == g.getV() && getDegree(vertex) == 1) {
                g.color(vertex, getValidColorFor(vertex));
                uncolored.remove(indexOf(vertex));
                nextVertex = g.getAdjListArray()[vertex].get(0);

                //  Increment saturation
                saturationDeg[g.getAdjListArray()[vertex].get(0)]++;
            }
            //  First coloring and vertex degree >= 1
            else if (uncolored.size() == g.getV()) {
                g.color(vertex, 1);
                uncolored.remove(indexOf(vertex));
                valid = getValidAdjacentsOf(vertex); // We recompute valid everytime a vertex is colored and we choose from valid

                //  Increment saturation
                for (int i = 0; i < numOfValidNeighbors; i++) {
                    int currNeighbor = valid.get(i);
                    saturationDeg[currNeighbor]++;
                }

                nextVertex = getNextVertex(valid);
            }
            //  Not the first coloring, follow rules
            else {
                g.color(vertex, getValidColorFor(vertex));
                uncolored.remove(indexOf(vertex));
                valid = getValidAdjacentsOf(vertex);

                //  Increment saturation
                for (int i = 0; i < numOfValidNeighbors; i++) {
                    int currNeighbor = valid.get(i);
                    saturationDeg[currNeighbor]++;
                }

                nextVertex = getNextVertex(valid);
            }
        } else if (valid.size() == 0) { // If all neighbors are uncolored, or vertex has no edges
            //  First coloring and vertex has degree 0
            if (uncolored.size() == g.getV() && getDegree(vertex) == 0) {
                g.color(vertex, 1);

                uncolored.remove(indexOf(vertex));
                nextVertex = randomFromUncolored();
            }
            //  Last vertex to color and degree >= 1
            else if (uncolored.size() == 1 && getDegree(vertex) >= 1) {
                g.color(vertex, getValidColorFor(vertex));

                uncolored.remove(indexOf(vertex));
            }
            //  Last vertex to color and degree == 0
            else if (uncolored.size() == 1 && getDegree(vertex) == 0) {
                g.color(vertex, 1);

                uncolored.remove(indexOf(vertex));
            }
            //  Not yet finished coloring and degree == 0
            else if (uncolored.size() > 1 && getDegree(vertex) == 0) {
                g.color(vertex, 1);

                uncolored.remove(indexOf(vertex));
                nextVertex = randomFromUncolored();
            }
            //  All adjacent to vertex are colored
            else {
                g.color(vertex, getValidColorFor(vertex));

                uncolored.remove(indexOf(vertex));
                nextVertex = randomFromUncolored();//getNextVertex(valid,vertex);
            }
        }
        return nextVertex;

    }

    private int randomFromUncolored() {
        return uncolored.get(ThreadLocalRandom.current().nextInt(0, uncolored.size()));
    }

    private int getNextVertex(LinkedList<Integer> valid) {
        int nextVertex = getMinSatOrMinDegree(valid);
        return nextVertex;
    }

    private int random(LinkedList<Integer> valid) {
        return ThreadLocalRandom.current().nextInt(0, valid.size());
    }


    /**
     * @param validAdjacents
     * @return -1 if all equal saturation
     */
    private int getMinSatOrMinDegree(LinkedList<Integer> validAdjacents) {
        //  if degree 0 return random uncolored vertex
        //  if valid 0 return random uncolored vertex
        //  if valid 1 return 0th valid
        //  Please check this part of MinDsat
        int nextVertex = 1011;
        int numOfValidNeighbors = validAdjacents.size();
        int ithValidNeighborWithMinSAT = validAdjacents.get(0);
        int ithValidNeighborWithMinDegree = validAdjacents.get(0);

        if (validAdjacents.size() == 1)
            return validAdjacents.get(0);

        int ctr = 1;
        int ctr2 = 1;
        for (int i = 1; i < numOfValidNeighbors; i++) {
            int ithValidNeighbor = validAdjacents.get(i);
            if (saturationDeg[ithValidNeighbor] < saturationDeg[ithValidNeighborWithMinSAT]) {
                ithValidNeighborWithMinSAT = ithValidNeighbor;
            }
            if (saturationDeg[ithValidNeighbor] == saturationDeg[ithValidNeighborWithMinSAT]) {
                ctr++;
            }
            if (getDegree(ithValidNeighbor) < getDegree(ithValidNeighborWithMinDegree)) {
                ithValidNeighborWithMinDegree = ithValidNeighbor;
            }

            if (getDegree(ithValidNeighbor) == getDegree(ithValidNeighborWithMinDegree)) {
                ctr2++;
            }
        }
        //  If equal saturation, try mindegree
        if (ctr == numOfValidNeighbors){
            //  check first if all degrees equal
            if (ctr2 == numOfValidNeighbors)    // if all equal degrees random
                nextVertex = randomFromUncolored();
            else    // else choose vertex with min degree
                nextVertex = ithValidNeighborWithMinDegree;
        }
        else {
            nextVertex = ithValidNeighborWithMinSAT;
        }
        return nextVertex;
    }

    private int getValidColorFor(int vertex) {
        LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
        int countAdjacents = g.getAdjListArray()[vertex].size();
        int color;
        int pass;
        pass = 0;
        for (int i = 1; i > 0; i++) {
            color = i;
            pass = 0;
            for (int adjacentVertex :
                    adjacentsOfVertex) {
                if (!isColorUnused(color, adjacentVertex))
                    pass = pass + 1;
                if (pass == countAdjacents)
                    return color;
            }
        }
        return -1;
    }

    private boolean isColorUnused(int color, int vertex) {
        if (color == g.getvColors()[vertex]) return true;
        else return false;
    }

    /**
     * @param vertex
     * @return Uncolored adjacents of vertex
     */
    private LinkedList<Integer> getValidAdjacentsOf(int vertex) {
        LinkedList<Integer> valid = new LinkedList<>();
        LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
        for (int adjacentVertex :
                adjacentsOfVertex) {
            if (!isColored(adjacentVertex))
                valid.add(adjacentVertex);
        }
        return valid;
    }

    private int getDegree(int vertex) {
        return g.getAdjListArray()[vertex].size();
    }

    private boolean isColored(int vertex) {
        if (g.getvColors()[vertex] == 0)
            return false;
        return true;
    }

    private int indexOf(int vertex) {
        for (int i = 0; i < uncolored.size(); i++) {
            if (uncolored.get(i) == vertex)
                return i;
        }
        return -1;
    }

    private void initUncolored() {
        for (int i = 0; i < g.getV(); i++) {
            uncolored.add(i);
        }
    }

    private void setMinDegree() {
        int min;
        min = 0;
        for (int i = 1; i < g.getV(); i++) {
            if (g.getAdjListArray()[i].size() < g.getAdjListArray()[min].size())
                min = i;
        }
        minDegree = min;
    }
}

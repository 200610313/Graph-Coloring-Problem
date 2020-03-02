package Brelaz;

import Graph.Graph;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class DSAT {
    private Graph g;
    private int maxDegree;
    private int[] saturationDeg;
    private int[] uncoloredDeg;
    private LinkedList<Integer> uncolored;// Stores the uncolored V's
    private int V;

    public DSAT(Graph g) {
        this.g = g;
        g.startTimer();
        this.V = g.getV();
        this.saturationDeg = new int[V];
        this.uncoloredDeg = new int[V];
        this.uncolored = new LinkedList<>();
        initialize();
    }

    private void initialize() {
        maxDegree = 0;
        saturationDeg[0] = 0;
        uncoloredDeg[0] = getDegree(0);
        uncolored.add(0);
        for (int i = 1; i < V; i++) {
            saturationDeg[i] = 0;
            uncoloredDeg[i] = getDegree(i);
            uncolored.add(i);
            if (getDegree(maxDegree) < uncoloredDeg[i])
                maxDegree = i;
        }
        start();
    }

    private void start() {
        int vPredecessor = -1;  //Previous vertex colored
        int v = -1; //Vertex to be colored
        int j = 1;
        while (!uncolored.isEmpty()) {
            LinkedList<Integer> validAdjacents = null;
            if (vPredecessor == -1) {
                // If first coloring
                v = maxDegree;
            } else {
                // Decide which to color v based on vPredecessor
                validAdjacents = getValidAdjacentsOf(vPredecessor);
                v = maxSATorMaxUncolored(vPredecessor, validAdjacents);
            }
            validAdjacents = getValidAdjacentsOf(v);

            int vALen = validAdjacents.size();
            j = getValidColorFor(v);
            for (int i = 0; i < vALen; i++) {
                int ithNeighborOfv = validAdjacents.get(i);
                if (hasNeighborColored(ithNeighborOfv, j) == false) {
                    saturationDeg[ithNeighborOfv]++;
                }
                uncoloredDeg[ithNeighborOfv]--;
            }

            uncolored.remove(indexOf(v));
            g.color(v, j);
            vPredecessor = v;
        }

    }

    private boolean hasNeighborColored(int vertex, int color) {
        int numOfNeighbors = g.getAdjListArray()[vertex].size();
        for (int i = 0; i < numOfNeighbors; i++) {
            // if color is the same as the color of the neighbor's, return false
            int neighborOfVertex = g.getAdjListArray()[vertex].get(i);
            if (color == g.getvColors()[neighborOfVertex])
                return true;
        }
        return false;
    }

    private int maxSATorMaxUncolored(int vPredecessor, LinkedList<Integer> validAdjacents) {
        //  if degree 0 return random uncolored vertex
        //  if valid 0 return random uncolored vertex
        //  if valid 1 return 0th valid
        //  Please check this part of MinDsat
        int nextVertex = -1;

        if (validAdjacents.size() == 0 || getDegree(vPredecessor) == 0) {
            nextVertex = randomFromUncolored();

        } else if (validAdjacents.size() == 1) {
            nextVertex = validAdjacents.get(0);
        } else {
            int numOfValidNeighbors = validAdjacents.size();
            int ithValidNeighborWithMaxSAT = validAdjacents.get(0);
            int ithValidNeighborWithMaxUncolored = validAdjacents.get(0);

            int ctr = 1;
            int ctr2 = 1;
            for (int i = 1; i < numOfValidNeighbors; i++) {
                int ithValidNeighbor = validAdjacents.get(i);
                if (saturationDeg[ithValidNeighbor] > saturationDeg[ithValidNeighborWithMaxSAT]) {
                    ithValidNeighborWithMaxSAT = ithValidNeighbor;
                }
                if (saturationDeg[ithValidNeighbor] == saturationDeg[ithValidNeighborWithMaxSAT]) {
                    ctr++;
                }
                if (uncoloredDeg[ithValidNeighbor] > uncoloredDeg[ithValidNeighborWithMaxUncolored]) {
                    ithValidNeighborWithMaxUncolored = ithValidNeighbor;
                }
                if (uncoloredDeg[ithValidNeighbor] == uncoloredDeg[ithValidNeighborWithMaxUncolored]) {
                    ctr2++;
                }
            }
            //  Check if equal saturation
            if (ctr == numOfValidNeighbors) {
                nextVertex = ithValidNeighborWithMaxUncolored;
            } else {
                if (ctr2 == numOfValidNeighbors)
                    nextVertex = validAdjacents.get(0);
                else//  Least degree
                    nextVertex = ithValidNeighborWithMaxSAT;
            }
            //  If equal saturation, try mindegree
            if (ctr == numOfValidNeighbors){
                //  check first if all degrees equal
                if (ctr2 == numOfValidNeighbors)    // if all equal degrees random
                    nextVertex = randomFromUncolored();
                else    // else choose vertex with min degree
                    nextVertex = ithValidNeighborWithMaxUncolored;
            }
            else {
                nextVertex = ithValidNeighborWithMaxSAT;
            }
            return nextVertex;
        }
        return nextVertex;
    }

    private int randomFromUncolored() {
        return uncolored.get(ThreadLocalRandom.current().nextInt(0, uncolored.size()));
    }

    private boolean isColorUnused(int color, int vertex) {
        if (color != g.getvColors()[vertex]) return true;
        else return false;
    }

    private int getValidColorFor(int vertex) {
        if (getDegree(vertex)!=0){
            LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
            int countAdjacents = g.getAdjListArray()[vertex].size();
            int color;
            int pass;
            pass = 0;
            for (int i = 1; i > 0; i++) {
                pass = 0;
                color = i;
                for (int adjacentVertex :
                        adjacentsOfVertex) {
                    if (isColorUnused(color, adjacentVertex) == true)
                        pass = pass + 1;
                    if (pass == countAdjacents)
                        return color;
                }
            }
        }
        return 1;
    }

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

    private int getDegree(int vertex) {
        return g.getAdjListArray()[vertex].size();
    }
}
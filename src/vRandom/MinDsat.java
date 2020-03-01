package vRandom;
import customgraph.Graph;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class MinDsat {
    private Graph g;
    private int minDegree; // Stores the index of the V w min degree
    private LinkedList<Integer> uncolored;// Stores the uncolored V's
    private int currColor;

    public MinDsat(Graph g)
    {
        this.g = g;
        this.uncolored = new LinkedList<>();
        this.currColor = 1;// Coloring starts with color 1
        initUncolored();// Set all V's to uncolored
        getMinDegree();// Determine which V has the minDegree
        solve();// Starts the main algorithm
    }

    private void solve() {
        int nextV;
        nextV = color(minDegree); // Color V with min degree with the first color
        for (int i = 1; i < g.getV(); i++) {
            nextV = color(nextV);
        }
        System.out.println("Hello word");
    }

    //  Colors the vertex, also returns the next vertex to be colored
    private int color(int vertex) {
        LinkedList<Integer> valid = getValidAdjacentsOf(vertex);
        int nextVertex;
        nextVertex = -99;
        if (valid.size() > 0){
            //  First coloring and vertex has one degree
            if (uncolored.size() == g.getV() && getDegree(vertex) == 1){
                g.color(vertex, getValidColorFor(vertex));
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                nextVertex = g.getAdjListArray()[vertex].get(0);
            }
            //  First coloring and vertex degree >= 1
            else if (uncolored.size() == g.getV()){
                g.color(vertex, 1);
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                valid = getValidAdjacentsOf(vertex); // We recompute valid everytime a vertex is colored
                nextVertex = getNextVertex(valid, vertex);
            }
            //  Not the first coloring
            else {
                g.color(vertex, getValidColorFor(vertex));
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                valid = getValidAdjacentsOf(vertex);
                nextVertex = getNextVertex(valid,vertex);
            }
        }

        else if (valid.size() == 0){
            //  First coloring and vertex has degree 0
            if (uncolored.size() == g.getV() && getDegree(vertex) == 0){
                g.color(vertex, 1);
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                nextVertex = randomFromUncolored();
            }
            //  Last vertex to color and degree >= 1
            else if (uncolored.size() == 1 && getDegree(vertex) >= 1){
                g.color(vertex, getValidColorFor(vertex));
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
            }
            //  Last vertex to color and degree == 0
            else if (uncolored.size() == 1 && getDegree(vertex) == 0){
                g.color(vertex, 1);
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
            }
            //  Not yet finished coloring and degree == 0
            else if(uncolored.size() > 1 && getDegree(vertex) == 0){
                g.color(vertex, 1);
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                nextVertex = randomFromUncolored();
            }
            //  All adjacent to vertex are colored
            else{
                g.color(vertex, getValidColorFor(vertex));
                System.out.println("Colored "+ vertex);
                uncolored.remove(indexOf(vertex));
                valid = getValidAdjacentsOf(vertex);
                nextVertex = randomFromUncolored();//getNextVertex(valid,vertex);
            }
        }
        return nextVertex;

    }

    private int randomFromUncolored() {
        return uncolored.get(ThreadLocalRandom.current().nextInt(0, uncolored.size()));
    }

    private int getNextVertex(LinkedList<Integer> valid, int vertex) {
        int nextVertex = getMinSat(valid);
        if (nextVertex != -1){ //   If not tie in SAT
            nextVertex = valid.get(nextVertex);
        }
        else if (nextVertex == -1) { // If tie in SAT
            nextVertex = getMinDegree(valid);
            if (nextVertex != -1)   // If not tie in degree
                nextVertex = valid.get(nextVertex);
            else if (nextVertex == -1) // if tie in degree
                nextVertex = random(valid); //  Go random
        }
        return nextVertex;
    }

    private int random(LinkedList<Integer> valid) {
        return ThreadLocalRandom.current().nextInt(0, valid.size());
    }

    /**
     *
     * @param valid
     * @return -1 if equal degree
     */
    private int getMinDegree(LinkedList<Integer> valid) {
        if (valid.size()==1){
            return 0;
        }
        else if (valid.size()==2){
            if (getDegree(valid.get(0))==getDegree(valid.get(1)))
                return -1;
            else if (getDegree(valid.get(0))>getDegree(valid.get(1)))
                return 1;
            else
                return 0;
        }
        else {
            int ithValidWithMinDegree = 0;
            for (int i = 1; i < valid.size(); i++) {
                if (getDegree(valid.get(ithValidWithMinDegree)) >= getDegree(valid.get(i)))
                {
                    ithValidWithMinDegree = i;
                }
            }
            if (ithValidWithMinDegree == valid.size()-1)
                return -1;
            else
                return ithValidWithMinDegree;
        }
    }
    /**
     *
     * @param valid
     * @return -1 if all equal saturation
     */
    private int getMinSat(LinkedList<Integer> valid) {
        LinkedList<Integer> saturationOfValid = new LinkedList<>();
        int nextV;
        nextV = 0;
        //  Determine saturation of each adjacent
        for (int validVertex:
                valid) {
            int saturationValue = countColoredAdjacentsOf(validVertex);
            saturationOfValid.add(saturationValue);
        }
        //  Get adjacent with MIN saturation
        if (saturationOfValid.size()==1){
            return 0;
        }
        else if (saturationOfValid.size()==2){
            if (saturationOfValid.get(0) == saturationOfValid.get(1))
                return -1;
            else if (saturationOfValid.get(0) > saturationOfValid.get(1))
                return 1;
            else
                return 0;
        }
        else {
            int ithValidWithMinDegree = 0;
            for (int i = 1; i < valid.size(); i++) {
                if (saturationOfValid.get(ithValidWithMinDegree) >= saturationOfValid.get(i))
                {
                    ithValidWithMinDegree = i;
                    /*flag2 = true;*/
                }
            }
            if (ithValidWithMinDegree == valid.size()-1)
                return -1;
            else
                return ithValidWithMinDegree;
        }
    }

    private int getValidColorFor(int vertex) {
        LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
        int countAdjacents = g.getAdjListArray()[vertex].size();
        int color;
        int pass;
        pass = 0;
        for (int i = 1; i > 0; i++) {
            color = i;
            for (int adjacentVertex:
                 adjacentsOfVertex) {
                if (!isColorUnused(color,adjacentVertex))
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
     *
     * @param vertex
     * @return Uncolored adjacents of vertex
     */
    private LinkedList<Integer> getValidAdjacentsOf(int vertex) {
        LinkedList<Integer> valid = new LinkedList<>();
        LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
        for (int adjacentVertex:
             adjacentsOfVertex) {
            if (!isColored(adjacentVertex))
                valid.add(adjacentVertex);
        }
        return valid;
    }

    private int getDegree(int vertex){
         return g.getAdjListArray()[vertex].size();
    }

    private int countColoredAdjacentsOf(int vertex) {
        LinkedList<Integer> adjacentsOfVertex = g.getAdjListArray()[vertex];
        int saturation = 0;
        for (int adjacentVertex:
             adjacentsOfVertex) {
            if (isColored(adjacentVertex))
                saturation = saturation + 1;
        }
        return saturation;
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

    private void getMinDegree() {
        int min;
        min = 0;
        for (int i = 1; i < g.getV(); i++) {
            if (g.getAdjListArray()[i].size() < g.getAdjListArray()[min].size())
                min = i;
        }
        minDegree = min;
    }
}

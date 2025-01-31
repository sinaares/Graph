import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DirectedGraph implements DirectedGraphInteface {

    // HashMap to store vertices in the graph, where the key is the vertex name, and the value is the Vertex object.
    protected HashMap<String, Vertex> vertices;


    // Default constructor initializes the vertices HashMap.
    public DirectedGraph()
    {
        this.vertices = new HashMap<>();
    }

    // Returns the number of vertices in the graph.
    public int vertexSize()
    {
        return this.vertices.size();
    }

    // Checks if the graph contains a vertex with the given name.
    public boolean contansVertex(String name)
    {
        return this.vertices.containsKey(name);
    }

    public boolean containsVertex(String name)
    {
        return this.vertices.containsKey(name);
    }

    // Adds a new vertex to the graph with the given name.
    public void addVertex(String name)
    {
        Vertex vertex = new Vertex();
        vertices.put(name , vertex);
    }

    // Adds a new vertex to the graph with the given name and additional value.
    public void addVertex(String name , String line)
    {
        Vertex vertex = new Vertex(line);
        vertices.put(name , vertex);
    }

    // Removes a vertex and its edges from the graph based on the given name.
    public void remove(String name)
    {
        Vertex vtx = vertices.get(name);
        ArrayList<String> keys = new ArrayList<>(vtx.neighbors.keySet());

        for (String key : keys){
            Vertex neighborVertex = vertices.get(key);
            neighborVertex.neighbors.remove(name);
        }
    }

    // Returns the number of edges in the graph.
    public int numEdges()
    {
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int count = 0;

        for (String key : keys)
        {
            Vertex vertex = vertices.get(key);
            count = count + vertex.neighbors.size();
        }
        return count / 2;
    }

    // Checks if there is an edge between two vertices with the given names.

    public boolean containsEdge(String name1 , String name2)
    {
        Vertex vertex1 = vertices.get(name1);
        Vertex vertex2 = vertices.get(name2);
        if(vertex1 == null || vertex2 == null || vertex1.neighbors.containsKey(name2))
        {
            return false;
        }
        return true;

    }
    // Adds a directed edge between two vertices with the given names and a specified weight.
    public void addEdge(String name1 , String name2 , int value){
        Vertex vertex1 = vertices.get(name1);
        Vertex vertex2 = vertices.get(name2);

        if(vertex1 == null || vertex2 == null || vertex1.neighbors.containsKey(name2)){
            return;
        }
        vertex1.neighbors.put(name2 , value);
        vertex2.neighbors.put(name1 , value);
    }

    // Adds a vertex if not present, then adds an edge between source and destination vertices with a specified weight.
    public void add(String source, String destination, int value) {
        addVertex(source);
        addVertex(destination);

        addEdge(source, destination, value);
    }

    // Removes the directed edge between two vertices with the given names.
    public  void removeEdge(String name1 , String name2){

        Vertex vertex1 = vertices.get(name1);
        Vertex vertex2 = vertices.get(name2);

        if(vertex1 == null || vertex2 == null || !vertex1.neighbors.containsKey(name2)){
            return;
        }
        vertex1.neighbors.remove(name2);
        vertex2.neighbors.remove(name1);
    }

    // Displays the graph with vertex names and their associated neighbors and weights.
    public void displayMap(){
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());

        for (String key : keys){
            String str = key + "=>\n";
            Vertex vertex = vertices.get(key);
            ArrayList<String> vtxnbrs = new ArrayList<>(vertex.neighbors.keySet());
            for (String neighbor : vtxnbrs){
                str = str + "\t" + neighbor + "\t";
                if (neighbor.length()<16)
                    str = str + "\t";
                if (neighbor.length()<8)
                    str = str + "\t";
                str = str + vertex.neighbors.get(neighbor) + "\n";
            }
            System.out.println(str);
        }
    }

    // Displays the list of vertices in the graph.
    public void displayStation(){
        ArrayList<String> keys = new ArrayList<>(vertices.keySet());
        int i = 1;
        for (String key : keys){
            System.out.println(i + ". " + key);
            i++;
        }
    }

    // Checks if there is a path between two vertices with the given names, considering processed vertices to avoid cycles.
    public boolean hasPath(String name1 , String name2 , HashMap<String , Boolean> processed){
        if(containsEdge(name1 , name2)){
            return true;
        }
        processed.put(name1 , true);
        Vertex vertex = vertices.get(name1);
        ArrayList<String> nbrs = new ArrayList<>(vertex.neighbors.keySet());
        for (String neighbor : nbrs){
            if (!processed.containsKey(neighbor)){
                if (hasPath(neighbor , name2 , processed)){
                    return true;
                }
            }
        }
        return false;
    }


    // Implements Dijkstra's algorithm to find the minimum distances from a source vertex to all other vertices.
    public HashMap<String, Integer> dijkstra(String source) {
        // Initialize distances with infinity and the source vertex with distance 0
        HashMap<String, Integer> distances = new HashMap<>();
        for (String vertex : vertices.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        // Priority queue to get the minimum distance vertex efficiently
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            String currentVertex = priorityQueue.poll();

            Vertex current = vertices.get(currentVertex);

            for (Map.Entry<String, Integer> entry : current.neighbors.entrySet()) {
                String neighbor = entry.getKey();
                int edgeWeight = entry.getValue();
                int newDistance = distances.get(currentVertex) + edgeWeight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    priorityQueue.add(neighbor);
                }
            }
        }

        return distances;
    }

    // Displays the minimum path between two vertices based on the minimum time using Dijkstra's algorithm.
    public void showMinPathDijkstra(String source, String destination) {
        HashMap<String, Integer> distances = dijkstra(source);
        int station = 0;
        int edgeWeight = 0;

        if (!distances.containsKey(destination) || distances.get(destination) == Integer.MAX_VALUE) {
            System.out.println("No path found between " + source + " and " + destination);
            return;
        }

        LinkedList<String> result = new LinkedList<>();
        String currentVertex = destination;


        while (!currentVertex.equals(source)) {
            result.addFirst(currentVertex);
            vertices.get(currentVertex).getAdditionalValue();
            station++;
            for (Map.Entry<String, Integer> entry : vertices.get(currentVertex).neighbors.entrySet()) {
                String neighbor = entry.getKey();
                edgeWeight = entry.getValue();
                //station = station + edgeWeight;
                int neighborDistance = distances.get(neighbor);
                if (distances.get(currentVertex) == neighborDistance + edgeWeight) {
                    currentVertex = neighbor;
                    station = station + edgeWeight;
                    break;
                }
            }
        }


        System.out.println("Origin station : " + source);
        System.out.println("Destination : " + destination);
        System.out.println("Preferetion : Minimum path using time");
        System.out.println("\n" + "Suggestion" + "\n");
        System.out.print(source + " -> ");
        System.out.println( String.join(" -> ", result));

        /*
        System.out.println("Line : " + vertices.get(result.get(0)).getAdditionalValue());
        Boolean flag = true;
        System.out.print(source + " --> ");
        for (int i = 0; i < result.size() - 1; i++) {
            if(vertices.get(result.get(i)).getAdditionalValue().equals(vertices.get(result.get(i + 1)).getAdditionalValue())){
                System.out.print(result.get(i) + " --> ");
            }
            else {
                System.out.println();
                if (flag){
                    System.out.println("Line : " + vertices.get(result.get(i + 1)).getAdditionalValue());
                    flag = false;
                }
                System.out.print(result.get(i) + " --> ");
            }
            flag = true;
        }
        System.out.print(destination);

         */


        System.out.println("\nminimum time is  : " + (int) station / 60 + " min");
        System.out.println("\n");

    }


    // Finds and displays the minimum path between two vertices based on the minimum number of stations using BFS.
    public void findMinPath(String source, String destination) {
        if (!contansVertex(source) || !contansVertex(destination)) {
            System.out.println("Invalid source or destination vertex");
            return;
        }

        // Initialize data structures for BFS
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<String, String> pred = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        // Enqueue the source vertex
        queue.add(source);
        visited.put(source, true);
        pred.put(source, null);

        // Perform BFS
        while (!queue.isEmpty()) {
            String current = queue.poll();

            // Check if the destination is reached
            if (current.equals(destination)) {
                // Print the path
                printPath(pred, source, destination);
                return;
            }

            // Explore neighbors
            Vertex currentVertex = vertices.get(current);
            for (String neighbor : currentVertex.neighbors.keySet()) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, true);
                    pred.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // If destination is not reachable from the source
        System.out.println("No path found between " + source + " and " + destination);
    }

    // Helper function to print the reconstructed path based on predecessors.
    private void printPath(HashMap<String, String> pred, String source, String destination) {
        LinkedList<String> path = new LinkedList<>();
        String current = destination;
        int count = 0;

        // Reconstruct the path
        while (current != null) {
            path.addFirst(current);
            current = pred.get(current);
            count++;
        }

        // Print the path
        System.out.println("Origin station : " + source);
        System.out.println("Destination : " + destination);
        System.out.println("Preferetion : Minimum path using station");
        System.out.println("\n" + "Suggestion" + "\n");
        System.out.println("Minimum Path: \n" + String.join(" -> ", path));

        /*
        System.out.println("Line : " + vertices.get(path.get(0)).getAdditionalValue());
        Boolean flag = true;
        System.out.print(source + " --> ");
        for (int i = 0; i < path.size() - 1; i++) {
            if(vertices.get(path.get(i)).getAdditionalValue().equals(vertices.get(path.get(i + 1)).getAdditionalValue())){
                System.out.print(path.get(i) + " --> ");
            }
            else {
                System.out.println();
                if (flag){
                    System.out.println("Line : " + vertices.get(path.get(i + 1)).getAdditionalValue());
                    flag = false;
                }
                System.out.print(path.get(i) + " --> ");
            }
            flag = true;
        }
        System.out.print(destination);

         */


        System.out.println("\nminimum station is : " + count + " Stations" );
        System.out.println("\n");

    }

    // Inner class representing a Pair of vertex name, path so far, minimum distance, and minimum time.
    private class Pair
    {
        String vertexName;
        String pathSoFar;
        int minDistance;

        int distance;
        int minTime;



        Pair(String vertexName, int distance) {
            this.vertexName = vertexName;
            this.distance = distance;
        }
        public Pair(String vertexName, String pathSoFar, int minDistance, int minTime) {
            this.vertexName = vertexName;
            this.pathSoFar = pathSoFar;
            this.minDistance = minDistance;
            this.minTime = minTime;
        }
    }

    // Finds and returns the minimum time path between two vertices using a modified version of Dijkstra's algorithm.
    public String Get_Minimum_Time(String source, String destination)
    {
        int min = Integer.MAX_VALUE;
        String result = "";
        HashMap<String, Boolean> processed = new HashMap<>();
        LinkedList<Pair> stack = new LinkedList<>();

        // create a new pair
        Pair startPair = new Pair(source , source + " " , 0 , 0);

        // put the new pair in queue
        stack.addFirst(startPair);

        // while queue is not empty keep on doing the work
        while (!stack.isEmpty()) {

            // remove a pair from queue
            Pair removedpair = stack.removeFirst();

            if (processed.containsKey(removedpair.vertexName))
            {
                continue;
            }

            // processed put
            processed.put(removedpair.vertexName, true);

            //if there exists a direct edge b/w removed pair and destination vertex
            if (removedpair.vertexName.equals(destination))
            {
                int temp = removedpair.minTime;
                if(temp<min) {
                    result = removedpair.pathSoFar;
                    min = temp;
                }
                continue;
            }

            Vertex removedVertex = vertices.get(removedpair.vertexName);
            ArrayList<String> neighbors = new ArrayList<>(removedVertex.neighbors.keySet());

            for (String neighbor : neighbors)
            {
                // process only unprocessed nbrs
                if (!processed.containsKey(neighbor)) {

                    // make a new pair of nbr and put in queue

                    Pair newPair = new Pair(neighbor , removedpair.pathSoFar + neighbor + " " , 0 ,(removedpair.minTime + removedVertex.neighbors.get(neighbor)) );
                    stack.addFirst(newPair);
                }
            }
        }
        Double minutes = (double) min / 60;
        result = result + "\n" + Double.toString(minutes) + "  min";

        result = "Origin Station : " + source + "\n" + "Destination : " + destination + "preferection : Minimun time" + "\n"
                +"\n" + "Suggestion" + "\n\n" + result;
        return result;
    }


    // Finds and returns the minimum distance path between two vertices using a modified version of Dijkstra's algorithm.
    public String Get_Minimum_Distance(String sorce, String destination)
    {
        int min = Integer.MAX_VALUE;
        //int time = 0;
        String result = "";
        HashMap<String, Boolean> processed = new HashMap<>();
        LinkedList<Pair> stack = new LinkedList<>();

        // create a new pair
        Pair startPair = new Pair(sorce , sorce + " " , 0 , 0);

        // put the new pair in stack
        stack.addFirst(startPair);

        // while stack is not empty keep on doing the work
        while (!stack.isEmpty())
        {
            // remove a pair from stack
            Pair removedPair = stack.removeFirst();

            if (processed.containsKey(removedPair.vertexName))
            {
                continue;
            }

            // processed put
            processed.put(removedPair.vertexName, true);

            //if there exists a direct edge b/w removed pair and destination vertex
            if (removedPair.vertexName.equals(destination))
            {
                int temp = removedPair.minDistance;
                if(temp<min) {
                    result = removedPair.pathSoFar;
                    min = temp;
                }
                continue;
            }

            Vertex removedVertex = vertices.get(removedPair.vertexName);
            ArrayList<String> neighbors = new ArrayList<>(removedVertex.neighbors.keySet());

            for(String neighbor : neighbors)
            {
                // process only unprocessed nbrs
                if (!processed.containsKey(neighbor)) {

                    // make a new pair of nbr and put in queue
                    Pair newPair = new Pair(neighbor , removedPair.pathSoFar + neighbor + "  " ,removedPair.minDistance + removedVertex.neighbors.get(neighbor) , 0 );
                    stack.addFirst(newPair);
                }
            }
        }
        result = result + Integer.toString(min);
        return result;
    }

    // Recursive helper function to find and store alternative paths between two vertices.
    public void showAlternativePathsprint(String source, String destination) {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String, Boolean> processed = new HashMap<>();
        int pathsToFind = countAlternativePaths(source , destination); // Set the desired number of paths

        showalternativePathsHelper(source, destination, new LinkedList<>(), result, processed, pathsToFind);

        // Display or process the alternative paths
        for (String path : result) {
            System.out.println(path);
        }
    }

    // Recursive helper function to count the number of alternative paths between two vertices.
    private void showalternativePathsHelper(String current, String destination, LinkedList<String> path, ArrayList<String> result, HashMap<String, Boolean> processed, int pathsToFind) {
        processed.put(current, true);
        path.addLast(current);

        if (current.equals(destination) && result.size() < pathsToFind) {
            // Found a path from src to dst
            result.add(String.join(" -> ", path));
        } else {
            Vertex currentVertex = vertices.get(current);
            ArrayList<String> neghbors = new ArrayList<>(currentVertex.neighbors.keySet());

            for (String neighbor : neghbors) {
                if (!processed.containsKey(neighbor) && result.size() < pathsToFind) {
                    showalternativePathsHelper(neighbor, destination, path, result, processed, pathsToFind);
                }
            }
        }

        // Backtrack
        processed.remove(current);
        path.removeLast();
    }

    // Counts and returns the number of alternative paths between two vertices using a recursive helper function.
    public int countAlternativePaths(String source, String destination) {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String, Boolean> processed = new HashMap<>();

        // Start the recursive process to find alternative paths
        countAlternativePathsHelper(source, destination, new LinkedList<>(), result, processed);

        // Return the count of alternative paths, up to a maximum of ten
        return Math.min(result.size(), 10);
    }

    // Recursive helper function to count the number of alternative paths between two vertices.
    private void countAlternativePathsHelper(String current, String destination, LinkedList<String> path, ArrayList<String> result, HashMap<String, Boolean> processed) {
        processed.put(current, true);
        path.addLast(current);

        if (current.equals(destination) && result.size() < 10) {
            // Found a path from src to dst
            result.add(String.join(" -> ", path));
        } else {
            Vertex currentVertex = vertices.get(current);
            ArrayList<String> neighbors = new ArrayList<>(currentVertex.neighbors.keySet());

            for (String neighbor : neighbors) {
                if (!processed.containsKey(neighbor) && result.size() < 10) {
                    countAlternativePathsHelper(neighbor, destination, path, result, processed);
                }
            }
        }

        // Backtrack
        processed.remove(current);
        path.removeLast();
    }




    // Displays the minimum path between two vertices based on the minimum number of stations.
    public void showMinPath(String source, String destination) {
        LinkedList<String> result = new LinkedList<>();
        HashMap<String, Boolean> processed = new HashMap<>();

        showMinPathHelper(source, destination, new LinkedList<>(), result, processed);

        // Display or process the minimum path
        if (!result.isEmpty()) {
            System.out.println("Minimum Path: " + String.join(" -> ", result));
        } else {
            System.out.println("No path found between " + source + " and " + destination);
        }
    }

    // Recursive helper function to find and store the minimum path between two vertices based on the minimum number of stations.
    private void showMinPathHelper(String current, String destination, LinkedList<String> path, LinkedList<String> result, HashMap<String, Boolean> processed) {
        processed.put(current, true);
        path.addLast(current);

        if (current.equals(destination)) {
            // Found a path from src to dst
            if (result.isEmpty() || path.size() < result.size()) {
                result.clear();
                result.addAll(path);
            }
        } else {
            Vertex currentVertex = vertices.get(current);
            ArrayList<String> neighbors = new ArrayList<>(currentVertex.neighbors.keySet());

            for (String neighbor : neighbors) {
                if (!processed.containsKey(neighbor)) {
                    showMinPathHelper(neighbor, destination, path, result, processed);
                }
            }
        }

        // Backtrack
        processed.remove(current);
        path.removeLast();
    }


}

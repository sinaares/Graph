// File class represents a utility for reading input from a file and performing operations on a DirectedGraph.

import java.io.BufferedReader;
import java.io.FileReader;

public class File {
    // Private field to store the file path.
    private String file;

    // Constructor to initialize the File object with a file path.
    public File(String file) {
        this.file = file;
    }

    // Reads the contents of the file to populate a DirectedGraph with vertices and edges.
    public void read(DirectedGraph graph) {
        String line = null;
        try {
            // Open the file for reading.
            BufferedReader text = new BufferedReader(new FileReader(file));

            // Count the number of lines in the file to determine the size of the data.
            int size = 0;
            while ((line = text.readLine()) != null) {
                size++;
            }

            // Reset the buffered reader to the beginning of the file.
            line = null;
            text = new BufferedReader(new FileReader(file));

            // Create an array to store each line of data.
            String[] list = new String[size - 1];

            // Skip the first line (header) of the file.
            text.readLine();

            // Read and store each line of data in the array.
            int i = 0;
            while ((line = text.readLine()) != null) {
                list[i] = line;
                i++;
            }

            // Add vertices to the graph based on the data.
            for (int j = 0; j < list.length; j++) {
                graph.addVertex(list[j].split(",")[1], list[j].split(",")[5]);
            }

            // Add edges to the graph based on the data.
            for (int j = 0; j < list.length - 1; j++) {
                String[] metro = list[j].split(",");
                String[] metro2 = list[j + 1].split(",");
                if ((Integer.parseInt(metro[3]) + 1) == Integer.parseInt(metro2[3])) {
                    graph.addEdge(metro[1], metro2[1], Integer.parseInt(metro2[2]) - Integer.parseInt(metro[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reads the contents of the file to perform search operations on the DirectedGraph.
    public void search(DirectedGraph graph) {
        String line = null;
        try {
            // Open the file for reading.
            BufferedReader text = new BufferedReader(new FileReader(file));

            // Create an array to store each line of data.
            String[] list = new String[3];

            // Skip the first line (header) of the file.
            text.readLine();

            // Read and process each line of data in the array.
            while ((line = text.readLine()) != null) {
                list = line.split(",");
                if (list[2].equals("0")) {
                    // If the preference is 0, find the minimum path using stations.
                    graph.findMinPath(list[0], list[1]);
                } else {
                    // If the preference is not 0, use Dijkstra's algorithm to find the minimum path using time.
                    graph.showMinPathDijkstra(list[0], list[1]);
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

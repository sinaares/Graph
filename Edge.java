// Edge class represents a weighted edge in a graph, connecting a source vertex to a destination vertex with a specified weight.

public class Edge {
    // Private fields to store the source vertex name, destination vertex name, and weight of the edge.
    private String source;
    private String destination;
    private int weight;

    // Constructor to initialize the Edge with the source vertex, destination vertex, and weight.
    public Edge(String source, String destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Getter method to retrieve the source vertex name.
    public String getSource() {
        return source;
    }

    // Getter method to retrieve the destination vertex name.
    public String getDestination() {
        return destination;
    }

    // Getter method to retrieve the weight of the edge.
    public int getWeight() {
        return weight;
    }

    // Overridden toString method to provide a string representation of the Edge.
    @Override
    public String toString() {
        return source + " -> " + destination + " : " + weight;
    }
}

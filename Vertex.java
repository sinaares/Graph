import java.util.HashMap;

public class Vertex implements GraphVertex{
    // HashMap to store neighbors of the vertex, where the key is the neighbor's name and the value is an associated integer value.
    protected HashMap<String, Integer> neighbors;

    // Additional value associated with the vertex.
    protected String additionalValue;

    // Default constructor initializes the neighbors HashMap.
    public Vertex()
    {
        this.neighbors = new HashMap<>();
    }

    // Constructor with an additional value parameter initializes the neighbors HashMap and sets the additional value.
    public Vertex(String additionalValue)
    {
        this.neighbors = new HashMap<>();
        this.additionalValue = additionalValue;
    }

    // Adds a neighbor with the given name and associated value to the neighbors HashMap.
    public void addNeighbor(String neighborName, int value)
    {
        neighbors.put(neighborName, value);
    }

    // Removes the neighbor with the specified name from the neighbors HashMap.
    public void removeNeighbor(String neighborName)
    {
        neighbors.remove(neighborName);
    }

    // Checks if the vertex has a neighbor with the given name.
    public boolean hasNeighbor(String neighborName)
    {
        return neighbors.containsKey(neighborName);
    }

    // Retrieves the value associated with a specific neighbor by name.
    // Returns -1 if the neighbor is not found.
    public int getNeighborValue(String neighborName)
    {
        return neighbors.getOrDefault(neighborName, -1);
    }

    // Getter method to retrieve the additional value associated with the vertex.
    public String getAdditionalValue()
    {
        return additionalValue;
    }

    // Setter method to set the additional value associated with the vertex.
    public void setAdditionalValue(String additionalValue)
    {
        this.additionalValue = additionalValue;
    }
}

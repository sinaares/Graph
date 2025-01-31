public interface GraphVertex {

    void addNeighbor(String neighborName, int value);

    void removeNeighbor(String neighborName);

    boolean hasNeighbor(String neighborName);

    int getNeighborValue(String neighborName);

    String getAdditionalValue();

    void setAdditionalValue(String additionalValue);
}

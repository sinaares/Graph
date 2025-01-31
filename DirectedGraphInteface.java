import java.util.HashMap;
public interface DirectedGraphInteface {

    int vertexSize();

    boolean containsVertex(String name);

    void addVertex(String name);

    void addVertex(String name, String line);

    void remove(String name);

    int numEdges();

    boolean containsEdge(String name1, String name2);

    void addEdge(String name1, String name2, int value);

    void add(String source, String destination, int value);

    void removeEdge(String name1, String name2);

    void displayMap();

    void displayStation();

    boolean hasPath(String name1, String name2, HashMap<String, Boolean> processed);

    String Get_Minimum_Time(String source, String destination);

    String Get_Minimum_Distance(String source, String destination);

    void showAlternativePathsprint(String source, String destination);

    void showMinPath(String source, String destination);

    void findMinPath(String source, String destination);

    void showMinPathDijkstra(String source, String destination);
}

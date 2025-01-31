public class Main {

    public static void main(String[] args) {

        Long startTime = System.nanoTime();
        File metro = new File("C:\\Users\\sinaa\\OneDrive\\Desktop\\sina\\sem 3\\2201 data\\lab\\assignment-2\\Paris_RER_Metro_v2.csv");
        File search = new File("C:\\Users\\sinaa\\OneDrive\\Desktop\\sina\\sem 3\\2201 data\\lab\\assignment-2\\Test100.csv");
        DirectedGraph graph = new DirectedGraph();
        metro.read(graph);
        search.search(graph);
        //graph.displayMap();
        Long finishTime = System.nanoTime();
        float avgtime = (finishTime - startTime)/ 1000000000;
        System.out.print("the average time is : " + avgtime);
    }

}
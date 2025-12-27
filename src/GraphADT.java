public interface GraphADT {
    void addProtein(String id, String name);
    void addInteraction(String id1, String id2, double score);

    Protein getProtein(String id);
    int getVertexCount();
    int getEdgeCount();

    boolean isConnected(String id1, String id2);
    void shortestPath(String id1, String id2);
    void printMetrics();

    void bfs(String startId);
    void dfs(String startId);
}
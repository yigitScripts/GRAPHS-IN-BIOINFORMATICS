import java.util.*;

public class Graph implements GraphADT {

    private ArrayList<Edge> edges;

    private HashMap<String, Protein> proteins;
    // Helper adjacency list for performance
    private HashMap<String, List<Edge>> adj;

    public Graph() {
        this.edges = new ArrayList<>();
        this.proteins = new HashMap<>();
        this.adj = new HashMap<>();
    }

    public void addProtein(String id, String name) {
        if (!proteins.containsKey(id)) {
            proteins.put(id, new Protein(id, name));
        }
    }

    public void addInteraction(String id1, String id2, double score) {
        Protein p1 = proteins.get(id1);
        Protein p2 = proteins.get(id2);

        if (p1 != null && p2 != null) {
            Edge e = new Edge(p1, p2, score);
            edges.add(e);

            // Add to helper list
            if (!adj.containsKey(id1)) {
                adj.put(id1, new ArrayList<>());
            }
            adj.get(id1).add(e);
        }
    }

    public Protein getProtein(String id) {
        return proteins.get(id);
    }

    public int getVertexCount() {
        return proteins.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public boolean isConnected(String id1, String id2) {
        List<Edge> list = adj.get(id1);
        if (list != null) {
            for (Edge e : list) {
                if (e.getDestination().getId().equals(id2)) {
                    System.out.println("Interaction found! Score: " + e.getScore());
                    return true;
                }
            }
        }
        System.out.println("No interaction found.");
        return false;
    }

    // Silent check for metrics
    private boolean checkConnection(String id1, String id2) {
        List<Edge> list = adj.get(id1);
        if (list != null) {
            for (Edge e : list) {
                if (e.getDestination().getId().equals(id2)) return true;
            }
        }
        return false;
    }

    // Calculates basic graph metrics
    public void printMetrics() {
        int v = proteins.size();
        int e = edges.size();

        System.out.println("Vertex Count: " + v);
        System.out.println("Edge Count: " + e);

        if (v > 0) {
            double avg = (double) e / v;
            System.out.println("Average Degree: " + String.format("%.2f", avg));
        }

        // Reciprocity Calculation
        int mutual = 0;
        for (Edge edge : edges) {
            String src = edge.getSource().getId();
            String dest = edge.getDestination().getId();

            if (checkConnection(dest, src)) {
                mutual++;
            }
        }
        if (e > 0) {
            double rec = (double) mutual / e;
            System.out.println("Reciprocity: " + String.format("%.4f", rec));
        }

        // Diameter Calculation
        System.out.println("Calculating Diameter (this may take time)...");
        int max = 0;
        int count = 0;

        for (String key : proteins.keySet()) {
            count++;
            if (count % 2000 == 0) System.out.print(".");

            int dist = bfsMax(key);
            if (dist > max) max = dist;
        }
        System.out.println("\nDiameter: " + max);
    }

    private int bfsMax(String start) {
        Queue<String> q = new LinkedList<>();
        HashMap<String, Integer> dist = new HashMap<>();

        q.add(start);
        dist.put(start, 0);
        int max = 0;

        while (!q.isEmpty()) {
            String curr = q.poll();
            int d = dist.get(curr);
            if (d > max) max = d;

            List<Edge> neighbors = adj.get(curr);
            if (neighbors != null) {
                for (Edge edge : neighbors) {
                    String next = edge.getDestination().getId();
                    if (!dist.containsKey(next)) {
                        dist.put(next, d + 1);
                        q.add(next);
                    }
                }
            }
        }
        return max;
    }

    // Most Confident Path using Dijkstra Logic
    public void shortestPath(String startId, String endId) {
        if (!proteins.containsKey(startId) || !proteins.containsKey(endId)) {
            System.out.println("Proteins not found.");
            return;
        }

        HashMap<String, Double> scores = new HashMap<>();
        HashMap<String, String> previous = new HashMap<>();

        for (String k : proteins.keySet()) scores.put(k, 0.0);
        scores.put(startId, 1.0);

        // Simple Comparator for PriorityQueue
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            public int compare(String s1, String s2) {
                if (scores.get(s2) > scores.get(s1)) return 1;
                else return -1;
            }
        });

        pq.add(startId);
        HashSet<String> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (u.equals(endId)) break;

            if (visited.contains(u)) continue;
            visited.add(u);

            List<Edge> neighbors = adj.get(u);
            if (neighbors != null) {
                for (Edge e : neighbors) {
                    String v = e.getDestination().getId();
                    double newScore = scores.get(u) * e.getScore();

                    if (newScore > scores.get(v)) {
                        scores.put(v, newScore);
                        previous.put(v, u);
                        pq.add(v);
                    }
                }
            }
        }

        if (scores.get(endId) == 0.0) {
            System.out.println("Path not found.");
        } else {
            System.out.println("Most confident path score: " + scores.get(endId));
            String temp = endId;
            String route = "";
            while (temp != null) {
                String name = proteins.get(temp).getName();
                if (route.equals("")) route = name;
                else route = name + " -> " + route;

                temp = previous.get(temp);
            }
            System.out.println("Path: " + route);
        }
    }

    public void bfs(String startId) {
        if (!proteins.containsKey(startId)) return;
        Queue<String> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();

        q.add(startId);
        visited.add(startId);
        int count = 0;

        System.out.print("BFS: ");
        while (!q.isEmpty() && count < 30) {
            String u = q.poll();
            System.out.print(proteins.get(u).getName() + " - ");
            count++;

            List<Edge> list = adj.get(u);
            if (list != null) {
                for (Edge e : list) {
                    String v = e.getDestination().getId();
                    if (!visited.contains(v)) {
                        visited.add(v);
                        q.add(v);
                    }
                }
            }
        }
        System.out.println("...");
    }

    public void dfs(String startId) {
        if (!proteins.containsKey(startId)) return;
        HashSet<String> visited = new HashSet<>();
        System.out.print("DFS: ");
        dfsRec(startId, visited, 0);
        System.out.println("...");
    }

    private void dfsRec(String u, HashSet<String> visited, int count) {
        if (count > 30) return;
        visited.add(u);
        System.out.print(proteins.get(u).getName() + " - ");

        List<Edge> list = adj.get(u);
        if (list != null) {
            for (Edge e : list) {
                String v = e.getDestination().getId();
                if (!visited.contains(v)) {
                    dfsRec(v, visited, count + 1);
                }
            }
        }
    }
}
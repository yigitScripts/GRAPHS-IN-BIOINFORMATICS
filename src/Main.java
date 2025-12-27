import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        GraphADT graph = new Graph();
        Scanner scan = new Scanner(System.in);

        // Ensure these files are in your project folder
        String fileInfo = "resources/9606.protein.info.v12.0.txt";
        String fileLinks = "resources/9606.protein.links.v12.0.txt";

        System.out.println("--- STRING Graph Project ---");

        // --- THRESHOLD INPUT ---
        double threshold = 0.0;
        while (true) {
            System.out.print("Threshold (0.0 - 1.0): ");
            try {
                threshold = scan.nextDouble();

                if (threshold < 0.0 || threshold > 1.0) {
                    System.out.println("Range error. Use 0.0 to 1.0.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Not a number. Try again.");
                scan.next(); // clear buffer
            }
        }

        System.out.println("Loading data...");
        long startTime = System.currentTimeMillis();

        try {
            // Load Names into memory first
            HashMap<String, String> nameMap = new HashMap<>();

            BufferedReader br1 = new BufferedReader(new FileReader(fileInfo));
            String line;
            br1.readLine(); // skip header
            while ((line = br1.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    nameMap.put(parts[0], parts[1]);
                }
            }
            br1.close();

            // Read Interactions and add only relevant proteins
            BufferedReader br2 = new BufferedReader(new FileReader(fileLinks));
            br2.readLine(); // skip header
            while ((line = br2.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    int rawScore = Integer.parseInt(parts[2]);
                    double score = rawScore / 1000.0;

                    if (score >= threshold) {
                        String id1 = parts[0];
                        String id2 = parts[1];

                        // If proteins are not in graph, add them now
                        if (graph.getProtein(id1) == null) {
                            String name = nameMap.containsKey(id1) ? nameMap.get(id1) : id1;
                            graph.addProtein(id1, name);
                        }
                        if (graph.getProtein(id2) == null) {
                            String name = nameMap.containsKey(id2) ? nameMap.get(id2) : id2;
                            graph.addProtein(id2, name);
                        }

                        // Add the edge
                        graph.addInteraction(id1, id2, score);
                    }
                }
            }
            br2.close();

        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
            System.out.println("Check .txt files.");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Done. Time: " + (endTime - startTime) + " ms");
        System.out.println("Proteins: " + graph.getVertexCount());
        System.out.println("Edges: " + graph.getEdgeCount());

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Search Protein");
            System.out.println("2. Interaction Check");
            System.out.println("3. Best Path (Confidence)");
            System.out.println("4. Metrics");
            System.out.println("5. BFS / DFS");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice = -1;
            try {
                choice = scan.nextInt();
            } catch (Exception e) {
                scan.next();
            }

            if (choice == 1) {
                System.out.print("Protein ID: ");
                String id = scan.next();
                Protein p = graph.getProtein(id);
                if (p != null) System.out.println("Result: " + p);
                else System.out.println("Not found.");
            }
            else if (choice == 2) {
                System.out.print("ID 1: ");
                String id1 = scan.next();
                System.out.print("ID 2: ");
                String id2 = scan.next();
                graph.isConnected(id1, id2);
            }
            else if (choice == 3) {
                System.out.print("Start ID: ");
                String start = scan.next();
                System.out.print("End ID: ");
                String end = scan.next();
                graph.shortestPath(start, end);
            }
            else if (choice == 4) {
                graph.printMetrics();
            }
            else if (choice == 5) {
                System.out.print("Start ID: ");
                String start = scan.next();
                System.out.println("Running BFS...");
                graph.bfs(start);
                System.out.println("Running DFS...");
                graph.dfs(start);
            }
            else if (choice == 6) {
                System.out.println("Exiting.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scan.close();
    }
}
public class Edge {
    private Protein source;
    private Protein destination;
    private double score;

    public Edge(Protein source, Protein destination, double score) {
        this.source = source;
        this.destination = destination;
        this.score = score;
    }

    public Protein getSource() {
        return source;
    }

    public Protein getDestination() {
        return destination;
    }

    public double getScore() {
        return score;
    }
}
//Induranga Kawishwara - 20200688

public class DirecGraph implements Cloneable{
    private final int num_vertices;
    private final boolean[][] Matrix;

    // Constructor to initialize the graph with num_vertices vertices
    public DirecGraph(int num_vertices) {
        this.num_vertices = num_vertices;
        Matrix = new boolean[num_vertices][num_vertices];
    }

    // The number of edges in a graph may be retrieved using a "getter" method
    public int getNumber_vertices() {
        return num_vertices;
    }

    // Method to set an edge in the graph from vertex r to vertex l
    public void setEdge(int r, int l) {
        Matrix[r][l] = true;
    }

    // Method to get the out-degree of the point r in the graph
    public int get_OutDegree(int r) {
        int count = 0;
        for (int l = 0; l < num_vertices; l++) {
            // check if there is an edge from vertex r to vertex l
            if (Matrix[r][l]) {
                count++;
            }
        }
        return count; // return the out-degree of vertex u
    }

    // Method for obtaining the graph neighbours of vertex r
    public int[] get_Neighbors(int r) {
        int[] neighbors = new int[get_OutDegree(r)];
        int index = 0;
        for (int l = 0; l < num_vertices; l++) {
            // Determine if there is a path connecting vertices r and l
            if (Matrix[r][l]) {
                neighbors[index] = l;
                index++;
            }
        }
        return neighbors;
    }

    // Method to get the adjacency matrix of the graph
    public boolean[][] get_AdjMatrix() {
        return Matrix;
    }

    // Method to create a clone of the DirectedGraph object
    @Override
    public DirecGraph clone() {
        try {
            return (DirecGraph) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
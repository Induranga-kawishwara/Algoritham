//Induranga Kawishwara - 20200688

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    DirecGraph graph;
    public Graph(File file , String line) throws IOException {
        // Open the given file in a new BufferedReader instance.Withdrawn Sink
        BufferedReader file_reader = new BufferedReader(new FileReader(file));

        // Create a fresh DirectedGraph with the amount of nodes you provided at the start of the file.
        this.graph = new DirecGraph(Integer.parseInt(line));

        // Each line of the file is read, tokenized, and the edge to the graph is added
        while ((line = file_reader.readLine()) != null) {
            String[] token_line = line.split(" ");
            try {
                int r = Integer.parseInt(token_line[0]) - 1;
                int l = Integer.parseInt(token_line[1]) - 1;
                graph.setEdge(r, l);
            }
            catch (Exception ignored) {
            }
        }
        file_reader.close();
    }

    public DirecGraph getGraph() {
        return graph;
    }
}
//Induranga Kawishwara - 20200688

import java.util.ArrayList;
import java.util.List;

class AcyclicDet {
    public static boolean graphType(DirecGraph graph) {

        // A notice stating the sinks are going to be removed will be printed.
        System.out.print("The Sink Removed : ");

        // Variable to keep track of the number of sinks removed
        int sink_Count = 0;

        // ArrayList to keep track of the outer degree of each vertex
        ArrayList<Integer> outerDegree = new ArrayList<>();

        // Create a clone of the graph so that we don't modify the original graph
        DirecGraph cloneGraph = graph.clone();

        // Find each vertex's outside degree and save it in the outerDegree ArrayList.
        for (int r = 0; r < cloneGraph.getNumber_vertices(); r++) {
            outerDegree.add(cloneGraph.get_OutDegree(r));
        }

        while (true) {
            // Each vertex's external degree should be updated.
            for (int u = 0; u < cloneGraph.getNumber_vertices(); u++) {
                if (!(outerDegree.get(u) == -1)) {
                    outerDegree.set(u, cloneGraph.get_OutDegree(u));
                }
            }

            // Find a sink (vertex with no outgoing edges) in the graph
            int sink = findSinks(outerDegree);

            // Determine if the graph is acyclic by looking at the degree of the edges that connect each vertex.
            boolean isAcyclic = true;
            for (int degree : outerDegree) {
                if (degree != -1) {
                    isAcyclic = false;
                    break;
                }
            }

            // True if and only if the graph is without cycles.
            if (isAcyclic) {
                if (sink_Count == 0) {
                    System.out.println("No found !");
                } else {
                    System.out.println();
                }
                return true;
            }

            // If a sink is found, remove its outgoing edges and update the outer degree of the vertices
            if (sink != -1) {
                for (int v = 0; v < cloneGraph.getNumber_vertices(); v++) {
                    if (cloneGraph.get_AdjMatrix()[v][sink]) {
                        cloneGraph.get_AdjMatrix()[v][sink] = false;
                    }
                }
                System.out.print(sink + 1 + " ");
                sink_Count++;
                outerDegree.set(sink, -1);
            } else {
                // If no sink is found, the graph is cyclic
                if (sink_Count == 0) {
                    System.out.println("No found !");
                } else {
                    System.out.println();
                }
                return false;
            }
        }
    }

    public static int[] findCycle(DirecGraph graph) {
        //Determine the total number of graph nodes.
        int number_vertices = graph.getNumber_vertices();

       // Make arrays to keep track of the visited and stacked vertices.
        boolean[] visit = new boolean[number_vertices];
        boolean[] onStack = new boolean[number_vertices];

       // Create a list to store the vertices in the cycle
        List<Integer> cycle = new ArrayList<>();

      // For each unvisited vertex, check if it is part of a cycle
        for (int r = 0; r < number_vertices; r++) {
            if (!visit[r] && findCycleHelper(graph, r, visit, onStack, cycle)) {
                // Return an array containing the cycle's vertices if one is discovered.
                int[] result = new int[cycle.size()];
                for (int i = 0; i < cycle.size(); i++) {
                    result[i] = cycle.get(i);
                }
                return result;
            }
        }

      // In the absence of a cycle, return null.
        return null;
    }

    // Function to locate graph cycles using recursion.
    private static boolean findCycleHelper(DirecGraph graph, int r, boolean[] visit, boolean[] on_Stack, List<Integer> cycle) {

            visit[r] = true; // mark vertex as visited
            on_Stack[r] = true; // mark vertex as on the stack
            cycle.add(r); // add vertex to the cycle list

            for (int v : graph.get_Neighbors(r)) { // iterate over neighbors of vertex r
                if (!visit[v]) { // If the neighbour is not visited, the recursive function is called on the neighbour
                    if (findCycleHelper(graph, v, visit, on_Stack, cycle)) { // if function returns true, there is a cycle
                        return true;
                    }
                } else if (on_Stack[v]) { // If one's neighbour is also on the stack, then a cycle exists.
                    // Cycle detected
                    int idx = cycle.indexOf(v); // Determine the vertex v's index in the cycle.
                    cycle.add(v); // add vertex v to the cycle
                    int[] cycleArr = cycle.subList(idx, cycle.size()).stream().mapToInt(i -> i).toArray(); // create an array of the cycle
                    cycle.clear(); // clear the cycle list
                    for (int i : cycleArr) { // Include the cycle array's vertices in the cycle list
                        cycle.add(i);
                    }
                    return true;
                }
            }
            cycle.remove(cycle.size() - 1); // remove vertex r from the cycle list
            on_Stack[r] = false; // mark vertex as not on the stack

            return false; // no cycle found
        }

    public static int findSinks(ArrayList<Integer> outDegree) {
        for (int r = outDegree.size()-1; r>=0; r--) {
            if (outDegree.get(r) == 0) {
                // Found a sink
                return r;
            }
        }
        // Found a sink
        return -1;
    }
}
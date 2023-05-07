//Induranga Kawishwara - 20200688

//import packages
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Set the directory where the input files are located
        String folder_name = "testcases";
        File folder = new File(folder_name);

        // Find out what files are in the directory
        File[] files_name = folder.listFiles();

        // To store the input file list, make an ArrayList
        ArrayList<File> fileList = new ArrayList<>();

        // Iterate through each directory file in the system
        for (File file : files_name) {
            // Verify if the file is a text file by looking for the ".txt" extension
            if (file.isFile() && file.getName().endsWith(".txt")) {
                // If the file fits the requirements, add it to the ArrayList.
                fileList.add(file);
            }
        }

        // Mix up the input file list
        Collections.shuffle(fileList);

        // Loop through the shuffled list of input files
        for (File file: fileList) {
            // The current file's name will be printed to the console
            System.out.println("Txt File Name: " + file.getName());

            // To find out how many vertices a graph may have, you can access a "vertices" method
            String max_vertices = vertices(file);

            // Execute the "graph_process" method on the current file to analyse its graph data
            graph_process(file , max_vertices);
        }
    }

    private static void graph_process(File file , String line) {
        try {
            // Count the milliseconds from the beginning
            long start_Time = System.currentTimeMillis();

            // Convert the file using a GraphParser object into a directed graph
            Graph graphParser = new Graph(file , line);

            // To read the file, make a BufferedReader object.
            BufferedReader read_file = new BufferedReader(new FileReader(file));

            // Show the console the total number of nodes in the graph.Show the console the total number of nodes in the graph
            System.out.println("Sum of the vertices : "+ line);

            // Print the edges in the graph to the console, separated by commas
            System.out.print("Edges in a graph are : { ");
            while ((line = read_file.readLine()) != null) {
                String[] token_line = line.split(" ");
                // Print the line break and a comma to the console if there are additional lines to read
                if (read_file.ready()) {
                    System.out.print("["+token_line[0]+","+ token_line[1] +"]" + ", ");
                } else {
                    // If this is the final line, the closing brace and edge will be printed to the console
                    System.out.println("["+token_line[0]+","+ token_line[1] +"]" +" }");
                }
            }

            // Use the AcyclicDetector class to determine if the graph is indeed acyclic
            if (AcyclicDet.graphType(graphParser.getGraph())) {
                // Print a message to the console if the graph is acyclic
                System.out.println("The graph is acyclic.");
            } else {
                // If the graph is cyclic, a notification will be output to the console and the AcyclicDetector class will be used to locate the cycle
                System.out.println("The graph is cyclic.");
                int[] cycles = AcyclicDet.findCycle(graphParser.getGraph());
                System.out.print("Cycle: ");
                for (int i = 0; i < Objects.requireNonNull(cycles).length; i++) {
                    System.out.print(cycles[i] + 1);
                    if (i < cycles.length - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            }

            // Count the last milliseconds
            long end_Time = System.currentTimeMillis();

            // Determine how much time has passed in milliseconds
            long elapsed_Time = end_Time - start_Time;

            // Print the elapsed time to the console
            System.out.println("Elapsed time: " + elapsed_Time + " milliseconds");
            System.out.println();


        } catch (IOException e) {
            // If the file could not be read, an error message should be displayed and the programme should be terminated
            System.err.println("Failure to read file : " + e.getMessage());
            System.exit(1);
        }
    }

    public static String vertices(File redar) {

        int maxvert = 0;

        // Make an ArrayList to store the input file's non-zero data
        ArrayList<Integer> temp = new ArrayList<>();

        // Try to read the file using a Scanner
        try {
            Scanner file = new Scanner(redar);
            // While there are still values to be read from the file, do a loop
            while (file.hasNext()) {
                int value = Integer.parseInt(file.next());
                // Put it in the ArrayList if it's not zero and it's not there
                if(value != 0){
                    if(!temp.contains(value)){
                        temp.add(value);
                    }
                }
            }
            // Make maxvert equal to the ArrayList's size, which is the total number of non-empty values in the dataset
            maxvert= temp.size();
            // Close the file
            file.close();
        } catch (FileNotFoundException e) {
            // If the file is not found, print an error message
            System.out.println("Unable to read file");
        }
        // Convert maxvert to a String and return it
        return Integer.toString(maxvert);


    }
}
import java.io.File;  // Import the File class
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.*;
import java.io.FileWriter;

public class GraphFileReading {
	
    private int V;   // No. of vertices 
    private LinkedList<Integer> adj[];//Adjacency Lists 
    private boolean[] H_list;//boolean array of size (numOfNodes) 
                            //to record whether a node is a hospital
  
    // Constructor 
    @SuppressWarnings("unchecked")
    GraphFileReading(int v, int[] hospitals) 
    { 
        V = v; 
        adj = new LinkedList[v];
        for (int i=0; i<v; ++i) 
            adj[i] = new LinkedList<Integer>();
        H_list = markAsHospital(hospitals);
    } 
  
  
    // Function to add an edge into the graph
    void addEdge(int v,int w) 
    { 
    	if (adj[v].contains(w)||adj[w].contains(v)) {
    		return;
    	}
        adj[v].add(w); 
        adj[w].add(v);
    }
    
    // mark node i as true if i is a hospital
        boolean[] markAsHospital(int[] hospitalList) {
            H_list = new boolean[V];
            for (int i : hospitalList) {
                H_list[i] = true;
            }
            return H_list;
        }

    // check if a node is hospital.
        boolean isHospital(int v) {
            return H_list[v];
        }
    
    // get BFS traversal from a given source s and write to a file
        void BFS(int s, FileWriter myWriter) throws IOException
        { 
            // Mark all the vertices as not visited(By default 
            // set as false) 
            boolean visited[] = new boolean[V];
            int firstParent = s;
      
            // Create a queue for BFS 
            LinkedList<Integer> queue = new LinkedList<Integer>(); 
      
            // Mark the current node as visited and enqueue it 
            visited[s]=true; 
            queue.add(s); 
            int[] parents = new int[V];
            parents[firstParent] = -1; // create a BFS tree
            boolean canReach = false;

      
            while (queue.size() != 0) 
            { 
                // Dequeue a vertex from queue and print it 
                s = queue.poll(); 
                
                //visit node
                if (isHospital(s)) {
                    // if current node is a hospital, break out of the searching.
                    // output the path into a file
                    canReach = true;
                    String path = "";
                    path = printPath(s, parents, firstParent, path); //printPath: how did we reach the current node(hospital)?
                    int length = lengthOfPath(s, parents, firstParent); //count the total distance in number of edges
                    path = path.concat("\nThe total distance is " + length + " edges\n\n");
                    myWriter.write(path);
                    break;
                }
                
                // Get all adjacent vertices of the dequeued vertex s 
                // If a adjacent has not been visited, then mark it 
                // visited and enqueue it 
                Iterator<Integer> i = adj[s].listIterator(); 
                while (i.hasNext()) 
                { 
                    int n = i.next(); 
                    if (!visited[n]) 
                    { 
                        visited[n] = true;                      
                        queue.add(n); 
                        parents[n] = s; //store the node into BFS tree
                    } 
                }

            }

            if (!canReach)
                myWriter.write(" cannot reach any hospital.\n\n");

            
        } 
    
    // Function to print shortest path 
        // from source to currentVertex 
        // using parents array 
        private String printPath(int currentVertex, 
                                      int[] parents, int firstParent, String path) 
        { 
              
            // Base case : Source node has 
            // been processed 
            if (parents[currentVertex] == -1) 
            {
                return path.concat(firstParent + " ");
            } 
            // if not the source
            return printPath(parents[currentVertex], parents, firstParent, path).concat(currentVertex + " "); 
        } 
    
    // Function to total distance in number of edges 
        // from source to currentVertex 
        private int lengthOfPath(int currentVertex, 
                int[] parents, int firstParent) 
        { 
        
            // Base case : Source node has 
            // been processed 
            if (parents[currentVertex] == -1) 
            {
                return 0; 
            } 
            // if not the source
            return lengthOfPath(parents[currentVertex], parents, firstParent) + 1; 
             
        } 

    //perform BFS for each node in the graph
    private void BFSforEachNode() {
        try {
                

                Scanner sc = new Scanner(System.in);

                String writeTo = "";
                System.out.println();
                System.out.println("Create a text file to output your results.");
                System.out.println("Default output file name is <RoadMapBFS.txt>");
                System.out.println();
                System.out.println("Input file name (include .txt extension): ");
                writeTo = sc.nextLine();

                if(writeTo.isEmpty()) {
                    System.out.println("Default output file name is RoadMapBFS.txt");
                    writeTo = "RoadMapBFS.txt"; // default output file name
                    System.out.println("Output is written to " + writeTo);
                    System.out.println();
                }
                else{
                    File file = new File(writeTo);
                    System.out.println();
                    System.out.println("Output is written to " + writeTo);
                    System.out.println();
                }

                FileWriter myWriter = new FileWriter(writeTo);
                
                for (int i = 0; i < this. V; i++) {
                    Iterator<Integer> j = adj[i].listIterator();
                    if (!j.hasNext()) { // if the node is isolated
                        myWriter.write("Vertex " + i + " is an isolated vertex.\n\n");
                        continue;
                    }
                    
                    if (isHospital(i)) { // if the node itself is a hospital
                        myWriter.write("Vertex " + i + " is a hospital.\n\n");
                        continue;
                    }
                    // if the node is neither isolated nor a hospital.
                    myWriter.write("Solution path from vertex " + i + ": ");
                    this.BFS(i, myWriter);
                    myWriter.write("\n");
                }
                myWriter.close();
            } catch (IOException e) { // if an error occurs
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }
  
    // Driver method to 
    public static void main (String args[]) throws IOException 
    { 

        Scanner reader = new Scanner(System.in);
        
        Scanner sc = new Scanner(System.in);

        // ------------ Hospital Information text file ------------ //
        String H_fileName = "";
        System.out.println();
        System.out.println("Enter the file name that contains the hospital information.");
        System.out.println("Default hospital information file name is <Hospitals.txt>");
        System.out.println();
        System.out.println("Input file name (include .txt extension): ");
        H_fileName = sc.nextLine();

        if(H_fileName.isEmpty()) {
            System.out.println("Default hospital information file name is Hospitals.txt");
            H_fileName = "Hospitals.txt"; // default output file name
            System.out.println("Hospital information .txt file used: " + H_fileName);
            System.out.println();
        }

        String H_text;
        //read the file in as a string
        try {
            Path GfilePath = Paths.get(H_fileName);
            H_text = Files.readString(GfilePath);
            H_text = Files.readString(GfilePath).replace("# ", "").replaceAll("\\s+",",");
        }
        catch (Exception e) {
            System.out.println("Invalid File. Default file Hospitals.txt is used as hospital sources.");
            Path GfilePath = Paths.get("Hospitals.txt");
            H_text = Files.readString(GfilePath).replace("# ", "").replaceAll("\\s+",",");   
        }
        
            // get the numOfHospitals in the fixed position. (input format is standard)
        String[] hospital_array = H_text.split(","); 
        int numOfHospitals = Integer.parseInt(hospital_array[0]); 
        System.out.println("Number of Hospitals =  "+ numOfHospitals);
        int[] hospitals = new int[hospital_array.length - 1];
        
        // put each hospitalId into an int array
        for (int i = 0; i < hospitals.length; i++) {
            hospitals[i] = Integer.parseInt(hospital_array[i+1]);
        }//put each hospital node into an int array

        // ------------ Road Network Information text file ------------ //
        String R_fileName = "";
        System.out.println();
        System.out.println("Enter the file name that contains the road network information.");
        System.out.println("Default road network information file name is <roadNetwork_testing.txt>");
        System.out.println();
        System.out.println("Input file name (include .txt extension): ");
        R_fileName = sc.nextLine();

        if(R_fileName.isEmpty()) {
            System.out.println("Default road network information file name is roadNetwork_testing.txt");
            R_fileName = "roadNetwork_testing.txt"; // default output file name
            System.out.println("Road network information .txt file used: " + R_fileName);
            System.out.println();
        }

        String R_text;
        //read the file in as a string
        try{
            Path GfilePath = Paths.get(R_fileName);
            R_text = Files.readString(GfilePath);
            R_text = Files.readString(GfilePath).replaceAll("\\s+",",");
        } catch (Exception e) {
            System.out.println("Invalid File. Default file roadNetwork_testing is used as hospital sources.");
            Path GfilePath = Paths.get("roadNetwork_testing.txt");
            R_text = Files.readString(GfilePath).replaceAll("\\s+",",");    
        }
         
        //so that we can split the numbers into an array
        int findNodes1 = R_text.indexOf("Nodes:,")+7;
        int findNodes2 = R_text.indexOf(",Edges:,");
        int findEdges1 = findNodes2 + 8;
        int findEdges2 = R_text.lastIndexOf(",#,FromNodeId");
        
        int numOfNodes = Integer.parseInt(String.valueOf(R_text.substring(findNodes1,findNodes2)));
        int numOfEdges = Integer.parseInt(String.valueOf(R_text.substring(findEdges1,findEdges2)));
        System.out.println("\nNumber of Nodes: " + numOfNodes);
        System.out.println("\nNumber of Edges: " + numOfEdges);
        System.out.println(); //get the numOfNodes and numOfEdges based on the standard format
        
        //put each node in the road info file into an int array
        int startIndex = R_text.lastIndexOf("d,") + 2;
        String nodes = R_text.substring(startIndex, R_text.length());
        String[] node_char_array = nodes.split(",");
        int v = 0;
        int w = 0;
        int[] R_array = new int[node_char_array.length];
        int max = 0;
        for (int i = 0; i < R_array.length; i++) {
            R_array[i] = Integer.parseInt(node_char_array[i]);
            if (R_array[i] >= max)
                max = R_array[i];
        }

        if (numOfNodes >= max)
            max = numOfNodes;
        else max++;


        GraphFileReading g = new GraphFileReading(max, hospitals);
        for (int j = 0; j < R_array.length; j+=2) {
            g.addEdge(R_array[j], R_array[j+1]);
        }// for every pair j and j+1 in the array, pass them to graph and construct the edge.
        
       

        g.BFSforEachNode(); //call function
    } 
}

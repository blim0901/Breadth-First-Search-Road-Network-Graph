import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Scanner;

import java.io.File;  // Import the File class
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;

// Pair class to store a pair of <Integer, Integer> values.
class Pair {
	  public int front, back;
	  
	  Pair(int front, int back) {
		  this.front = front;
		  this.back = back;
	  }
	  
	  int getFirst() {
		  return front;
	  }
	  
	  int getSecond() {
		  return back;
	  }
	}

// Node class to store information about a vertex.
// V: 
class Node {
	public int v, hospt, dist, hospt_rank;
	
	Node(int v, int hospt, int dist, int hospt_rank) {
		this.v = v;
		this.hospt = hospt;
		this.dist = dist;
		this.hospt_rank = hospt_rank;
	}
}


public class MSGraphK {

    // default constructor
    public MSGraphK() {}
    
    // Integer constant for _INT_MAX
	private final static int N = 10000000;
    
    // Create an array of ArrayList to store (hospital, distance)
    // hospital: visited hospitalID
    // distance: distance from Vertex i (ith row) to the corresponding hospital
	private ArrayList<Pair>[] hosp_dist = new ArrayList[N]; // (hospital, distance)
	
	// Create an array of ArrayList to store (pre, rank of hospital(path)) 
	// pre: previous vertex visited of the current vertex visited
	// rank: numberOfHospital visited (one vertex visited by multiple hospital using the same distance), that's why we need a rank to differentiate the path
	private ArrayList<Pair>[] previous = new ArrayList[N]; // (pre, rank of hospital(path))
    
    // Create a Set to store hospitalID visited (no duplicate values for one row (one vertex))
    private Set<Integer>[] hospt = new Set[N];
    
	public static void addEdge(ArrayList<Integer>[] graph, int u, int v) {

		if (graph[u].contains(v) || graph[v].contains(u))
    		return;

		//Function to add 2 edges in an undirected graph 
	    graph[u].add(v);  
	    graph[v].add(u);
		
	}
	
    // Function for creating a queue, nodes to facilitate BFS,
    // and also printing the path using "previous" array of ArrayList
	void nearestTown(ArrayList<Integer>[] graph, int n, int[] sources,int s, int k) throws IOException { 
		
		for (int i = 0; i < N; i++) {
			previous[i] = new ArrayList<Pair>();
			hosp_dist[i] = new ArrayList<Pair>();
			hospt[i] = new HashSet<Integer>();
		}
		
	    //Create a queue for BFS 
	    Queue<Node> q = new LinkedList<>();
	    
	    //Mark all the source vertices as visited and enqueue it  
	    for(int i = 0; i < s; i++) { 
            
            // Node structure: {VertexID, HospitalID(visited), Distance, Rank}
            // Hospital have distance of 0, rank of 0
	    	Node temp1 = new Node(sources[i], sources[i], 0, 0);
	        q.add(temp1);
           
            // Distance of a hospital to itself is 0
	        Pair temp2 = new Pair(sources[i], 0);
	        hosp_dist[sources[i]].add(temp2); 
            
            // Report for a specific vertex i (ith row in sources array), the hospitalID it visited
	        hospt[sources[i]].add(sources[i]);
            
            // A hospital doesn't have a previous vertex, we use "-1" to indicate it.
	        Pair temp3 = new Pair(-1, -1);
	        previous[sources[i]].add(temp3); // there is no previous vertex for a hospital, thus vertex id = -1
	     
	    } 
		
		// MultiSource BFS main algorithm function
		Multisource_BFS(graph,q, k); 
		
		Scanner sc = new Scanner(System.in);

		// ------------ Output text file ------------ //
		String writeTo = "";
		System.out.println();
		System.out.println("Create a text file to output your results.");
		System.out.println("Default output file name is <RoadMapMS_TopK.txt>");
		System.out.println();
		System.out.println("Input file name (include .txt extension): ");
		writeTo = sc.nextLine();

		if(writeTo.isEmpty()) {
			System.out.println("Default output file name is RoadMapMS_TopK.txt");
			writeTo = "RoadMapMS_TopK.txt"; // default output file name
			System.out.println("Output is written to " + writeTo);
			System.out.println();
		}
		else{
			File file = new File(writeTo);
			System.out.println();
			System.out.println("Output is written to " + writeTo);
			System.out.println();
		}

		FileWriter writer = new FileWriter(writeTo);
		String heading = "Searching the Top-" +k+ " nearest hospital for Source: Vertex ()\n";
		heading = heading.concat("------------------------------------------------------------\n\n");
		writer.write(heading);
        
        // Printing Distances and k-Paths for every vertex i.
        // i: Vertex ID
        // j: jth closest hospital

	    for(int i = 0; i < n+1; i++) {
	    	
	    	writer.write("---------- Source: Vertex " + i + " ----------\n");
	    	
	    	for(int j = 0; j < k; j++) {
	    		writer.write("------ Hopsital No(" + (j+1) + ") ------\n");
	    		Pair current = new Pair(i, j);
	    		
	    		if(previous[i].size() < j+1) {
	    			writer.write((j) + ": No Hospital exists\n\n");
	    			continue;
	    		}
	    		
	    		writer.write("Path: ");
	    		
	    		// Printing the path by using backtracking
	    		while(current.getFirst() != -1) {
					writer.write(String.valueOf(current.getFirst()));
                    
                    // Note .get(current.getSecond()) is the rank, used in order to backtrack the correct path
                    // because there might be many vertex visiting the same current vertex.
	    			current = previous[current.getFirst()].get(current.getSecond());
	    			if(current.getFirst()!=-1)
	    				writer.write(" -> ");
	    		}
	    		
	    		// Printing the Distance
	    		writer.write("\nDistance: " + hosp_dist[i].get(j).getSecond() + "(edges)\n\n"); // get distance from Vertex i to jth closest Hospital
			}
			writer.write("\n\n");
		}
		writer.close();
	  
	}
	
	// Multisource BFS Function 
	void Multisource_BFS(ArrayList<Integer>[] graph, Queue<Node> q, int k) { 
        
        // Continue the BFS until the BFS queue is empty
	    while(q.size() != 0) 
	    { 
	        Node current = q.remove();
	      
	        for(int j = 0; j < graph[current.v].size(); j++)
	        { 
                // Fetch the adjacent vertices of "current.v"
	        	int adj = graph[current.v].get(j);
	        	
	        	if(hosp_dist[adj].size() < k) { // if haven't been visited by k numbers of hospital, then true.
	        		if(hospt[adj].contains(current.hospt)) // if visited by the current(parent)'s hospital, skip and continue the next iteration.
	        			continue;
                    
					// Add to queue: {adjacent vertex ID, hospitalID, distance = distance + 1, rank}
	        		Node temp1 = new Node(adj, current.hospt, current.dist + 1, hosp_dist[adj].size());
	        		q.add(temp1);
                    
                    // Store distance from the adjacent vertex to the current hospital visited
	        		Pair temp2 = new Pair(current.hospt, current.dist + 1);
	        		hosp_dist[adj].add(temp2);
                    
                    // Report hospital visited by the adjacent vertex to the corresponding adjacent vertex (ith row)
	        		hospt[adj].add(current.hospt);
                    
                    // Store the previous vertex and its rank of the current adjacent vertex.
                    // Rank is used to store different possible path that visited the same vertex before visiting the adjacent vertex
	        		Pair temp3 = new Pair(current.v, current.hospt_rank);
	        		previous[adj].add(temp3);
	        		
	        	}
	        } 
	    } 
	}
	public static void main(String[] args) throws IOException {

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

		String[] hospital_array = H_text.split(","); 
        int h = Integer.parseInt(hospital_array[0]); 
        System.out.println("Number of Hospitals =  "+ h);
        int[] sources = new int[hospital_array.length - 1];
		
		// put each hospitalId into an int array
		for (int i = 0; i < sources.length; i++)
			sources[i] = Integer.parseInt(hospital_array[i+1]);
		
		// number of hospitals
		int S = sources.length;



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
		
		// read in the road info file as a string and process it 
        // so that we can split the numbers into an array
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
		
        int[] R_array = new int[node_char_array.length];
		int max = 0;
        for (int i = 0; i < R_array.length; i++) {
            R_array[i] = Integer.parseInt(node_char_array[i]);
            if (R_array[i] >= max)
				max = R_array[i];
		}
		
		// ------------ Input k ------------ //
		int k = 1; // default value of k
		do{
			System.out.println("Input the number of nearest hospitals k you want to search: ");
            
            if (!sc.hasNextInt()) {
                System.out.println("Input is not a number.");
                sc.nextLine();
                continue;
			}
			
			k = sc.nextInt();
			
            if (k < 1 || k > S){
                System.out.println("Invalid number. Please input an integer k such that k >= 1 and k <= " 
                                    + S + "\n");
                continue;
            }
			else
				break;

		} while(true);
		
		// update the numOfNodes in case there's error or bug
		if (numOfNodes >= max)
            max = numOfNodes;
		else max++;
		
		// final number of vertices
		int n = max;
	
		ArrayList<Integer>[] graph = new ArrayList[N];
		for (int j = 0; j < N; j++) { 
            graph[j] = new ArrayList<Integer>(); 
        }
		
		// Adding Edges
		for(int z = 0; z < R_array.length; z+=2)
			addEdge(graph, R_array[z], R_array[z+1]);
	    
	    MSGraphK app = new MSGraphK();
	    app.nearestTown(graph, n, sources, S, k);

	}
}
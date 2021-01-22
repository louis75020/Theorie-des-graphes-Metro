package javaProject2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Louis & Thomas
 * 
 * The class Network is the main class of this project.
 * The way a social network and a subway network work are the same; so the same methods will be applied to these networks.
 * A network will be represented with a list of Nodes.
 *
 */
class Network {
	
	/**
	 * @author Louis & Thomas
	 * 
	 * useful to return the 2 objects created by the dijkstra algorithm
	 *
	 */
	private class Dijkstra{
		
		int[] dist;//distances between a node and all the other nodes (the shortest)
		ArrayList <Node> tmpNetwork;
		
		public int[] getDist() {
			return dist;
		}
		public void setDist(int[] dist) {
			this.dist = dist;
		}
		public ArrayList<Node> getTmpNetwork() {
			return tmpNetwork;
		}
		public void setTmpNetwork(ArrayList<Node> tmpNetwork) {
			this.tmpNetwork = tmpNetwork;
		}
		
		/**
		 * @return the mean of the array dist taken as a vector
		 */
		private float mean() {
		    float sum = 0;
		    for (int i = 0; i < dist.length; i++) {
		        sum += dist[i];
		    }
		    return sum / dist.length;
		}
		
		
	}
	
	//a list which contains all the nodes of the network-very useful
	ArrayList <Node> allnodes;

	//constructors
	
	/**
	 * This constructor is not useful but is necessary.
	 */
	Network(){
		this.allnodes=new ArrayList <Node>();
	}
	
	
	/**
	 * @param file-A formated file which contains all the information about the network.
	 *	the main constructor for this class. It is a general method for all kinds of networks.
	 */
	Network(File file){

		System.out.println(">>>>>>>Network(File)>>>>>");

		allnodes=new ArrayList<Node>();

		//reading

		String tmp="";
		Scanner scanner = null;
		Node node;

		try{
			scanner=new Scanner(file);// A scanner is used... even if bugs may happen.
			tmp=scanner.nextLine();
			
			while(!tmp.contains("###") && scanner.hasNext()) { //loading the names of the nodes
				if(!tmp.contains("%")) {//not a commentary

					node=new Node(tmp);

					if(!allnodes.contains(node))allnodes.add(node);//verifying if there are no doubles.

				}
				tmp=scanner.nextLine();
				//the whole names may have been added
			}

			System.out.println("********");

			String[] toSplit;
			while(scanner.hasNext()) {
				if(!tmp.contains("%")) {
					
					//checking if the arc is oriented or not
					
					if(tmp.contains(">")) {
						toSplit=tmp.split(">");

						for(Node jsp:allnodes) {
							if(jsp.getName().equals(toSplit[0])) {
								jsp.addChild(new Node(toSplit[1]));
							}
						}
					}

					if(tmp.contains(":")) {
						toSplit=tmp.split(":");

						for(Node jsp:allnodes) {
							if(jsp.getName().equals(toSplit[0])) {
								jsp.addChild(new Node(toSplit[1]));
							}
							if(jsp.getName().equals(toSplit[1])) {
								jsp.addChild(new Node(toSplit[0]));
							}
						}
					}
				}
				tmp=scanner.nextLine();
			}

		}catch(FileNotFoundException e) {//this exception is caught there.
			e.printStackTrace();
		}finally {
			if(scanner!=null)scanner.close();
		}

		System.out.println("<<<<<<<<Network(File)<<<<<<<");
	}
	//methods
	
	private void stepConnected(ArrayList<Node> mostConnected, Node tmp) {
		
		Node node=mostConnected.get(0);
		int j=-1;
		
		if(!mostConnected.contains(tmp)) {

			for(Node tmp2:mostConnected) {
				if(tmp2.children.size()<=node.children.size()) {
					node=tmp2;
					j=mostConnected.indexOf(tmp2);
					
				}
			}
		}
		if(tmp.children.size()>node.children.size() && j!=-1)mostConnected.set(j,tmp);
	}

	private void sortBySize(ArrayList<Node>Tab) {
		
		ArrayList<Node>T=new ArrayList<>();
		
		Node switcher=Tab.get(0);
		int j=-1,i=-1;
		
		for(i=0;i<Tab.size();i++) {
			for(j=i+1;j<Tab.size();j++) {
				if(Tab.get(i).children.size()<Tab.get(j).children.size()) {
					switcher=Tab.get(i);
					Tab.set(i, Tab.get(j));
					Tab.set(j, switcher);
				}
			}
		}
		Tab=T;
	}
	/**
	 * @param nbOfHubs the number of hubs wanted (10 in general)
	 * @return a list with the nbOfHubs hubs; ordered from the first to the last.
	 */
	
	public ArrayList<Node> mostConnected(int nbOfHubs) {//q1 and q3a
		
		System.out.println(">>>>>>>mostConnected>>>>>>>>");
		
		ArrayList <Node>Tab=new ArrayList <Node>();
		
		if(nbOfHubs>allnodes.size()) { //checking the argument
			System.out.println("pas assez de noeuds");
			return null;
		}
		
		//Initialization: copying the nbOfHubs first hubs
		
		for(int j=0;j<nbOfHubs;j++) {
			Tab.add(allnodes.get(j));
		}

		for(Node jsp:allnodes) {
			stepConnected(Tab,jsp);

		}
		
		sortBySize(Tab);
		
		System.out.println("<<<<<<<<<mostConnected()<<<<<<<<<");
		return Tab;
	}
	
	//O(n)
	/**
	 * @param notVisited -the nodes in the list allnodes which have not been visited yet
	 * @param dist the current distances between the departure node and the other nodes
	 * @return the nearest node from departure node in the list
	 * 
	 * This private method will be used in the dijkstra method
	 */
	private Node minDistNode(ArrayList<Node>notVisited, int[] dist){

		int min=1000;//-inf

		Node node=null; //may catch if null

		for(Node jsp:notVisited) {

			if(dist[allnodes.indexOf(jsp)]<min) {
				min=dist[allnodes.indexOf(jsp)];
				node=jsp;
			}
		}

		return(node);
	}
	
	//O(2n)
	/**
	 * @param s1-the departure node
	 * @param s2-another node
	 * @param dist-the current array with the current distances between the nodes and the node departure
	 * @param tmpNetwork-the sub-graph created by dijkstra algorithm in construction
	 * 
	 * This method allows to add in the sub-graph s2 if it is interesting to go from the departure node to this node by a certain way.
	 */
	private void maj_dist(Node s1,Node s2,int[] dist,ArrayList<Node>tmpNetwork) {

		int tmp1=-1,tmp2=-1;

		for(Node jsp:allnodes) {

			if(s1.getName().equals(jsp.getName())) {
				tmp1=allnodes.indexOf(jsp);
			}
			if(s2.getName().equals(jsp.getName())) {
				tmp2=allnodes.indexOf(jsp);
			}
		}

		if(dist[tmp2]>dist[tmp1]+1) {
			dist[tmp2]=dist[tmp1]+1;

			if(!tmpNetwork.contains(s1))tmpNetwork.add(s1);
			for(Node jsp3:allnodes) {
				if(jsp3.getName()==s2.getName())tmpNetwork.add(jsp3);
			}
		}

	}
	
	//O(n*n)
	/**
	 * @param s any node from the tmpNetwork
	 * @param tmpNetwork the partial graphe created with the dijkstra algorithm
	 * @return the previous node in the tmpNetwork of s
	 */
	private Node previous (Node s, ArrayList<Node>tmpNetwork){

		for(Node jsp:tmpNetwork) {
			for(Node jsp2:jsp.children) {
				if(jsp2.getName().equals(s.getName())) {
					return jsp;
				}
			}
		}
		System.out.println("error");
		return(null);
	}

	/**
	 * @param departure any node
	 * @return an object with the sub-graph of dijkstra algorithm and the linked distances.
	 * 
	 * Source:https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
	 * helpful link for the implementation of this famous algorithm
	 */
	private Dijkstra dijkstra_general(Node departure) {
		
		//Initialization
		int dist[]=new int[allnodes.size()];
		ArrayList<Node>notVisited=new ArrayList<Node>();
		ArrayList<Node>tmpNetwork=new ArrayList<Node>();
		
		for(Node jsp:allnodes) {

			notVisited.add(jsp);
			if(jsp.getName().equals(departure.getName())) {
				dist[allnodes.indexOf(jsp)]=0;
			}
			else {
				dist[allnodes.indexOf(jsp)]=1000; //1000=infinite
			}
		}

		//method

		Node s1;
		
		while(!notVisited.isEmpty()) {
			s1=minDistNode(notVisited, dist);
			notVisited.remove(s1);
			if(s1!=null) {
				for(Node s2:s1.children) {
					maj_dist(s1, s2, dist, tmpNetwork);
				}
			}else {break;}
		}
		
		Dijkstra toReturn=new Dijkstra();
		toReturn.setDist(dist);
		toReturn.setTmpNetwork(tmpNetwork);
		return toReturn;
	}
	
	public int[] dijkstra_dist(Node departure) {
		return dijkstra_general(departure).getDist();
	}
	
	public ArrayList<Node> dijkstra(Node departure){
		return dijkstra_general(departure).getTmpNetwork();
	}
	/**
	 * @param departure
	 * @param arrival
	 * @return true if the names of the arguments are names of nodes of the list allnodes
	 */
	private Boolean checkIfNode(Node departure,Node arrival) {
		
		Boolean A=false,B=false;
		
		for(Node node:allnodes) {
			if(departure.getName().equals(node.getName()))A=true;
			if(departure.getName().equals(node.getName()))B=true;
		}
		if(A && B)return true;
		else {
			System.out.println("mauvais nom");
			return false;
		}
	}

	/**
	 * @param departure
	 * @param arrival
	 * @return an optimal itinerary between 2 nodes of any network; if possible. The algorithm uses the dijkstra algorithm.
	 */
	public Itinerary createItinerary(Node departure, Node arrival) {//q2 and q3b
		//https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra
		
		Boolean A=checkIfNode(departure,arrival);
		if(!A) {
			System.out.println(departure);
			System.out.println(arrival);
			return null;
		}

		ArrayList<Node>tmpNetwork=dijkstra(departure);
		System.out.println(tmpNetwork);
		Itinerary itinerary=new Itinerary();
		Node node=arrival;
		//System.out.println(tmpNetwork);
		
		while(!node.getName().equals(departure.getName())) {
			itinerary.itinerary.add(node);
			System.out.println(itinerary);
			node=previous(node, tmpNetwork);
		}
		itinerary.itinerary.add(node);

		//System.out.println("<<<<<<<<<<<createItinerary()<<<<<<<<<<");
		return(itinerary);
	}
	
	/**
	 * @param nb 
	 * @param kevinBaker a name of a node to test
	 * @param anyActor another name of a node to test
	 * @return true if the optimal itinerary between these 2 nodes has a sorter length than nb
	 */
	public boolean degrees(int nb, Node kevinBaker,Node anyActor) {
		
		Itinerary itinerary=createItinerary(anyActor, kevinBaker);
		
		if(itinerary.itinerary.size()>nb+1) {
			System.out.println("ça marche");
			return true;
		}
		else {
			System.out.println("ça ne marche pas");
			return false;
		}
	}


 	/**
 	 * @param nb
 	 * @param kevinBaker the node to test
 	 * @return true if for each other node from the network, less than nb degrees are necessay to join Kevin to the other nodes
 	 * 
 	 * this method will write on the screen the mean of necessary degrees to join the nodes
 	 */
 	public boolean degrees(int nb, Node kevinBaker) {//q4
		
		Dijkstra dj=dijkstra_general(kevinBaker);
		
		float mean=dj.mean();
		System.out.println("la moyenne en degrés kavinBakon de ce réseau est: "+mean);
		
		Itinerary itinerary;
		
		for(Node node:allnodes) {
			itinerary=new Itinerary();
			while(!node.getName().equals(kevinBaker.getName())) {
				itinerary.itinerary.add(node);
				node=previous(node, dj.tmpNetwork);
			}
			itinerary.itinerary.add(node); //Kevin himself has a value of 0.
			if(itinerary.itinerary.size()>nb+1) {
				System.out.println("unchecked");
				System.out.println("example"+"   "+itinerary);
				return false;
			}
			
		}
		
		System.out.println("verified");
		return true;
		
	}
	
 	//auto-generated
	@Override
	public String toString() {
		return "Network [allnodes=" + allnodes + "]";
	}




}

package javaProject2018;

import java.io.File;
import java.util.Scanner;

/**
 * @author Louis & Thomas
 * 
 * A class which contains the main method for this project.
 * It contains a kind of interface for the users.
 *
 */

public class Test {
	
	/**
	 * A static method which would be called to get the menu of the interface.
	 */
	private static void options() {
		System.out.println("press 1 to Quit");
		System.out.println("press 2 to do the complete project and quit");
		System.out.println("press 3 to do the question 1");
		System.out.println("press 4 to do the question 2");
		System.out.println("press 5 to do the question 3a");
		System.out.println("press 6 to do the question 3b");
		System.out.println("press 7 to do the question 4");
		//System.out.println("press 8 to do the question bonus");
		System.out.println("press 9 to get the options of this menu");
	}

	/**
	 * @param args_if the array is empty; the files would be automatically found in the current file. Otherwise, 2 arguments are asked ("filenames")
	 * the main is a menu.
	 * The user can choose between doing the complete project or do any question separately.
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Network subwayNetwork;
		Network socialNetwork;
		
		if (args.length==0) {
			File metroFile;
			File socialFile;
			metroFile=new File("C:/Users/louis/Documents/L3/metro.txt");
			socialFile=new File("C:/Users/louis/Documents/L3/sonet-1000.txt");
			subwayNetwork=new Network(metroFile);
			socialNetwork=new Network(socialFile);
		}else {
			subwayNetwork=new Network(new File(args[0]));
			socialNetwork=new Network(new File(args[1]));
		}
		int tmp;
		Scanner scanner=new Scanner(System.in);
		
		Node departure;
		Node arrival;
		
		String s1;
		String s2;
		
		Test.options();
		while(true) {
			
			System.out.println("Menu");
			tmp=scanner.nextInt();
			
			if(tmp==1) {
				System.out.println("au revoir");
				if(scanner!=null)scanner.close();
				break;
			}if(tmp==2) {
				System.out.println("q1:");
				System.out.println(subwayNetwork.mostConnected(10));
				System.out.println("q2:");
				//System.out.println(subwayNetwork.allnodes);
				System.out.println("Donner le nom de 2 stations svp, remplacez tous les espaces par des > svp");//attention car les espaces passent mal avec le scanner
				s1=scanner.next();
				s2=scanner.next();
				s1=s1.replace(">", " ");
				s2=s2.replace(">", " ");
				departure=new Node(s1);
				arrival=new Node(s2);
				System.out.println(subwayNetwork.createItinerary(arrival,departure));
				System.out.println("q3a:");
				System.out.println(socialNetwork.mostConnected(10));
				System.out.println("q3b:");
				departure=new Node(scanner.next());
				arrival=new Node(scanner.next());
				System.out.println(socialNetwork.createItinerary(arrival, departure));
				System.out.println("q4:");
				System.out.println("donner l'id de Kevin Baker");
				departure=new Node(scanner.next());
				socialNetwork.degrees(6,departure);
				System.out.println("au revoir");
				break;
			}if(tmp==3) {
				System.out.println("q1:");
				System.out.println(subwayNetwork.mostConnected(10));
			}if(tmp==4) {
				System.out.println("q2:");
				//System.out.println(subwayNetwork.allnodes);
				System.out.println("Donner le nom de 2 stations svp, remplacez tous les espaces par des > svp");
				
				
				s1=scanner.next();
				s2=scanner.next();
				
				s1=s1.replace(">", " ");
				s2=s2.replace(">", " ");
				
				departure=new Node(s1);
				arrival=new Node(s2);
				System.out.println(subwayNetwork.createItinerary(arrival,departure));
				
					
			}if(tmp==5) {
				System.out.println("q3a:");
				System.out.println(socialNetwork.mostConnected(10));
			}if(tmp==6) {
				System.out.println("q3b:");
				System.out.println("donner le nom (id) de 2 personnes svp");
				departure=new Node(scanner.next());
				arrival=new Node(scanner.next());
				System.out.println(socialNetwork.createItinerary(arrival,departure));
			}if(tmp==7) {
				System.out.println("q4:");
				System.out.println("donner l'id de Kevin Bakon");
				departure=new Node(scanner.next());
				socialNetwork.degrees(6,departure);
			}
			/*if(tmp==8) {
				File file3=new File("C:/Users/louis/Documents/L3/metro-wl.txt");
				MetroManager metro=new MetroManager(metroFile,file3);
				metro.itineraryByAllSubwayLines();
			}*/
			if(tmp==9) {
				Test.options();
			}
			
		}
		
	}

}

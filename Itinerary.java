package javaProject2018;

import java.util.ArrayList;

/**
 * @author Louis & Thomas
 * 
 * This class only contains a list and a constructor and might not be used...
 * But, an itinerary is an oriented graph and his nodes have less than one child
 *
 */
class Itinerary {
	
	ArrayList <Node>itinerary;

	
	Itinerary(){
		this.itinerary=new ArrayList <Node>();
	}
	

	@Override
	public String toString() {
		return "Itinerary [itinerary=" + itinerary + "]";
	}
	
	
}

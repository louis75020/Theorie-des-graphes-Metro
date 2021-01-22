package javaProject2018;

import java.util.ArrayList;

//all classes may be "protected"
class Node {
	
	private String name;
	
	ArrayList <Node> children;
	
	//constructor
	Node(String name){
		this.name=name;
		children=new ArrayList <Node>();
	}
	
	
	public void addChild(Node node) {
		//System.out.println("ici");
		children.add(node);
	}
	
	//auto-implemented
	@Override
	public String toString() {
		return "Node [name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

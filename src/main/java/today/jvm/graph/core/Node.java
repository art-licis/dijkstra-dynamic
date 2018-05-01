package today.jvm.graph.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Graph node. Represented by name and list of edges connecting this node
 * to other nodes.
 *
 * @author Arturs Licis
 *
 */
public class Node {
	private String name;
	private List<Edge> edges = new LinkedList<Edge>();

	public Node() {
	}

	public Node(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public List<Edge> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Node[");
		str.append(name).append("|");
		getEdges().forEach(e -> str.append(" ->").append(e.getTarget().getName()));
		str.append("]");
		return str.toString();
	}
}

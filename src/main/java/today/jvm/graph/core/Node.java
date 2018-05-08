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
public class Node implements PathElement {
	private String name;
	private List<Edge> edges = new LinkedList<Edge>();

	private transient int seq = -1;
	private transient int distance;
	private transient Node previous;

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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Node[");
		str.append(name).append("/").append(distance).append("/ ");
		getEdges().forEach(e -> str.append(" ->").append(e.getTarget().getName()));
		str.append("]");
		return str.toString();
	}
}

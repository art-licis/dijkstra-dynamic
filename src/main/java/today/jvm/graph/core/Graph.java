package today.jvm.graph.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph represented by Node objects and adjacency list.
 *
 * @author Arturs Licis
 */
public class Graph {
	private String name;
	private List<Node> nodes = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();

	private transient Node[] nodeRefs;
	private transient boolean isDirty = false;

	public Graph(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node addNode(Node node) {
		if (nodes.contains(node)) throw new IllegalArgumentException("Graph already contains: " + node);
		nodes.add(node);
		isDirty = true;
		return node;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public Node getNode(int seq) {
		if (nodeRefs == null) throw new RuntimeException("Node refs have not been initialized");
		if (seq > getNodeCount()) throw new IndexOutOfBoundsException("Node index out of bounds: " + seq);

		return nodeRefs[seq];
	}

	public int getNodeCount() {
		return nodes.size();
	}

	public int getEdgeCount() {
		return edges.size();
	}

	/**
	 * Creates directed edge between nodes nFrom and nTo.
	 * @param nFrom - source node
	 * @param nTo - target node
	 * @param weight edge weight
	 */
	public void createEdge(Node nFrom, Node nTo, int weight) {
		Edge edge = new Edge(nFrom, nTo, weight);
		nFrom.addEdge(edge);
		isDirty = true;
	}

	/**
	 * Creates undirected edge between nodes n1 and n2. In other words, this
	 * method creates two instances of edges (n1->n2 and n2->n1), and updates
	 * each of node with corresponding edge.
	 *
	 * @param n1 - node 1
	 * @param n2 - node 2
	 * @param weight edge weight
	 */
	public void createBidirectionalEdge(Node n1, Node n2, int weight) {
		{
			Edge edge = new Edge(n1, n2, weight);
			n1.addEdge(edge);
			edges.add(edge);
		}
		{
			Edge edge = new Edge(n2, n1, weight);
			n2.addEdge(edge);
			edges.add(edge);
		}
		isDirty = true;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void buildNodeRefs() {
		nodeRefs = new Node[getNodeCount()];
		int seq = 0;
		for (Node node : getNodes()) {
			nodeRefs[seq] = node;
			node.setSeq(seq++);
		}
	}

	public void resetDirty() {
		this.isDirty = false;
	}

	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Graph: '").append(name).append("' [\n");

		str.append("  Node(s): (");
		for (Node n : nodes) {
			str.append(n.getName()).append(", ");
		}
		str.setLength(str.length() - 2);
		str.append(")\n");

		str.append("  Edge(s): (");
		for (Edge e : edges) {
			str.append(e.getSource().getName())
					.append(" --{").append(e.getWeight()).append("}--> ")
					.append(e.getTarget().getName()).append(", ");
		}
		str.setLength(str.length() - 2);
		str.append(")\n");

		str.append("]\n");

		return str.toString();
	}
}

package today.jvm.graph.core;

/**
 * Edge in weighted graph connecting source and target nodes.
 *
 * @author Arturs Licis
 */
public class Edge {
	private Node source;
	private Node target;
	private int weight;

	public Edge(Node source, Node target, int weight) {
		super();
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}

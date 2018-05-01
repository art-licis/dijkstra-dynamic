package today.jvm.graph.core;

/**
 * Edge in weighted graph connecting source and target nodes.
 *
 * @author Arturs Licis
 */
public class Edge {
	private Node source;
	private Node target;
	private String name;
	private int weight;

	public Edge(Node source, Node target, String name, int weight) {
		super();
		this.source = source;
		this.target = target;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}

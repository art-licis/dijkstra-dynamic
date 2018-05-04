package today.jvm.graph.core;

/**
 * Path fragment within a path, represented by source & target nodes.
 *
 * @author Arturs Licis
 */
public class PathFragment implements PathElement {
	private Node source;
	private Node target;
	private int distance;

	public PathFragment(Node source, Node target, int distance) {
		this.source = source;
		this.target = target;
		this.distance = distance;
	}

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}

	@Override
	public int getDistance() {
		return distance;
	}
}

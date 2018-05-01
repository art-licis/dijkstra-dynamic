package today.jvm.graph.core;

/**
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

	@Override
	public int getDistance() {
		return distance;
	}
}

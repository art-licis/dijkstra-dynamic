package today.jvm.graph.core;

import java.util.List;

/**
 * Path with a total distance, represented by a list of nodes.
 *
 * @author Arturs Licis
 *
 */
public class Path {
	private int distance;
	private List<Node> nodes;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
}

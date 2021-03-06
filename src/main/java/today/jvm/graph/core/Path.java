package today.jvm.graph.core;

import java.util.List;

/**
 * Path with a total distance, represented by a list of nodes.
 *
 * @author Arturs Licis
 *
 */
public class Path implements PathElement {
	private int distance;
	private List<PathElement> pathElements;

	@Override
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public List<PathElement> getPathElements() {
		return pathElements;
	}

	public void setNodes(List<PathElement> pathElements) {
		this.pathElements = pathElements;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Path: [");
		for (PathElement pathElement: pathElements) {
			str.append(pathElement.toString()).append(", ");
		}
		str.setLength(str.length() - 2);
		str.append("]");

		return str.toString();
	}
}

package today.jvm.graph.dijkstra.dynamic;

import java.util.*;

import today.jvm.graph.core.*;

/**
 * Shortest path solver, using Dijkstra + dynamic programming to reuse previously
 * discovered shortest paths.
 * <p>
 * This can be effective in problems when you need to search for the shortest path in the same
 * graph multiple times.
 * <p>
 * The idea is to hold all previously found shortest paths (therefore it requires additional O(n^2) memory,
 * where n - node), and re-use it as a cache when searching for a new path. It can also be reused
 * from the mid-point in a new path.
 * <p>
 *
 * TODO: ensuring graph immutability once it has been submitted to solver.
 *
 * @author Arturs Licis
 */
public class DijkstraDynamicSolver {
	private Graph graph;
	private int[][] minDistance;

	public DijkstraDynamicSolver(Graph graph) {
		this.graph = graph;
		graph.buildNodeRefs();
		minDistance = new int[graph.getNodeCount()][graph.getNodeCount()];
	}

	public Path findShortestPath(Node source, Node target) {
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance).thenComparingInt(Node::getSeq));

		graph.getNodes().forEach(n -> { n.setDistance(Integer.MAX_VALUE); n.setPrevious(null); });
		source.setDistance(0);

		priorityQueue.add(source);

		PathElement finalElement = null;
		while (!priorityQueue.isEmpty()) {
			Node node = priorityQueue.poll();

			if (node != source) {
				minDistance[source.getSeq()][node.getSeq()] = node.getDistance();
			}
			if (node == target) {
				finalElement = node;
				break;
			}

			if (minDistance[node.getSeq()][target.getSeq()] != 0) {
				// 'cache' hit between current node & target
				finalElement = new PathFragment(node, target, minDistance[node.getSeq()][target.getSeq()]);
				break;
			}
			for (Edge edge : node.getEdges()) {
				Node nextNode = edge.getTarget();
				int newDist = node.getDistance() + edge.getWeight();
				if (newDist < nextNode.getDistance()) {
					// new distance to 'nextNode' is less than currently found:
					// update distance, 'previous' reference and 'nextNode' in queue
					nextNode.setDistance(newDist);
					nextNode.setPrevious(node);

					// TODO: remove is O(n), think of a better way
					priorityQueue.remove(nextNode);
					priorityQueue.add(nextNode);
				}
			}
		}

		Path path = null;
		if (finalElement instanceof Node) {
			updateMinDistance(source, target);
			path = buildPath(target);
		} else if (finalElement instanceof PathFragment){
			updateMinDistance(source, ((PathFragment) finalElement).getSource());
			path = buildPath(((PathFragment) finalElement).getSource());
			path.getPathElements().add(finalElement);
		}

		return path;
	}

	protected void updateMinDistance(Node sourceNode, Node targetNode) {
		// TODO: intermediate results
		do {
			minDistance[sourceNode.getSeq()][targetNode.getSeq()] = targetNode.getDistance();
			targetNode = targetNode.getPrevious();
		} while (targetNode != null);
	}

	protected Path buildPath(Node targetNode) {
		Path path = new Path();
		path.setDistance(targetNode.getDistance());
		Deque<PathElement> nodes = new LinkedList<>();
		do {
			nodes.addFirst(targetNode);
			targetNode = targetNode.getPrevious();
		} while (targetNode != null);
		path.setNodes((List) nodes);

		return path;
	}

	public void printMinDistances() {
		final String PAD = "%4s";
		System.out.printf(PAD, "");
		for (int i = 0; i < graph.getNodeCount(); i++) {
			System.out.printf(PAD, graph.getNode(i).getName());
		}
		System.out.println();
		System.out.printf(PAD, "");
		for (int i = 0; i < graph.getNodeCount(); i++) {
			System.out.printf(PAD, "----");
		}
		System.out.println();
		for (int r = 0; r < minDistance.length; r++) {
			System.out.printf(PAD, graph.getNode(r).getName() + "|");
			for (int d : minDistance[r]) {
				System.out.printf(PAD, d);
			}

			System.out.println();
		}
	}
}

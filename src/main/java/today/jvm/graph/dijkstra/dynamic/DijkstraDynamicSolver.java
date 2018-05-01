package today.jvm.graph.dijkstra.dynamic;

import java.util.*;

import today.jvm.graph.core.Edge;
import today.jvm.graph.core.Graph;
import today.jvm.graph.core.Node;
import today.jvm.graph.core.Path;

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

		graph.getNodes().forEach(n -> n.setDistance(Integer.MAX_VALUE));
		source.setDistance(0);

		priorityQueue.add(source);
		while (!priorityQueue.isEmpty()) {
			Node node = priorityQueue.poll();
			// TODO: this might be redundant
			if (node != source) {
				minDistance[source.getSeq()][target.getSeq()] = node.getDistance();
			}
			if (node == target) {
				break;
			}
			if (minDistance[node.getSeq()][target.getSeq()] != 0) {
				System.out.printf("Cache hit between nodes %s and %s; distance: %d\n", source, target, minDistance[source.getSeq()][target.getSeq()]);
				// TODO: cache hit; re-use cached min path?
			}
			for (Edge edge : node.getEdges()) {
				Node nextNode = edge.getTarget();
				int newDist = node.getDistance() + edge.getWeight();
				if (newDist < nextNode.getDistance()) {
					nextNode.setDistance(newDist);
					nextNode.setPrevious(node);
					priorityQueue.remove(nextNode);
					priorityQueue.add(nextNode);
				}
			}
		}

		buildMinDistance(source, target);

		Path p = buildPath(target);
		return p;
	}

	protected void buildMinDistance(Node sourceNode, Node targetNode) {
		// TODO: intermediate results
		do {
			minDistance[sourceNode.getSeq()][targetNode.getSeq()] = targetNode.getDistance();
			targetNode = targetNode.getPrevious();
		} while (targetNode != null);
	}

	protected Path buildPath(Node targetNode) {
		Path path = new Path();
		path.setDistance(targetNode.getDistance());
		Deque<Node> nodes = new LinkedList<>();
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

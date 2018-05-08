package today.jvm.graph.dijkstra.dynamic;

import java.util.*;

import today.jvm.graph.core.*;

/**
 * Dijkstra /Dynamic/: shortest path solver using Dijkstra + dynamic programming
 * to reuse previously discovered shortest paths.
 * <p>
 * This can be effective in problems when you need to search for the shortest path in the same
 * graph multiple times.
 * <p>
 * The idea is to hold all previously found shortest paths (therefore it requires additional O(n^2) memory,
 * where n - node), and re-use it as a cache when searching for a new path. It can also be reused
 * from the mid-point in a new path.
 * <p>
 * NB: This solver version works with only non-negative edge lengths.
 *
 * @author Arturs Licis
 */
public class DijkstraDynamicSolver {
	private Graph graph;
	private DijkstraDynamicStrategy solverStrategy = new DijkstraDynamicDistanceStrategy();

	public DijkstraDynamicSolver(Graph graph) {
		this.graph = graph;
		this.solverStrategy.reset(graph);
	}

	/**
	 * Finds shortest path between source and target node.
	 *
	 * @param source - source node in a path
	 * @param target - target node in a path
	 *
	 * @return {@link Path} that may contain {@link PathFragment}.
	 */
	public Path findShortestPath(Node source, Node target) {
		if (graph.isDirty()) {
			solverStrategy.reset(graph);
		}

		graph.getNodes().forEach(n -> {
			n.setDistance(Integer.MAX_VALUE);
			n.setPrevious(null);
		});
		source.setDistance(0);

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance).thenComparingInt(Node::getSeq));
		priorityQueue.add(source);

		PathElement finalElement = null;
		while (!priorityQueue.isEmpty()) {
			Node node = priorityQueue.poll();

			if (node != source) {
				solverStrategy.updateMinDistance(source, node);
			}
			if (node == target) {
				finalElement = node;
				break;
			}

			if ((finalElement = solverStrategy.getPathElement(node, target)) != null) break;

			for (Edge edge : node.getEdges()) {
				Node nextNode = edge.getTarget();
				int newDist = node.getDistance() + edge.getWeight();
				if (newDist < nextNode.getDistance()) {
					// new distance to 'nextNode' is less than currently found:
					// update distance, 'previous' reference and the 'nextNode' in queue
					nextNode.setDistance(newDist);
					nextNode.setPrevious(node);

					priorityQueue.remove(nextNode);
					priorityQueue.add(nextNode);
				}
			}
		}

		Path path = null;
		if (finalElement instanceof Node) {
			solverStrategy.updateMinDistance(source, target);
			path = buildPath(target);
		} else if (finalElement instanceof PathFragment){
			solverStrategy.updateMinDistance(source, ((PathFragment) finalElement).getSource());
			path = buildPath(((PathFragment) finalElement).getSource());
			path.getPathElements().add(finalElement);
		}

		return path;
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

	public void printCache() {
		solverStrategy.printCache();
	}

	public int getCachedDistance(Node sourceNode, Node targetNode) {
		return solverStrategy.getCachedDistance(sourceNode, targetNode);
	}
}

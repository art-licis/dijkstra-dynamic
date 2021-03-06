package today.jvm.graph.dijkstra.dynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import today.jvm.graph.core.Graph;
import today.jvm.graph.core.Node;
import today.jvm.graph.core.Path;
import today.jvm.graph.core.PathFragment;

/**
 * @author Arturs Licis
 */
public class DijkstraDynamicSolverTest {
	@Test
	public void testValidShortestPath() {
		Graph g = new Graph("Sample-1");
		Node n1 = g.addNode(new Node("1"));
		Node n2 = g.addNode(new Node("2"));
		Node n3 = g.addNode(new Node("3"));
		Node n4 = g.addNode(new Node("4"));
		Node n5 = g.addNode(new Node("5"));
		Node n6 = g.addNode(new Node("6"));

		g.createBidirectionalEdge(n1, n2, 14);
		g.createBidirectionalEdge(n1, n3, 18);
		g.createBidirectionalEdge(n1, n4, 12);
		g.createBidirectionalEdge(n2, n3, 3);
		g.createBidirectionalEdge(n2, n6, 5);
		g.createBidirectionalEdge(n3, n5, 2);
		g.createBidirectionalEdge(n4, n5, 2);
		g.createBidirectionalEdge(n5, n6, 3);

		DijkstraDynamicSolver solver = new DijkstraDynamicSolver(g);
		// n1 -> n3
		assertEquals(16, solver.findShortestPath(n1, n3).getDistance());

		// n1 -> n6
		Path shortestPathN1N6 = solver.findShortestPath(n1, n6);
		assertEquals(17, shortestPathN1N6.getDistance());
		assertEquals(n1, shortestPathN1N6.getPathElements().get(0));
		assertEquals(n4, shortestPathN1N6.getPathElements().get(1));
		assertEquals(n5, shortestPathN1N6.getPathElements().get(2));
		assertEquals(n6, shortestPathN1N6.getPathElements().get(3));

		Path shortestPathN1N5 = solver.findShortestPath(n1, n5);
		assertEquals(n1, shortestPathN1N5.getPathElements().get(0));
		assertEquals(true, shortestPathN1N5.getPathElements().get(1) instanceof PathFragment);

		solver.printCache();
	}

	@Test
	public void testLinearGraph() {
		Graph g = new Graph("Sample-1");
		Node n1 = g.addNode(new Node("1"));
		Node n2 = g.addNode(new Node("2"));
		Node n3 = g.addNode(new Node("3"));
		Node n4 = g.addNode(new Node("4"));
		Node n5 = g.addNode(new Node("5"));

		g.createBidirectionalEdge(n1, n2, 5);
		g.createBidirectionalEdge(n2, n3, 6);
		g.createBidirectionalEdge(n3, n4, 7);
		g.createBidirectionalEdge(n4, n5, 8);

		DijkstraDynamicSolver solver = new DijkstraDynamicSolver(g);
		Path firstPath = solver.findShortestPath(n1, n5);
		assertEquals(5, firstPath.getPathElements().size());

		assertEquals(13, solver.getCachedDistance(n2, n4));

		solver.printCache();
	}
}


package today.jvm.graph.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphCoreTest {
    @Test
    public void testBidirectionalEdge() {
        Graph g = new Graph("Sample-1");
        Node n1 = g.addNode(new Node("1"));
        Node n2 = g.addNode(new Node("2"));

        g.createBidirectionalEdge(n1, n2, 5);

        assertEquals(1, n1.getEdges().size());
        assertEquals(1, n2.getEdges().size());

        assertEquals(n2, n1.getEdges().get(0).getTarget());
        assertEquals(n1, n2.getEdges().get(0).getTarget());

        assertEquals(5, n1.getEdges().get(0).getWeight());
        assertEquals(5, n2.getEdges().get(0).getWeight());

        assertNotEquals(n1.getEdges().get(0), n2.getEdges().get(0));
    }
}

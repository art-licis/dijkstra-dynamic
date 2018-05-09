package today.jvm.graph.dijkstra.dynamic;

import today.jvm.graph.core.Graph;
import today.jvm.graph.core.Node;
import today.jvm.graph.core.PathElement;
import today.jvm.graph.core.PathFragment;

import java.util.Arrays;

/**
 * Dijkstra /Dynamic/ solver strategy to cache only distances.
 *
 * @author Arturs Licis
 */
public class DijkstraDynamicDistanceStrategy implements DijkstraDynamicStrategy {
    private Graph graph;
    private int[][] minDistance;

    @Override
    public void reset(Graph graph) {
        this.graph = graph;
        graph.buildNodeRefs();
        graph.resetDirty();

        if (minDistance == null || minDistance.length < graph.getNodeCount()) {
            minDistance = new int[graph.getNodeCount()][graph.getNodeCount()];
        } else {
            // avoid new memory allocation if nr. of nodes same or smaller
            Arrays.stream(minDistance).forEach(a -> Arrays.fill(a, 0));
        }
    }

    @Override
    public void updateMinDistance(Node sourceNode, Node targetNode) {
        int targetDistance = targetNode.getDistance();
        Node midNode = targetNode;
        do {
            minDistance[sourceNode.getSeq()][midNode.getSeq()] = midNode.getDistance();
            if (midNode != targetNode) minDistance[midNode.getSeq()][targetNode.getSeq()] = targetDistance - midNode.getDistance();
            midNode = midNode.getPrevious();
        } while (midNode != null);
    }

    @Override
    public PathElement getPathElement(Node sourceNode, Node targetNode) {
        if (minDistance[sourceNode.getSeq()][targetNode.getSeq()] == 0) return null;
        else return new PathFragment(sourceNode, targetNode, minDistance[sourceNode.getSeq()][targetNode.getSeq()]);
    }


    @Override
    public int getCachedDistance(Node sourceNode, Node targetNode) {
        if (sourceNode.getSeq() == targetNode.getSeq()) return 0;
        int cachedDistance = minDistance[sourceNode.getSeq()][targetNode.getSeq()];
        // TODO: consider setting -1 during array initialization for all cells except row==col
        return (cachedDistance == 0 ? -1 : cachedDistance);
    }

    @Override
    public void printCache() {
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

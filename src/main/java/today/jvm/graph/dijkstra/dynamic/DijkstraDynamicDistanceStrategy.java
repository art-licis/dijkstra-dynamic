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
        if (minDistance == null || minDistance.length != graph.getNodeCount()) {
            minDistance = new int[graph.getNodeCount()][graph.getNodeCount()];
        } else {
            // avoid new memory allocation if the size is the same
            for (int[] minDistanceLine : minDistance) {
                Arrays.fill(minDistanceLine, 0);
            }
        }
    }

    @Override
    public void updateMinDistance(Node sourceNode, Node targetNode) {
        do {
            minDistance[sourceNode.getSeq()][targetNode.getSeq()] = targetNode.getDistance();
            targetNode = targetNode.getPrevious();
        } while (targetNode != null);
    }

    @Override
    public PathElement getPathElement(Node sourceNode, Node targetNode) {
        if (minDistance[sourceNode.getSeq()][targetNode.getSeq()] == 0) return null;
        else return new PathFragment(sourceNode, targetNode, minDistance[sourceNode.getSeq()][targetNode.getSeq()]);
    }
}

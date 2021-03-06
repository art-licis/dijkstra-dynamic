package today.jvm.graph.dijkstra.dynamic;

import today.jvm.graph.core.Graph;
import today.jvm.graph.core.Node;
import today.jvm.graph.core.PathElement;

/**
 * Dijkstra /Dynamic/ solver strategy.
 *
 * @author Arturs Licis
 */
public interface DijkstraDynamicStrategy {
    /**
     * Reset all currently cached instances
     */
    void reset(Graph graph);

    /**
     * Updates cache min distance
     * @param sourceNode
     * @param targetNode
     */
    void updateMinDistance(Node sourceNode, Node targetNode);

    /**
     * Checks if there's a cached path between the provided source & target nodes.
     *
     * @return {@link PathElement} if this path was cached; null in case of cache miss
     */
    PathElement getPathElement(Node sourceNode, Node targetNode);

    /**
     * Returns cached distance between the provided source & target nodes.
     *
     * @return non-negative distance between the provided nodes; -1 if the distance is not cached
     */
    int getCachedDistance(Node sourceNode, Node targetNode);

    /**
     * Utility method to print cache.
     */
    void printCache();
}

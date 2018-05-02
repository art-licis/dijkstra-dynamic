# Dijkstra /Dynamic/

Implementation of Dijkstra's Algorithm with dynamic optimizations to reuse previously found shortest distances when performing multiple searches.

## Approach

As we move away from the source node, all intermediate targets that are guaranteed to have minimum path can be stored for a later re-use. At the same time, we can re-use previously found minimum distance as we make a new search.

This can be particularly effective when we are doing multiple (and possibly recurrent) searches in a graph big enough to avoid pre-calculation of distances between all nodes. The algorithm tries to provide a sweet spot between per-request search and full pre-calculation.

Graph modification would require invalidation of cached results; however, it can be proved that in some cases pre-stored cache can be modified accordingly, or at least invalidating only part of it.

## Implementation Details

The current implementation uses adjacency list to store graph, and an additional 'shortest distance matrix' to store previously found distances, thus additionally requiring O(n^2) space. However, this can be avoided using dictionary-based implementation, which would also allow using space-constraint and even smart eviction.

Whenever a node is removed from a priority queue, it is guaranteed to have a minimum distance, and therefore shortest distance matrix can be updated accordingly.

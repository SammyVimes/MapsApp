package ru.elweb.mapsapp.core.algorithm;

import ru.elweb.mapsapp.core.map.node.Branch;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.List;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */

/**
 * Abstract factory of algorithm specific objects
 */
public interface Factory {

    /**
     * Create map node with only one neighbour
     * @param id identifier of the node
     * @param node neighbour node
     * @param length length of a branch between nodes
     * @return node
     */
    MapNode newMapNode(final int id, final MapNode node, final int length);



    /**
     * Create map node with list of neighbours
     * @param id identifier of the node
     * @param linkedNodes branches to neighbours nodes
     * @param DEAD_END is this the last in a row
     * @return node
     */
    MapNode newMapNode(int id, List<Branch> linkedNodes, boolean DEAD_END);

    /**
     * Creating empty node
     * @param id identifier of the node
     * @param DEAD_END is this the last in a row
     * @return node
     */
    MapNode newMapNode(final int id, final boolean DEAD_END);

    MapSearchAlgorithm getSearchAlgorithm();

}

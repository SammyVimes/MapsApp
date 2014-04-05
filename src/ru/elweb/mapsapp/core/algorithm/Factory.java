package ru.elweb.mapsapp.core.algorithm;

import ru.elweb.mapsapp.core.map.node.Branch;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.List;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public interface Factory {

    MapNode newMapNode(final int id, final MapNode node, final int length);
    MapNode newMapNode(int id, List<Branch> linkedNodes, boolean DEAD_END);
    MapNode newMapNode(final int id, final boolean DEAD_END);

    MapSearchAlgorithm getSearchAlgorithm();

}

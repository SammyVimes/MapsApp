package ru.elweb.mapsapp.core.algorithm.dijkstra;

import ru.elweb.mapsapp.core.algorithm.Factory;
import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.map.node.DijkstraMapNode;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.List;

/**
 * Created by Администратор on 05.04.2014.
 */
public class DijkstraFactory implements Factory {

    @Override
    public MapNode newMapNode(int id, MapNode node) {
        return new DijkstraMapNode(id, node);
    }

    @Override
    public MapNode newMapNode(int id, List<MapNode> linkedNodes, boolean DEAD_END) {
        return new DijkstraMapNode(id, linkedNodes, DEAD_END);
    }

    @Override
    public MapNode newMapNode(int id, boolean DEAD_END) {
        return new DijkstraMapNode(id, DEAD_END);
    }

    @Override
    public MapSearchAlgorithm getSearchAlgorithm() {
        return new Dijkstra();
    }

}

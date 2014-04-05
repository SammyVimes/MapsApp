package ru.elweb.mapsapp.core.map.node;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraData;

import java.util.List;

/**
 * Created by Администратор on 05.04.2014.
 */
public class DijkstraMapNode extends MapNode {

    ThreadLocal<DijkstraData> dijkstraData;

    public DijkstraMapNode(int id, boolean DEAD_END) {
        super(id, DEAD_END);
    }

    public DijkstraMapNode(int id, List<MapNode> linkedNodes, boolean DEAD_END) {
        super(id, linkedNodes, DEAD_END);
    }

    public DijkstraMapNode(int id, MapNode node) {
        super(id, node);
        dijkstraData = new ThreadLocal<>(); //because in the end above constructors calls this constructor
    }

}

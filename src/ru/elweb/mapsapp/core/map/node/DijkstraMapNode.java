package ru.elweb.mapsapp.core.map.node;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraData;

import java.util.List;

/**
 * Created by Администратор on 05.04.2014.
 */
public class DijkstraMapNode extends MapNode {

    ThreadLocal<DijkstraData> dijkstraData;

    public DijkstraMapNode(final int id, final boolean DEAD_END) {
        super(id, DEAD_END);
    }

    public DijkstraMapNode(final int id, final List<Branch> branches, final boolean DEAD_END) {
        super(id, branches, DEAD_END);
    }

    public DijkstraMapNode(int id, MapNode node, final int length) {
        super(id, node, length);
        dijkstraData = new ThreadLocal<>(); //because in the end above constructors calls this constructor
    }

}

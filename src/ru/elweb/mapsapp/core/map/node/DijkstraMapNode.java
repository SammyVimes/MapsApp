package ru.elweb.mapsapp.core.map.node;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraData;

import java.util.List;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public class DijkstraMapNode extends MapNode {

    ThreadLocal<DijkstraData> dijkstraData;

    public DijkstraMapNode(final int id, final boolean DEAD_END) {
        super(id, DEAD_END);
        dijkstraData = new DisjkstraThreadLocal(); //because in the end below constructors calls this constructor
    }

    public DijkstraMapNode(final int id, final List<Branch> branches, final boolean DEAD_END) {
        super(id, branches, DEAD_END);
    }

    public DijkstraMapNode(int id, MapNode node, final int length) {
        super(id, node, length);
    }

    public DijkstraData getDijkstraData() {
        return dijkstraData.get();
    }

    public void removeDijkstraData() {
        this.dijkstraData.remove();
    }

    private class DisjkstraThreadLocal extends ThreadLocal<DijkstraData> {

        @Override
        protected DijkstraData initialValue() {
            return new DijkstraData();
        }

    }

}

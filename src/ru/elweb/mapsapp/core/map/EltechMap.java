package ru.elweb.mapsapp.core.map;

import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.LinkedList;
import java.util.List;

/**
 * It's not specifical for LETI, but still, this app is made for eltech so...
 * This class has self-described methods
 */
public class EltechMap {

    private List<MapNode> nodes;

    public EltechMap() {
        nodes = new LinkedList<>();
    }

    public void addNode(final MapNode node) {
        this.nodes.add(node);
    }

    public List<MapNode> getNodes() {
        return nodes;
    }

    public MapNode getNodeById(final int id) {
        for (MapNode node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

}

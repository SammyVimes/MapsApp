package ru.elweb.mapsapp.core.map;

import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.LinkedList;
import java.util.List;

public class Path {

    private List<MapNode> nodes;

    public Path() {
        nodes = new LinkedList<>();
    }

    public void addNode(final MapNode node) {
        this.nodes.add(node);
    }

    public void removeNodeById(final int id) {
        for (MapNode node : nodes) {
            if (node.getId() == id) {
                nodes.remove(node);
                break;
            }
        }
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = nodes.size() - 1; i >= 0; i--) {
            out += nodes.get(i).getId();
            if (i != 0) {
                out += " -> ";
            }
        }
        return out;
    }

    public List<MapNode> getNodes() {
        return nodes;
    }

}

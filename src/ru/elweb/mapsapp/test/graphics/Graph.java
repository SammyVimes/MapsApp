package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.node.Branch;
import ru.elweb.mapsapp.core.map.node.MapNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semyon Danilov on 11.04.2014.
 */
public class Graph {

    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public int getVerticesQuantity() {
        return vertices.size();
    }

    public static Graph fromEltechMap(final EltechMap eltechMap) {
        List<MapNode> nodes = eltechMap.getNodes();
        Graph graph = new Graph();
        List<Vertex> vertexList = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();
        for (MapNode mapNode : nodes) {
            Vertex vertex = new Vertex();
            vertexList.add(vertex);
            int id1 = mapNode.getId();
            vertex.id = id1;
            List<Branch> branches = mapNode.getBranches();
            for (Branch branch : branches) {
                int id2 = branch.getNode().getId();
                boolean shouldCreateNewEdge = true;
                for (Edge edge : edgeList) {
                    if ((edge.first.id == id1 && edge.second.id == id2)
                            || (edge.first.id == id2 && edge.second.id == id1)) {
                        shouldCreateNewEdge = false;
                        edge.second = vertex;
                        break;
                    }
                }
                if (shouldCreateNewEdge) {
                    Edge edge = new Edge();
                    edge.weight = branch.getLength();
                    edge.first = vertex;
                    edge.second = new Vertex();
                    edge.second.id = id2;
                    edgeList.add(edge);
                }
            }
        }
        graph.setVertices(vertexList);
        graph.setEdges(edgeList);
        return graph;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(final List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(final List<Edge> edges) {
        this.edges = edges;
    }
}

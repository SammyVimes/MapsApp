package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.core.map.EltechMap;

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
        return null;
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

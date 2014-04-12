package ru.elweb.mapsapp.test.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Semyon Danilov on 11.04.2014.
 */
public final class TestGraphBuilder {

    public static Graph createGraph() {
        Graph graph = new Graph();
        Random random = new Random();
        int n = 10;
        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            Vertex vertex = new Vertex();
            vertex.x = (int) (200 + (random.nextFloat() * 300));
            vertex.y = (int) (100 + (random.nextFloat() * 200));
            vertices.add(vertex);
            vertex.id = i;
        }
        for (int i = 0; i < 20; i++) {
            int a = (int) (random.nextFloat() * n);
            int b = (int) (random.nextFloat() * n);
            if (vertices.size() <= a || vertices.size() <= b) {
                continue;
            }
            Vertex aV = vertices.get(a);
            Vertex bV = vertices.get(b);
            boolean shouldSkip = false;
            for (int j = 0; j < edges.size(); j++) {
                Edge e = edges.get(j);
                if ((e.first == aV && e.second == bV) || (e.first == bV && e.second == aV)) {
                    shouldSkip = true;
                    break;
                }
            }
            if (a == b || shouldSkip) {
                continue;
            }
            Edge e = new Edge();
            e.first = aV;
            e.second = bV;
            edges.add(e);
        }
        graph.setEdges(edges);
        graph.setVertices(vertices);
        return graph;
    }

}

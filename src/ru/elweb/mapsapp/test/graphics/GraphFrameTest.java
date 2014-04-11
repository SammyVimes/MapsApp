package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.test.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Semyon Danilov on 11.04.2014.
 */
public class GraphFrameTest implements Test {

    GraphFrame frame = null;

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            frame = new GraphFrame(TestGraphBuilder.createGraph());
            frame.setVisible(true);
        });
    }

    public static class GraphFrame extends JFrame {

        private boolean needToUpdate = true;
        private Graph graph;

        Timer updateTimer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                paint(getGraphics());
                if (needToUpdate) {
                    updateTimer.restart();
                }
            }

        });

        private void updateGraph() {
            List<Vertex> vertices = graph.getVertices();
            List<Edge> edges = graph.getEdges();
            int verticesQuantity = graph.getVerticesQuantity();
            int edgesQuantity = graph.getEdges().size(); //todo: incapsulate
            for (int i = 0; i < graph.getVerticesQuantity(); i++) {
                Vertex vertex = vertices.get(i);
                vertex.netForceX = 0;
                vertex.netForceY = 0;
                for (int j = 0; j < verticesQuantity; j++) {
                    if (i == j) {
                        continue;
                    }
                    Vertex u = vertices.get(j);
                    float rsq = ((vertex.x - u.x) * (vertex.x - u.x) + (vertex.y - u.y) * (vertex.y - u.y));
                    vertex.netForceX += 200 * (vertex.x - u.x) / rsq;
                    vertex.netForceY += 200 * (vertex.y - u.y) / rsq;
                }
                for (int j = 0; j < edgesQuantity; j++) {
                    Edge edge = edges.get(j);
                    if (edge.first != vertex) {
                        continue;
                    }
                    Vertex u = edge.second;
                    vertex.netForceX += (u.x - vertex.x) * 0.06;
                    vertex.netForceY += (u.y - vertex.y) * 0.06;
                }
                vertex.velocityX = (vertex.velocityX + vertex.netForceX) * 0.85;
                vertex.velocityY= (vertex.velocityY + vertex.netForceY) * 0.85;
            }
            for (int i = 0; i < verticesQuantity; i++) {
                Vertex vertex = vertices.get(i);
                if (vertex.isDragged) {
                    int a = 0;
                } else {
                    vertex.x += vertex.velocityX;
                    vertex.y += vertex.velocityY;
                }
            }
        }

        private void redraw(Graphics graphics) {
            List<Vertex> vertices = graph.getVertices();
            List<Edge> edges = graph.getEdges();
            int verticesQuantity = graph.getVerticesQuantity();
            int edgesQuantity = graph.getEdges().size(); //todo: incapsulate
            for (int i = 0; i < graph.getVerticesQuantity(); i++) {
                Vertex v = vertices.get(i);
                graphics.drawOval(v.x, v.y, 20, 20);
            }
        }

        public void paint(Graphics g) {
            super.paint(g);
            updateGraph();
            redraw(g);
        }

        private Canvas canvas;

        public GraphFrame(final Graph graph) {
            super("Graph Frame");
            this.graph = graph;
//            this.canvas = new Canvas();
//            Dimension size = new Dimension (500, 500);
//            this.canvas.setPreferredSize(size);
//            this.add(canvas);
            this.setPreferredSize(new Dimension (500, 500));
            this.pack();
            updateTimer.start();
        }


    }

}

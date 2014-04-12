package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.test.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

    public static class GraphFrame extends JFrame implements MouseListener, MouseMotionListener {

        private boolean needToUpdate = true;
        private Graph graph;
        Timer updateTimer = null;

        private int diameter = 20;

        @Override
        public void repaint() {
            super.repaint();
        }

        @Override
        public void repaint(final int x, final int y, final int width, final int height) {
            super.repaint(x, y, width, height);
        }

        @Override
        public void repaint(final long time, final int x, final int y, final int width, final int height) {
            super.repaint(time, x, y, width, height);
        }

        private void updateGraph() {
            List<Vertex> vertices = graph.getVertices();
            List<Edge> edges = graph.getEdges();
            int verticesQuantity = graph.getVerticesQuantity();
            int edgesQuantity = graph.getEdges().size(); //todo: incapsulate
            for (int i = 0; i < graph.getVerticesQuantity(); i++) {
                Vertex v = vertices.get(i);
                v.netForceX = 0;
                v.netForceY = 0;
                for (int j = 0; j < verticesQuantity; j++) {
                    if (i == j) {
                        continue;
                    }
                    Vertex u = vertices.get(j);
                    double rsq = ((v.x - u.x) * (v.x - u.x) +(v.y - u.y) * (v.y - u.y));
                    v.netForceX += (200 * (v.x - u.x)) / rsq;
                    v.netForceY += (200 * (v.y - u.y)) / rsq;
                }
                for (int j = 0; j < edgesQuantity; j++) {
                    Edge edge = edges.get(j);
                    Vertex u = null;
                    if (edge.first == v) {
                        u = edge.second;
                    } else if (edge.second == v) {
                        u = edge.first;
                    } else {
                        continue;
                    }
                    v.netForceX += 0.06 * (u.x - v.x);
                    v.netForceY += 0.06 * (u.y - v.y);
                }
                v.velocityX = (v.velocityX + v.netForceX) * 0.85;
                v.velocityY= (v.velocityY + v.netForceY) * 0.85;
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

        public GraphFrame(final Graph graph) {
            super("Graph Frame");
            updateTimer = new Timer(50, e -> {
                repaint();
                if (needToUpdate) {
                    updateTimer.restart();
                }
            });
            this.graph = graph;
            this.setPreferredSize(new Dimension (500, 500));
            this.pack();
            TestPanel graphPanel = new TestPanel();
            graphPanel.graphics = getGraphics();
            graphPanel.setVisible(true);
            this.add(graphPanel);
            updateTimer.start();
        }

        private Vertex getPressedVertex(final int x, final int y) {
            for (Vertex v : graph.getVertices()) {
                if ((x > v.x && x < v.x + diameter) && (y > v.y && y < v.y + diameter)) {
                    return v;
                }
            }
            return null;
        }

        class TestPanel extends JPanel {

            protected Graphics graphics;

            public TestPanel() {
                addMouseListener(GraphFrame.this);
                addMouseMotionListener(GraphFrame.this);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                updateGraph();
                redraw(g);
            }

            private void redraw(Graphics graphics) {
                List<Vertex> vertices = graph.getVertices();
                List<Edge> edges = graph.getEdges();
                int verticesQuantity = graph.getVerticesQuantity();
                int edgesQuantity = graph.getEdges().size(); //todo: incapsulate
                graphics.setColor(Color.BLACK);
                for (int i = 0; i < edges.size(); i++) {
                    Edge edge = edges.get(i);
                    Vertex v = edge.first;
                    Vertex u = edge.second;
                    graphics.drawLine((int) v.x + (diameter/2),
                            (int) v.y + (diameter/2),
                            (int) u.x + (diameter/2),
                            (int) u.y + (diameter/2));
                }
                graphics.setColor(Color.RED);
                for (int i = 0; i < graph.getVerticesQuantity(); i++) {
                    Vertex v = vertices.get(i);
                    graphics.fillOval((int) v.x, (int) v.y, diameter, diameter);
                }
            }

        }

        Vertex pressed = null;
        int initPressX = 0;
        int initPressY = 0;
        int initVX = 0;
        int initVY = 0;

        @Override
        public void mouseMoved(final MouseEvent e) {
        }

        @Override
        public void mouseClicked(final MouseEvent e) {

        }

        @Override
        public void mousePressed(final MouseEvent e) {
            Vertex v = getPressedVertex(e.getX(), e.getY());
            if (v == null) {
                return;
            }
            v.isDragged = true;
            pressed = v;
            initPressX = e.getX();
            initPressY = e.getY();
            initVX = (int) v.x;
            initVY = (int) v.y;
        }

        @Override
        public void mouseDragged(final MouseEvent e) {
            if (pressed == null) {
                return;
            }
            int newX = e.getX() - initPressX;
            int newY = e.getY() - initPressY;
            pressed.x = initVX + newX;
            pressed.y = initVY + newY;
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            if (pressed == null) {
                return;
            }
            pressed.isDragged = false;
            pressed = null;
        }

        @Override
        public void mouseEntered(final MouseEvent e) {

        }

        @Override
        public void mouseExited(final MouseEvent e) {

        }
    }

}

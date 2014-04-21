package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.map.node.MapNode;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.MapRequestImpl;
import ru.elweb.mapsapp.test.Test;
import ru.elweb.mapsapp.test.TestMapBuilder;

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
            EltechMap map = TestMapBuilder.buildSimpleMap();
            frame = new GraphFrame(Graph.fromEltechMap(map));
            frame.setVisible(true);
            MapRequest mapRequest = new MapRequestImpl(1, 6);
            PathFindingThread pathFindingThread = new PathFindingThread(map, mapRequest, frame);
            pathFindingThread.start();
        });
    }

    private class PathFindingThread extends Thread {

        private EltechMap map = null;
        private MapRequest mapRequest = null;
        private GraphFrame frame = null;


        public PathFindingThread(final EltechMap map, final MapRequest mapRequest, final GraphFrame frame) {
            this.map = map;
            this.mapRequest = mapRequest;
            this.frame = frame;
        }

        @Override
        public void run() {
            DijkstraAlgorithm algorithm = new DijkstraAlgorithm();
            Path path = algorithm.findPath(map, mapRequest.getFromID(), mapRequest.getToID());
            frame.addPath(path);
        }
    }

    //TODO: add method to insert graph with path and make it synchronized
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

        private synchronized void addPath(final Path path) {
            List<MapNode> nodes = path.getNodes();
            for (int i = 0; i < nodes.size() - 1; i++) {
                MapNode first = nodes.get(i);
                MapNode second = nodes.get(i + 1);
                List<Edge> edgeList = graph.getEdges();
                for (Edge edge : edgeList) {
                    if ((edge.first.id == first.getId() && edge.second.id == second.getId())
                            || (edge.first.id == second.getId() && edge.second.id == first.getId())) {
                        edge.checked = true;
                    }
                }
            }
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
                synchronized (GraphFrame.this) {
                    updateGraph();
                    redraw(g);
                }
            }

            private void redraw(Graphics graphics) {
                List<Vertex> vertices = graph.getVertices();
                List<Edge> edges = graph.getEdges();
                int verticesQuantity = graph.getVerticesQuantity();
                int edgesQuantity = graph.getEdges().size(); //todo: incapsulate
                for (Edge edge : edges) {
                    Vertex v = edge.first;
                    Vertex u = edge.second;
                    if (edge.checked) {
                        graphics.setColor(Color.RED);
                    } else {
                        graphics.setColor(Color.BLACK);
                    }
                    graphics.drawLine((int) v.x + (diameter / 2),
                            (int) v.y + (diameter / 2),
                            (int) u.x + (diameter / 2),
                            (int) u.y + (diameter / 2));
                    int gX = (int) v.x;
                    int mX = (int) u.x;
                    int gY = (int) v.y;
                    int mY = (int) u.y;
                    if (mX > gX) {
                        int tmp = gX;
                        gX = mX;
                        mX = tmp;
                    }
                    if (mY > gY) {
                        int tmp = gY;
                        gY = mY;
                        mY = tmp;
                    }
                    String weight = edge.weight + "";
                    char[] chars = weight.toCharArray();
                    graphics.drawChars(chars, 0, weight.length(), mX + ((gX - mX) / 2), mY + ((gY - mY) / 2));
                }
                for (int i = 0; i < graph.getVerticesQuantity(); i++) {
                    Vertex v = vertices.get(i);
                    graphics.setColor(Color.RED);
                    graphics.fillOval((int) v.x, (int) v.y, diameter, diameter);
                    graphics.setColor(Color.BLACK);
                    String id = ("" + v.id);
                    char[] chars = id.toCharArray();
                    graphics.drawChars(chars, 0, id.length(), (int) v.x + (diameter / 2) - 5, (int) v.y + (diameter / 2) + 5);
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

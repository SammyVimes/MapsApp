package ru.elweb.mapsapp.test.graphics;

import ru.elweb.mapsapp.core.algorithm.dijkstra.DijkstraAlgorithm;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;
import ru.elweb.mapsapp.core.map.node.MapNode;
import ru.elweb.mapsapp.core.server.MapRequest;
import ru.elweb.mapsapp.core.server.MapRequestImpl;
import ru.elweb.mapsapp.core.util.Logger;
import ru.elweb.mapsapp.test.Test;
import ru.elweb.mapsapp.test.TestMapBuilder;
import sun.util.logging.resources.logging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Created by Semyon Danilov on 11.04.2014.
 */
public class GraphFrameTest implements Test {

    GraphFrame frame = null;
    Logger logger = Logger.getLogger(GraphFrameTest.class);

    @Override
    public void run() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        EltechMap map = TestMapBuilder.buildHardMap();
                        frame = new GraphFrame(map, Graph.fromEltechMap(map));
                        frame.setVisible(true);
                    }
        });
//        /**
//         * for test purposes, после теста удалить
//         */
//        EltechMap map = TestMapBuilder.buildHardMap();
//
//        PathFindingThread pft = new PathFindingThread(map, new MapRequestImpl(6, 750), null);
//        pft.start();
    }

    private static class PathFindingThread extends Thread {

        Logger logger = Logger.getLogger(PathFindingThread.class);


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
            if (path != null) {
                logger.log("Path: " + path.toString());
                frame.addPath(path);
            }
        }
    }

    public static class GraphFrame extends JFrame implements MouseListener, MouseMotionListener {

        private boolean needToUpdate = true;
        private Graph graph;
        private EltechMap map;
        Timer updateTimer = null;

        private int diameter = 20;

        private JTextField from;
        private JTextField to;
        private JLabel jLabel1;
        private JLabel status;


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
            List<Edge> edgeList = graph.getEdges();
            for (Edge edge : edgeList) {
                edge.checked = false;
            }
            for (int i = 0; i < nodes.size() - 1; i++) {
                MapNode first = nodes.get(i);
                MapNode second = nodes.get(i + 1);
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

        public GraphFrame(final EltechMap map, final Graph graph) {
            super("Graph Frame");
            updateTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    repaint();
                    if (needToUpdate) {
                        updateTimer.restart();
                    }
                }
            });
            this.map = map;
            this.graph = graph;
            this.setPreferredSize(new Dimension(500, 500));
            init();
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
                this.setBackground(Color.WHITE);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

        private void init() {
            from = new JTextField();
            to = new JTextField();
            status = new JLabel();
            jLabel1 = new JLabel();
            JButton button = new JButton("Найти путь");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    Integer fromId =  Integer.valueOf(from.getText());
                    Integer toId =  Integer.valueOf(to.getText());
                    MapRequest mapRequest = new MapRequestImpl(fromId, toId);
                    PathFindingThread pathFindingThread = new PathFindingThread(map, mapRequest, GraphFrame.this);
                    pathFindingThread.start();
                }
            });
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            TestPanel graphPanel = new TestPanel();
            graphPanel.graphics = getGraphics();
            graphPanel.setVisible(true);

            jLabel1.setText("Введите ID начальной точки и ID конечной:");

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);

            //Create a parallel group for the horizontal axis
            GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

            //Create a sequential and a parallel groups
            GroupLayout.SequentialGroup h1 = layout.createSequentialGroup();
            GroupLayout.ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

            //Add a container gap to the sequential group h1
            h1.addContainerGap();

            //Add a scroll pane and a label to the parallel group h2
            h2.addComponent(graphPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);
            h2.addComponent(status, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE);

            //Create a sequential group h3
            GroupLayout.SequentialGroup h3 = layout.createSequentialGroup();
            h3.addComponent(jLabel1);
            h3.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            h3.addComponent(from, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
            h3.addComponent(to, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);
            h3.addComponent(button, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE);

            //Add the group h3 to the group h2
            h2.addGroup(h3);
            //Add the group h2 to the group h1
            h1.addGroup(h2);

            h1.addContainerGap();

            //Add the group h1 to the hGroup
            hGroup.addGroup(GroupLayout.Alignment.TRAILING, h1);
            //Create the horizontal group
            layout.setHorizontalGroup(hGroup);


            //Create a parallel group for the vertical axis
            GroupLayout.ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            //Create a sequential group v1
            GroupLayout.SequentialGroup v1 = layout.createSequentialGroup();
            //Add a container gap to the sequential group v1
            v1.addContainerGap();
            //Create a parallel group v2
            GroupLayout.ParallelGroup v2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            v2.addComponent(jLabel1);
            v2.addComponent(from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            v2.addComponent(to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            v2.addComponent(button, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
            //Add the group v2 tp the group v1
            v1.addGroup(v2);
            v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            v1.addComponent(graphPanel, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE);
            v1.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            v1.addComponent(status);
            v1.addContainerGap();

            //Add the group v1 to the group vGroup
            vGroup.addGroup(v1);
            //Create the vertical group
            layout.setVerticalGroup(vGroup);
            pack();
        }

    }



}

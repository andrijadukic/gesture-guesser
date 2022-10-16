package neural.gui.shared;

import neural.gui.util.IntPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingBoard extends JPanel implements DrawingCreator {

    private final List<IntPoint> points;
    private final List<DrawingListener> drawingListeners;

    public DrawingBoard() {
        points = new ArrayList<>();
        drawingListeners = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                IntPoint firstPoint = new IntPoint(e.getX(), e.getY());
                points.add(firstPoint);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                points.add(new IntPoint(e.getX(), e.getY()));
                repaint();
                notifyDrawingListeners();

                points.clear();
                repaint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(new IntPoint(e.getX(), e.getY()));
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 1, n = points.size(); i < n; i++) {
            IntPoint p1 = points.get(i - 1);
            IntPoint p2 = points.get(i);

            g2d.drawLine(p1.x(), p1.y(), p2.x(), p2.y());
        }
    }

    @Override
    public void addDrawingListener(DrawingListener listener) {
        drawingListeners.add(listener);
    }

    @Override
    public void removeDrawingListener(DrawingListener listener) {
        drawingListeners.remove(listener);
    }

    @Override
    public void notifyDrawingListeners() {
        drawingListeners.forEach(listener -> listener.onDrawingEnd(new ArrayList<>(points)));
    }
}

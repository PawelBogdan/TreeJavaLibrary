package pl.edu.uj.ii.utils;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DiscreteLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import pl.edu.misztal.data.Point;

/**
 * @author Paweł Bogdan
 * Na podstawie: https://github.com/kmisztal/CEC/blob/master/src/main/java/cec/input/draw/DataDraw.java
 */
public class Plot extends JFrame {
    final List<List<Point>> data;
    final List<Color> colors;
    final List<Rectangle> rectangles;
    final List<Color> rectangleColors;

    public Plot() {
        this.data = new ArrayList<>();
        this.colors = new ArrayList<>();
        this.rectangles = new ArrayList<>();
        this.rectangleColors = new ArrayList<>();
    }

    public void addPoints(List<Point> points, Color color) {
        data.add(points);
        colors.add(color);
    }

    public void addRectangle(Rectangle rectangle, Color color) {
        rectangles.add(rectangle);
        rectangleColors.add(color);
    }

    public void disp() {
        disp("");
    }

    public void disp(String title) {
        DataTable[] dataTables = new DataTable[data.size() + rectangles.size()];
        for (int i = 0; i < data.size(); ++i) {
            DataTable temp = new DataTable(Double.class, Double.class);
            data.get(i).forEach(p -> temp.add(p.get(0), p.get(1)));
            dataTables[i] = temp;
        }

        for (int i = data.size(), j = 0; j < rectangles.size(); i++, j++) {
            DataTable temp = new DataTable(Double.class, Double.class);
            Rectangle r = rectangles.get(j);
            temp.add(r.getX1(), r.getY1());
            temp.add(r.getX1(), r.getY2());
            temp.add(r.getX2(), r.getY2());
            temp.add(r.getX2(), r.getY1());
            temp.add(r.getX1(), r.getY1());
            dataTables[i] = temp;
        }

        XYPlot plot = new XYPlot(dataTables);

        for (int i = 0; i < data.size(); ++i) {
            DataSource s = plot.get(i);
            PointRenderer points1 = new DefaultPointRenderer2D();
            points1.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
            points1.setColor(colors.get(i));
            plot.setPointRenderers(s, points1);
        }

        for (int i = data.size(), j = 0; j < rectangles.size(); i++, j++) {
            DataSource s = plot.get(i);
            LineRenderer points2 = new DiscreteLineRenderer2D();
//            points2.setShape(new Rectangle2D.Double(rectangles.get(j).getX1(), rectangles.get(j).getY1(),rectangles.get(j).getHeight(), rectangles.get(j).getWidth()));
            points2.setColor(rectangleColors.get(j));
            plot.setLineRenderers(s, points2);
        }

        // Style the plot
        double insetsTop = 20.0,
                insetsLeft = 60.0,
                insetsBottom = 60.0,
                insetsRight = 40.0;
        plot.setInsets(new Insets2D.Double(
                insetsTop, insetsLeft, insetsBottom, insetsRight));
        plot.getTitle().setText(title);

        // Style the plot area
        plot.getPlotArea().setBorderColor(new Color(0.0f, 0.3f, 1.0f));
        plot.getPlotArea().setBorderStroke(new BasicStroke(2f));

        // Style axes
        plot.getAxisRenderer(XYPlot.AXIS_X).setLabel(new de.erichseifert.gral.graphics.Label("X"));
        plot.getAxisRenderer(XYPlot.AXIS_Y).setLabel(new de.erichseifert.gral.graphics.Label("Y"));
        plot.getAxisRenderer(XYPlot.AXIS_X).setTickSpacing(1.0);
        plot.getAxisRenderer(XYPlot.AXIS_Y).setTickSpacing(2.0);
        plot.getAxisRenderer(XYPlot.AXIS_X).setIntersection(-Double.MAX_VALUE);
        plot.getAxisRenderer(XYPlot.AXIS_Y).setIntersection(-Double.MAX_VALUE);

        // Display on screen
        this.getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(getContentPane().getMinimumSize());
        this.setSize(550, 550);
        this.setVisible(true);
    }

}

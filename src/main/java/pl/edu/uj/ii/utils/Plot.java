package pl.edu.uj.ii.utils;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Scanner;

/**
 * @author Paweł Bogdan
 * Na podstawie: https://github.com/kmisztal/CEC/blob/master/src/main/java/cec/input/draw/DataDraw.java
 */
public class Plot extends JFrame {
    final List<Point> points;
    final Color color;

    public Plot(List<Point> points, Color color) {
        this.points = points;
        this.color = color;
    }

    public void disp() {
        disp("");
    }

    public void disp(String title) {
        DataTable dataTable = new DataTable(Double.class, Double.class);
        points.forEach(p -> dataTable.add(p.get(0), p.get(1)));
        XYPlot plot = new XYPlot(dataTable);

        PointRenderer points1 = new DefaultPointRenderer2D();
        points1.setShape(new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        points1.setColor(color);
        plot.setPointRenderers(plot.get(0), points1);

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
        Scanner sc = new Scanner(System.in);
        sc.next();
    }

}

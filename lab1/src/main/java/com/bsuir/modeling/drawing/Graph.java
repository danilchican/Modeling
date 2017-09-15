package com.bsuir.modeling.drawing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Graph {
    protected JFrame frame;

    XYSeries series;
    XYSeriesCollection dataset;

    static final String WIDTH = "width";
    static final String HEIGHT = "height";

    /**
     * Constructor to create new graph.
     *
     * @param graphTitle the name of the graph
     */
    public Graph(String graphTitle) {
        frame = new JFrame(graphTitle);
        series = new XYSeries("Data");

        this.setDefaultSettings();
    }

    protected void draw(String graphTitle, HashMap<String, Integer> size) {
        final JFreeChart chart = ChartFactory.createXYBarChart(
                graphTitle, "X", false, "Y", dataset,
                PlotOrientation.VERTICAL, false, false, false);

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(size.get(WIDTH), size.get(HEIGHT)));
        frame.setContentPane(chartPanel);
    }

    /**
     * Show the Graph on the screen.
     */
    public void show() {
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    /**
     * Set default settings for the graph.
     */
    private void setDefaultSettings() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}

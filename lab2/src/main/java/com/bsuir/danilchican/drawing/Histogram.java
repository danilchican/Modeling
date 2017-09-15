package com.bsuir.danilchican.drawing;

import com.bsuir.danilchican.generator.Generator;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.HashMap;
import java.util.List;

public class Histogram extends Graph {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 470;

    /**
     * Constructor to create new graph.
     *
     * @param graphTitle the name of the graph
     * @param yAxis      set of values for yAxis
     */
    public Histogram(String graphTitle, List<Double> yAxis) {
        super(graphTitle);

        setData(yAxis);

        HashMap<String, Integer> size = new HashMap<String, Integer>() {{
            put(WIDTH, DEFAULT_WIDTH);
            put(HEIGHT, DEFAULT_HEIGHT);
        }};

        draw(graphTitle, size);
    }

    private void setData(List<Double> yAxis) {
        for (int i = 1; i < Generator.INTERVAL_COUNT + 1; i++) {
            series.add(i, yAxis.get(i - 1));
        }

        dataset = new XYSeriesCollection(series);
    }
}

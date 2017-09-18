package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Generator {

    /**
     * Constants.
     */
    public static final int INTERVAL_COUNT = 20;
    private static final int VALUES_COUNT = 1_000_000;
    static final int N_POW = 2;

    private boolean isHistogramCalculated = false;

    /**
     * Storage of generated values.
     */
    List<Double> values;
    private List<Double> xAxis;
    private List<Double> yAxis;

    /**
     * Input manager.
     */
    Scanner in;

    /**
     * Generator's histogram.
     */
    Graph histogram;

    /**
     * Default constructor.
     */
    Generator() {
        this.values = new ArrayList<>();
        this.xAxis = new ArrayList<>();
        this.yAxis = new ArrayList<>();

        this.in = new Scanner(System.in);
    }

    /**
     * Calculate Mean value.
     *
     * @return Mx
     */
    public abstract double calculateMx();

    /**
     * Calculate Dispersion value.
     *
     * @return Dx
     */
    public abstract double calculateDx();

    /**
     * Print generator data.
     */
    public void print() {
        Printer.print("Histogram data: " + getHistogramData());

        Printer.print("Mx: " + calculateMx());
        Printer.print("Dx: " + calculateDx());
        Printer.print("Std: " + calculateStd());
    }

    /**
     * Generate numbers.
     */
    public void generate() {
        values.clear();

        for (int i = 0; i < VALUES_COUNT; i++) {
            double value = getRandomNumber();
            values.add(value);
        }

        calculateHistogram();
    }

    protected abstract double getRandomNumber();

    public abstract void showHistogram();

    public abstract void input();

    protected abstract boolean isValidValues();

    /**
     * Get histogram data.
     *
     * @return histogram data
     */
    List<Double> getHistogramData() {
        if (isHistogramCalculated()) {
            return this.yAxis;
        }

        Printer.print("Cannot get histogram data: that is not calculated");
        return null;
    }

    private boolean isHistogramCalculated() {
        return isHistogramCalculated;
    }

    private void setHistogramCalculated(boolean histogramCalculated) {
        isHistogramCalculated = histogramCalculated;
    }

    /**
     * Calculate standard deviation.
     *
     * @return std
     */
    private double calculateStd() {
        if (values.isEmpty()) {
            Printer.print("Cannot calculate std: list is empty");
            return -1;
        }

        double Dx = calculateDx();
        return new BigDecimal(Math.sqrt(Dx))
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calculate histogram data.
     */
    private void calculateHistogram() {
        if (values.isEmpty()) {
            Printer.print("Cannot calculate histogram. Values list is empty.");
            return;
        }

        xAxis.clear();
        yAxis.clear();

        final double startValue = 0;
        final double delta = 1 / (double) INTERVAL_COUNT;

        double frequency = startValue;
        double xMin = values.get(0);
        double xMax = xMin;

        for (int i = 0; i < VALUES_COUNT; i++) {
            if (xMin > values.get(i)) {
                xMin = values.get(i);
            }

            if (xMax < values.get(i)) {
                xMax = values.get(i);
            }
        }

        this.xAxis.add(startValue);
        this.yAxis.add(startValue);


        for (int i = 0; i < INTERVAL_COUNT - 1; i++) {
            this.xAxis.add(i * delta);
            this.yAxis.add(startValue);
        }

        for (int i = 0; i < INTERVAL_COUNT - 1; i++) {
            for (int j = 0; j < VALUES_COUNT; j++) {
                if (values.get(j) >= xAxis.get(i) && values.get(j) < xAxis.get(i + 1)) {
                    yAxis.set(i, yAxis.get(i) + 1);
                }

                if (frequency < yAxis.get(i)) {
                    frequency = yAxis.get(i);
                }
            }

            yAxis.set(i, yAxis.get(i) / VALUES_COUNT);
        }

        this.setHistogramCalculated(true);
    }
}

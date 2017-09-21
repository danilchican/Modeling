package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Graph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Generator {

    /**
     * Constants.
     */
    public static final int INTERVAL_COUNT = 20;
    private static final int VALUES_COUNT = 10_000;
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
    public double calculateMx() {
        if (values.isEmpty()) {
            Printer.print("Cannot calculate Mx: list is empty");
            return -1;
        }

        return values
                .stream()
                .mapToDouble(Double::doubleValue).sum() / values.size();
    }

    /**
     * Calculate Dispersion value.
     *
     * @return Dx
     */
    public double calculateDx() {
        if (values.isEmpty()) {
            Printer.print("Cannot calculate Dx: list is empty");
            return -1;
        }

        ArrayList<Double> list = new ArrayList<>();
        double Mx = calculateMx();

        values.forEach(v -> list.add(Math.pow(v - Mx, N_POW)));
        double Dx = list.stream().mapToDouble(Double::doubleValue).sum();

        return Dx / (values.size() - 1);
    }

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

        Collections.sort(values);

        final double startValue = 0;
        final double width = values.get(values.size() - 1) - values.get(0);

        final double intervalWidth = width / INTERVAL_COUNT;
        xAxis.add(0.0245 * width + values.get(0));

        for (int i = 1; i < INTERVAL_COUNT; i++) {
            xAxis.add(xAxis.get(i - 1) + intervalWidth);
        }

        for (int i = 0; i < INTERVAL_COUNT; i++) {
            this.yAxis.add(startValue);
        }

        double xLeft = values.get(0);
        double xRight = xLeft + intervalWidth;

        int j = 0;
        for (int i = 0; i < INTERVAL_COUNT; i++) {
            while (j < values.size() && xLeft <= values.get(j) && xRight > values.get(j)) {
                yAxis.set(i, yAxis.get(i) + 1);
                j++;
            }

            yAxis.set(i, yAxis.get(i) / VALUES_COUNT);
            xLeft = xRight;
            xRight += intervalWidth;
        }

        this.setHistogramCalculated(true);
    }
}

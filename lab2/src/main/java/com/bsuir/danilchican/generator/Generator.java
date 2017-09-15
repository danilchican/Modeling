package com.bsuir.danilchican.generator;

import com.bsuir.danilchican.data.Printer;
import com.bsuir.danilchican.drawing.Graph;
import com.bsuir.danilchican.drawing.Histogram;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class Generator {

    /**
     * Constants.
     */
    public static final int INTERVAL_COUNT = 20;
    private static final int VALUES_COUNT = 50_000;
    private static final int N_POW = 2;

    private boolean isHistogramCalculated = false;

    /**
     * Storage of generated values.
     */
    private List<Double> values;
    private List<Double> xAxis;
    private List<Double> yAxis;

    protected Graph histogram;

    Generator() {
        this.values = new ArrayList<>();
        this.xAxis = new ArrayList<>();
        this.yAxis = new ArrayList<>();
    }

    private boolean isHistogramCalculated() {
        return isHistogramCalculated;
    }

    private void setHistogramCalculated(boolean histogramCalculated) {
        isHistogramCalculated = histogramCalculated;
    }

    public List<Double> getValues() {
        return values;
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
     * Calculate standard deviation.
     *
     * @return std
     */
    public double calculateStd() {
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
    public void calculateHistogram() {
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

    /**
     * Get histogram data.
     *
     * @return histogram data
     */
    public List<Double> getHistogramData() {
        if (isHistogramCalculated()) {
            return this.yAxis;
        }

        Printer.print("Cannot get histogram data: that is not calculated");
        return null;
    }

    public abstract void generate();

    public abstract void showHistogram();

    public abstract void input();

    public abstract void print();

    protected abstract boolean isValidValues();

    protected abstract double getRandomNumber();
}

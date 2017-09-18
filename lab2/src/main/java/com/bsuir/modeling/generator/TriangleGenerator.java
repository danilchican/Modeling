package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Histogram;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class TriangleGenerator extends EvenGenerator {

    private static final int COUNT_R = 2;

    private final int aMG = 67;
    private final int m = 106897;
    private double r0 = 1;

    private void setR0(double r0) {
        this.r0 = r0;
    }

    @Override
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
    @Override
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
    @Override
    public void print() {
        super.print();
    }

    @Override
    protected double getRandomNumber() {
        List<Double> rList = new ArrayList<>();

        for (int i = 0; i < COUNT_R; i++) {
            double number = aMG * r0;

            setR0(number % m);
            rList.add(r0 / m);
        }

        OptionalDouble max = rList.stream().mapToDouble(Double::doubleValue).max();

        return a + (b - a) * max.getAsDouble();
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("TriangleGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        super.input();
    }

    @Override
    protected boolean isValidValues() {
        return super.isValidValues();
    }
}

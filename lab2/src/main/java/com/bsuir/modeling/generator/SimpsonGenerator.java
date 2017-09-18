package com.bsuir.modeling.generator;

import com.bsuir.modeling.data.Printer;
import com.bsuir.modeling.drawing.Histogram;

import java.util.ArrayList;
import java.util.List;

public class SimpsonGenerator extends Generator {

    /**
     * Interval of numbers.
     */
    private double a;
    private double b;

    private static final int COUNT_R = 2;

    private final int aMG = 67;
    private final int m = 106897;
    private double r0 = 1;

    private void setA(double a) {
        this.a = a / 2;
    }

    private void setB(double b) {
        this.b = b / 2;
    }

    private void setR0(double r0) {
        this.r0 = r0;
    }

    private double getA() {
        return a;
    }

    private double getB() {
        return b;
    }

    /**
     * Calculate Mean value.
     *
     * @return Mx
     */
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
        Printer.print(this.getClass().getName() + ":");
        Printer.print("A = " + getA() + ", B = " + getB());

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

        return a + (b - a) * rList.get(0)
                + a + (b - a) * rList.get(1);
    }

    @Override
    public void showHistogram() {
        this.histogram = new Histogram("SimpsonGenerator", getHistogramData());
        this.histogram.show();
    }

    @Override
    public void input() {
        Printer.print(this.getClass().getName() + ":");

        Printer.print("Input A: ");
        setA(in.nextDouble());

        Printer.print("Input B: ");
        setB(in.nextDouble());

        if (!isValidValues()) {
            Printer.print("Your data is invalid. Try again.");
            input();
        }
    }

    @Override
    protected boolean isValidValues() {
        return a < b;
    }
}

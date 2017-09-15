package com.bsuir.modeling.generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.bsuir.modeling.data.Printer.print;

public class Generator {

    /**
     * Constants.
     */
    private static final int N_POW = 2;

    /**
     * Count of intervals.
     */
    public static final int INTERVAL_COUNT = 20;
    private static final double STEP = 0.00001;

    /**
     * Max period: from 0 to M.
     */
    private int m;

    /**
     * Coefficient.
     */
    private int a;

    /**
     * Start value.
     */
    private double r0;

    /**
     * Count of numbers.
     */
    private int VALUES_COUNT = 1_000_000;

    private boolean isHistogramCalculated = false;

    /**
     * Storage of generated values.
     */
    private List<Double> values;
    private List<Double> xAxis;
    private List<Double> yAxis;

    public Generator() {
        this.values = new ArrayList<>();
        this.xAxis = new ArrayList<>();
        this.yAxis = new ArrayList<>();
    }

    public Generator(int a, int m, double r0) {
        this();

        setA(a);
        setM(m);
        setR0(r0);
    }

    public int getM() {
        return m;
    }

    public int getA() {
        return a;
    }

    public boolean isHistogramCalculated() {
        return isHistogramCalculated;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setR0(double r0) {
        this.r0 = r0;
    }

    public void setHistogramCalculated(boolean histogramCalculated) {
        isHistogramCalculated = histogramCalculated;
    }

    /**
     * Get generated values.
     *
     * @return list
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * Check if values are valid.
     *
     * @return boolean
     */
    public boolean isValidValues() {
        return m > 0 && a > 0 && m > a
                && r0 > 0
                && r0 < m;
    }

    /**
     * Task 1.
     * Generate values.
     */
    public void generateValues() {
        values.clear();

        for (int i = 0; i < VALUES_COUNT; i++) {
            double value = getRandomNumber();
            values.add(value);
        }
    }

    /**
     * Get random number by congruential method.
     *
     * @return generated number
     */
    private double getRandomNumber() {
        double number = a * r0;
        setR0(number % m);

        return r0 / m;
    }

    /**
     * Task 2.1.
     * Calculate Mean value.
     *
     * @return Mx
     */
    public double calculateMx() {
        if (values.isEmpty()) {
            print("Cannot calculate Mx: list is empty");
            return -1;
        }

        return values
                .stream()
                .mapToDouble(Double::doubleValue).sum() / values.size();
    }

    /**
     * Task 2.2.
     * Calculate Dispersion value.
     *
     * @return Dx
     */
    public double calculateDx() {
        if (values.isEmpty()) {
            print("Cannot calculate Dx: list is empty");
            return -1;
        }

        ArrayList<Double> list = new ArrayList<>();
        double Mx = calculateMx();

        values.forEach(v -> list.add(Math.pow(v - Mx, N_POW)));
        double Dx = list.stream().mapToDouble(Double::doubleValue).sum();

        return Dx / (values.size() - 1);
    }

    /**
     * Task 2.3.
     * Calculate standard deviation.
     *
     * @return std
     */
    public double calculateStd() {
        if (values.isEmpty()) {
            print("Cannot calculate std: list is empty");
            return -1;
        }

        double Dx = calculateDx();
        return new BigDecimal(Math.sqrt(Dx))
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Task 3.
     * Calculate histogram data.
     */
    public void calculateHistogram() {
        if (values.isEmpty()) {
            print("Cannot calculate histogram. Values list is empty.");
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

        print("Cannot get histogram data: that is not calculated");
        return null;
    }

    /**
     * Task 4.
     * Check comparing group of numbers.
     *
     * @return count of true condition result.
     */
    public int checkComparingRange() {
        if (values.isEmpty()) {
            print("Cannot check comparing range: list is empty");
            return -1;
        }

        int k = 0;

        for (int i = 0; i < values.size() / 2; i++) {
            double prevValue = Math.pow(values.get(2 * (i + 1) - 2), N_POW);
            double nextValue = Math.pow(values.get(2 * (i + 1) - 1), N_POW);

            if (prevValue + nextValue < 1) {
                k++;
            }
        }

        print("2K/N = " + (2 * k / (double) VALUES_COUNT) + ", PI/4 = " + Math.PI / 4);
        return k;
    }

    /**
     * Task 5.1.
     * Calculate period.
     *
     * @return period
     */
    public int calculatePeriod() {
        double lastValue = values.get(values.size() - 1);

        int i1 = 0;
        int i2 = values.size() - 1;

        boolean foundedFirst = false;

        for (int i = 0; i < values.size() - 1; i++) {
           // double value = Math.abs(values.get(i) - lastValue);

            if (values.get(i) == lastValue) {
                if (!foundedFirst) {
                    foundedFirst = true;
                    i1 = i;
                } else {
                    i2 = i;
                    break;
                }
            }
        }

        return i2 - i1;
    }

    public void round() {
        for(int i = 0; i < values.size(); i++) {
            values.set(i, Math.round(getRandomNumber() * 10_000) / 10_000.0);
        }
    }

    /**
     * Task 5.2.
     * Calculate aperiod.
     *
     * @return aperiod
     */
    public int calculateAperiod() {
        final int period = calculatePeriod();
        int i3 = 0;

        for (int i = 0; i < values.size() - 1 - period; i++) {
            //double value = Math.abs(values.get(i) - values.get(i + period));

            if (values.get(i).equals(values.get(i + period))) {
                i3 = i;
                break;
            }
        }

        return i3 + period;
    }
}

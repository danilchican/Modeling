package com.bsuir.danilchican.modeling.controller;

import java.util.*;

import static com.bsuir.danilchican.modeling.Main.*;

public class Workplace {

    private Map<String, List<String>> transitions = new HashMap<>();
    private Map<String, Integer> statistics = new LinkedHashMap<>();

    public static int QUEUE_ORDERS_COUNT = 0;
    public static int TIME_QUEUE_IS_FULL = 0;

    public static final String START_STATE = "2000";

    public Workplace() {
        statistics.put(START_STATE, 0);
        statistics.put("1000", 0);
        statistics.put("2010", 0);
        statistics.put("1010", 0);
        statistics.put("2011", 0);
        statistics.put("2110", 0);
        statistics.put("1011", 0);
        statistics.put("1110", 0);
        statistics.put("1111", 0);
        statistics.put("2111", 0);
        statistics.put("1001", 0);

        transitions.put(START_STATE, new ArrayList<>(Arrays.asList("1000")));
        transitions.put("1000", new ArrayList<>(Arrays.asList("2010")));
        transitions.put("2010", new ArrayList<>(Arrays.asList("1010", "1001")));
        transitions.put("1010", new ArrayList<>(Arrays.asList("2011", "2110")));
        transitions.put("2011", new ArrayList<>(Arrays.asList("1011", "1001", "1010")));
        transitions.put("2110", new ArrayList<>(Arrays.asList("1011", "1110")));
        transitions.put("1011", new ArrayList<>(Arrays.asList("2111", "2011", "2110")));
        transitions.put("1110", new ArrayList<>(Arrays.asList("2111", "2110")));
        transitions.put("1111", new ArrayList<>(Arrays.asList("2111", "2110")));
        transitions.put("2111", new ArrayList<>(Arrays.asList("1111", "1110", "1011")));
        transitions.put("1001", new ArrayList<>(Arrays.asList("2011", "2010")));
    }

    public boolean hasNextTransition(String inState, String outState) {
        if (inState == null || outState == null) {
            throw new NullPointerException("Args are null");
        }

        List<String> possibleStates = transitions.get(inState);

        if (possibleStates != null) {
            return possibleStates.contains(outState);
        } else {
            throw new IllegalArgumentException("ERROR: NEW STATE DISCOVERED " + inState);
        }
    }

    public void updateStatistics(String state) {
        if (state == null) {
            System.out.println("State is null");
            return;
        }

        statistics.put(state, statistics.get(state) + 1);
    }

    public void showTransitionsStatistics() {
        final int countOfTransitions = getTransitionsCount();

        statistics.entrySet().forEach(e ->
                System.out.println("state: " + e.getKey() + "; "
                        + "rate: " + e.getValue() + "/" + countOfTransitions + "; "
                        + "probability: " + (double) e.getValue() / countOfTransitions));
    }

    public void showMainStatistics(Machine machine) {
        double q = (double) machine.getEnd().getCounter() / CYCLES_COUNT * REQUEST_TIME;
        double Woch = TIME_QUEUE_IS_FULL / (double) QUEUE_ORDERS_COUNT;

        System.out.println("\nK1: " + String.format("%.5f", getK1()));
        System.out.println("K2: " + String.format("%.5f", getK2()));
        System.out.println("Q:  " + String.format("%.5f", q));
        System.out.println("W:  " + String.format("%.5f", Woch));
    }

    private int getTransitionsCount() {
        return statistics.values().stream().mapToInt(i -> i).sum();
    }

    private double getK1() {
        int countOfTransitions = getTransitionsCount();

        double P2010 = (double) statistics.get("2010") / countOfTransitions;
        double P1010 = (double) statistics.get("1010") / countOfTransitions;
        double P2011 = (double) statistics.get("2011") / countOfTransitions;
        double P2110 = (double) statistics.get("2110") / countOfTransitions;
        double P1011 = (double) statistics.get("1011") / countOfTransitions;
        double P1110 = (double) statistics.get("1110") / countOfTransitions;
        double P2111 = (double) statistics.get("2111") / countOfTransitions;
        double P1111 = (double) statistics.get("1111") / countOfTransitions;

        return P2010 + P1010 + P2011 + P2110 + P1011 + P1110 + P2111 + P1111;
    }

    private double getK2() {
        int countOfTransitions = getTransitionsCount();

        double P1001 = (double) statistics.get("1001") / countOfTransitions;
        double P2011 = (double) statistics.get("2011") / countOfTransitions;
        double P1011 = (double) statistics.get("1011") / countOfTransitions;
        double P2111 = (double) statistics.get("2111") / countOfTransitions;
        double P1111 = (double) statistics.get("1111") / countOfTransitions;

        return P1001 + P2011 + P1011 + P2111 + P1111;
    }
}

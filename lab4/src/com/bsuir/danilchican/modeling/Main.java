package com.bsuir.danilchican.modeling;

import com.bsuir.danilchican.modeling.controller.Machine;
import com.bsuir.danilchican.modeling.controller.Workplace;
import com.bsuir.danilchican.modeling.node.RequestGeneratorSource;
import com.bsuir.danilchican.modeling.state.State;

public class Main {

    public static final int CYCLES_COUNT = 1_000_000;

    public static final int REQUEST_TIME = 2;
    private static final int QUEUE_CAPACITY = 1;

    private static final double P1 = 0.9;
    private static final double P2 = 0;

    public static void main(String[] args) {
        Workplace workplace = new Workplace();
        Machine machine = new Machine(REQUEST_TIME, QUEUE_CAPACITY, P1, P2);
        RequestGeneratorSource system = (RequestGeneratorSource) machine.getSource();

        String inState = Workplace.START_STATE;

        for (int i = 0; i < CYCLES_COUNT; i++) {
            workplace.updateStatistics(inState);
            system.process();

            String outState = State.getStateAsString(system);

            if (!workplace.hasNextTransition(inState, outState)) {
                throw new IllegalArgumentException(
                        "Error: new outState discovered. " + "inState: " + inState + ", outState: " + outState);
            } else {
                inState = outState;
            }
        }

        workplace.showTransitionsStatistics();
        workplace.showMainStatistics(machine);
    }
}

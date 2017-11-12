package com.bsuir.danilchican.modeling.state;

import com.bsuir.danilchican.modeling.node.Node;

public class State {

    public static String getStateAsString(Node node) {
        if (node == null) {
            return null;
        }

        StringBuilder state = new StringBuilder();

        while (true) {
            state.append(node.getState());

            if (node.getNext() == null) {
                break;
            }

            node = node.getNext();
        }

        return state.toString();
    }
}

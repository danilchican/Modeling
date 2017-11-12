package com.bsuir.danilchican.modeling.controller;

import com.bsuir.danilchican.modeling.node.RequestGeneratorSource;
import com.bsuir.danilchican.modeling.node.RequestQueue;
import com.bsuir.danilchican.modeling.node.RequestStub;
import com.bsuir.danilchican.modeling.node.Node;
import com.bsuir.danilchican.modeling.node.RequestChannel;
import com.bsuir.danilchican.modeling.node.RequestDiscardingChannel;

public class Machine {

    private RequestGeneratorSource source;
    private RequestQueue queue;
    private RequestDiscardingChannel channel1;
    private RequestChannel channel2;
    private RequestStub end;

    public Machine(int requestTime, int capacity, double pi1, double pi2) {
        source = new RequestGeneratorSource(requestTime);
        queue = new RequestQueue(capacity);

        channel1 = new RequestDiscardingChannel(pi1);
        channel2 = new RequestChannel(pi2);

        end = new RequestStub();

        this.build();
    }

    public Node getSource() {
        return source;
    }

    public RequestStub getEnd() {
        return end;
    }

    private void build() {
        channel2.setNext(end);
        channel1.setNext(channel2);

        queue.setNext(channel1);
        source.setNext(queue);
    }
}

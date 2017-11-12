package com.bsuir.danilchican.modeling.node;

import com.bsuir.danilchican.modeling.request.Request;

public class RequestGeneratorSource extends Node {

    private int requestTime = 0;
    private int currentTicks;
    private int requestNumber = 0;
    private Request request;

    public RequestGeneratorSource(int requestTime) {
        setRequestTime(requestTime);
    }

    @Override
    public void activate(Request request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void process() {
        if (next == null) {
            return;
        }

        next.process();
        currentTicks--;

        if (currentTicks == 0) {
            request = new Request(String.format("#%d", requestNumber++));
            currentTicks = requestTime;
        }

        if (request != null) {
            if (!next.isFull()) {
                next.activate(request);
            }
            request = null;
        }
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
        this.currentTicks = requestTime;
    }

    @Override
    public String getState() {
        return String.valueOf(currentTicks);
    }
}

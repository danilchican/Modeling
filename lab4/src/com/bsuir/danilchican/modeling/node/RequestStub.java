package com.bsuir.danilchican.modeling.node;

import com.bsuir.danilchican.modeling.request.Request;

public class RequestStub extends Node {

    private int counter = 0;

    @Override
    public void activate(Request request) {
        counter++;
    }

    @Override
    public void process() {
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String getState() {
        return "";
    }

    public int getCounter() {
        return counter;
    }
}

package com.bsuir.danilchican.modeling.node;

import com.bsuir.danilchican.modeling.request.Request;

import java.util.Random;

public class RequestChannel extends Node {

    private double pi;
    private Request request;

    public RequestChannel(double pi) {
        setPi(pi);
    }

    RequestChannel() {
    }

    @Override
    public void activate(Request request) {
        if (isFull()) {
            throw new IllegalStateException("Can't forward Request to blocked Node");
        }

        this.request = request;
    }

    @Override
    public void process() {
        if (next == null) {
            return;
        }

        next.process();

        if (request != null) {
            if (!next.isFull()) {
                if (new Random().nextFloat() <= 1.0 - pi) {
                    next.activate(request);
                    request = null;
                }
            }
        }
    }

    double getPi() {
        return pi;
    }

    Request getRequest() {
        return request;
    }

    void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean isFull() {
        return request != null;
    }

    @Override
    public String getState() {
        return request != null ? "1" : "0";
    }

    void setPi(double pi) {
        this.pi = pi;
    }
}

package com.bsuir.danilchican.modeling.node;

import com.bsuir.danilchican.modeling.request.Request;

public abstract class Node {

    protected Node next;

    public abstract void activate(Request request);

    public abstract void process();

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public abstract boolean isFull();

    public abstract String getState();
}

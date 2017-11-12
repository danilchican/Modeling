package com.bsuir.danilchican.modeling.node;

import com.bsuir.danilchican.modeling.controller.Workplace;
import com.bsuir.danilchican.modeling.request.Request;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue extends Node {

    private int capacity;
    private long statistic = 0;
    private long tics = 0;
    private Queue<Request> queue;

    public RequestQueue(int capacity) {
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    @Override
    public void activate(Request request) {
        if (isFull()) {
            throw new IllegalStateException("Can't forward Request to blocked Node");
        }

        if (request != null) {
            Workplace.QUEUE_ORDERS_COUNT++;

            if (queue.isEmpty() && !next.isFull()) {
                next.activate(request);
            } else {
                queue.add(request);
            }
        }
    }

    @Override
    public void process() {
        if (next == null) {
            return;
        }

        next.process();

        if(!queue.isEmpty()) {
            Workplace.QUEUE_IS_FULL++;

            if (!next.isFull()) {
                next.activate(queue.remove());
                collectStatistic();
            }
        }
    }

    @Override
    public boolean isFull() {
        return queue.size() >= capacity;
    }

    @Override
    public String getState() {
        return "" + queue.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void clearQueue() {
        queue.clear();
    }

    public void addMembers(int count, Request request) {
        clearQueue();
        if (count > capacity) {
            throw new IllegalArgumentException("out of queue capacity, count should be less or equal " + capacity);
        }
        if (request == null) {
            throw new NullPointerException("request must be not null!");
        }
        for(int i = 0; i < count; i++) {
            queue.add(request);
        }
    }

    public long getStatistic() {
        return statistic;
    }

    public void collectStatistic() {
        if (!queue.isEmpty()) {
            statistic += queue.size();
            tics++;
        }

    }

    public long getTics() {
        return tics;
    }

    public void setTics(long tics) {
        this.tics = tics;
    }
}

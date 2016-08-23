package com.example.kenjihosaka.sampleandroid.model.domain.event;

import com.example.kenjihosaka.sampleandroid.BusProvider;

public class Event <E> {

    private E target;
    private Result result;
    private Throwable error;

    public void publish() {
        BusProvider.instance().send(this);
    }

    public E getTarget() {
        return target;
    }

    public void setTarget(E target) {
        this.target = target;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return result == Result.SUCCESS;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public enum Result {
        SUCCESS, FAILED, NOT_FOUND
    }

}


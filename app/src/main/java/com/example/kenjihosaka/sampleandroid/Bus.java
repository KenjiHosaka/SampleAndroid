package com.example.kenjihosaka.sampleandroid;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class Bus {
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object object) {
        bus.onNext(object);
    }

    public void error(Throwable e) {
        bus.onError(e);
    }

    public Observable<Object> observable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}

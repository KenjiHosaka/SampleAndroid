package com.example.kenjihosaka.sampleandroid;

public class BusProvider {

    private static final Bus bus = new Bus();

    public static Bus instance() {
        return bus;
    }
}

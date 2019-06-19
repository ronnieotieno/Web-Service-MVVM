package com.example.webservicewitharch.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public final class ThreadingExecutors {

    private static final Object LOCK = new Object();
    private static ThreadingExecutors sInstance;
    private final Executor networkIO;

    private ThreadingExecutors(Executor networkIO) {
        this.networkIO = networkIO;
    }

    public static ThreadingExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ThreadingExecutors(Executors.newFixedThreadPool(3));
            }
        }
        return sInstance;
    }

    public Executor networkIO() {
        return networkIO;
    }

}

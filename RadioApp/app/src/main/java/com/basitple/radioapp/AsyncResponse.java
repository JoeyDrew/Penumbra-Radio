package com.basitple.radioapp;

public interface AsyncResponse<T> {
    void processFinish(T value);
}

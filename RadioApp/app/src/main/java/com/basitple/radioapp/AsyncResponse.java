package com.basitple.radioapp;

/**
 * Created by BadAp on 12/8/2017.
 */

public interface AsyncResponse<T> {
    void processFinish(T value);
}

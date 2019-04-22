package com.android.esprit.smartreminders.Services;

public interface CallBackWSConsumerSend<T> extends CallBackWSConsumer<T> {
    void OnResultPresent();
    void OnResultNull();
}

package com.android.esprit.smartreminders.Services;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public interface CallBackWSConsumerSend<T> extends CallBackWSConsumer<T> {
    void OnResultPresent();
    void OnResultNull();


}

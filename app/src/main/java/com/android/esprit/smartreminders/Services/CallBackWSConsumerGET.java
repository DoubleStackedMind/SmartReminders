package com.android.esprit.smartreminders.Services;

import java.util.List;

public interface CallBackWSConsumerGET<T> extends CallBackWSConsumer<T> {
    void OnResultPresent(List<T> results);
}

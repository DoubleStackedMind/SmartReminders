package com.android.esprit.smartreminders.Services;

import java.util.List;

public interface CallBackWSConsumer<T> {
    void OnResultPresent(List<T> result);
    void OnResultNull();
}

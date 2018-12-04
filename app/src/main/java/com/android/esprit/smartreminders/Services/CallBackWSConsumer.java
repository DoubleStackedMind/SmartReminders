package com.android.esprit.smartreminders.Services;

import java.util.List;

public interface CallBackWSConsumer<T> {
    default void OnResultPresent(List<T> results){}
    default void OnResultPresent(){}
    default void  OnResultNull(){}
    void OnHostUnreachable();
}

package com.android.esprit.smartreminders.appcommons;

import android.app.Application;
import android.content.Context;


public class App extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppCommons.install(this);
    }
}

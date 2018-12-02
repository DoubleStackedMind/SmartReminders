package com.android.esprit.smartreminders.Enums;

import android.content.Context;
import android.content.res.Resources;

public enum StateOfTask {
    PENDING,
    DONE,
    CANCELED,
    IN_PROGRESS;

    public int StateForIcon( Context context) {
        switch (this) {

            case PENDING: {
                return context.getResources().getIdentifier("drawable/pending", null, context.getPackageName());
            }
            case DONE: {
                return context.getResources().getIdentifier("drawable/done_done", null, context.getPackageName());
            }
            case CANCELED: {
                return context.getResources().getIdentifier("drawable/canceled", null, context.getPackageName());
            }
            case IN_PROGRESS: {
                return context.getResources().getIdentifier("drawable/inprogress", null, context.getPackageName());

            }
        }
        return -1;
    }
}

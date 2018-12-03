package com.android.esprit.smartreminders.Enums;

import android.content.Context;
import android.content.res.Resources;

import com.android.esprit.smartreminders.Exceptions.NotAValidStateOfTask;

public enum StateOfTask {
    PENDING,
    DONE,
    CANCELED,
    IN_PROGRESS;

    public static StateOfTask fromString(String val)throws NotAValidStateOfTask {
        switch (val){
            case "PENDING":{
                return StateOfTask.PENDING;
            }
            case "DONE":{
                return StateOfTask.DONE;
            }
            case "CANCELED":{
                return StateOfTask.CANCELED;
            }
            case "IN_PROGRESS":{
                return StateOfTask.IN_PROGRESS;
            }
        }
        throw new NotAValidStateOfTask();
    }
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

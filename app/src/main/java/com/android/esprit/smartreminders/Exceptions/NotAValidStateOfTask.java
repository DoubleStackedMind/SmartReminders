package com.android.esprit.smartreminders.Exceptions;

public class NotAValidStateOfTask extends Exception {
    public NotAValidStateOfTask(){
        super("State String is not Compatible with Enum type StateOfTask possible Values are { PENDING," +
                "    DONE," +
                "    CANCELED," +
                "    IN_PROGRESS }");
    }
}

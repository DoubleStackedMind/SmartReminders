package com.android.esprit.smartreminders.appcommons.validator;

import android.widget.EditText;


import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.DateUtils;


/**
 * Created by Daniel on 21/01/2016.
 */
public class EditTextStartDateGreaterThanTodayValidator extends EditTextValidator {
    private final long startDateMilliSecs;

    public EditTextStartDateGreaterThanTodayValidator(EditText startDateEditText, long startDateMilliSecs) {
        super(startDateEditText, AppCommons.getAppCommonsConfiguration().getEditTextStartDateGreaterThanTodayErrorMessage());
        this.startDateMilliSecs = startDateMilliSecs;
    }

    @Override
    public boolean isValid() {
        return DateUtils.isDateGreaterThanToday(startDateMilliSecs);
    }
}

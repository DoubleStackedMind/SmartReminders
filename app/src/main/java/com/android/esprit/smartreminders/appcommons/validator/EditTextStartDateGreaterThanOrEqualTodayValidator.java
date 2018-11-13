package com.android.esprit.smartreminders.appcommons.validator;

import android.widget.EditText;

import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.DateUtils;


/**
 * Created by bendaniel on 05/09/2016.
 */

public class EditTextStartDateGreaterThanOrEqualTodayValidator extends EditTextValidator {
    private final long editTextDate;

    public EditTextStartDateGreaterThanOrEqualTodayValidator(EditText editText, long editTextDate) {
        super(editText, AppCommons.getAppCommonsConfiguration().getEditTextDateEarlierThanTodayErrorMessage());
        this.editTextDate = editTextDate;
    }

    @Override
    public boolean isValid() {
        return DateUtils.isDateGreaterThanOrEqualToday(editTextDate);
    }

}

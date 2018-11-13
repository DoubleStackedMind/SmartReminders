package com.android.esprit.smartreminders.appcommons.validator;

import android.widget.EditText;



import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;

/**
 * Created by Daniel on 9/11/2015.
 */
public class EditTextRequiredInputValidator extends EditTextValidator {


    public EditTextRequiredInputValidator(EditText editText) {
        super(editText, AppCommons.getAppCommonsConfiguration().getEditTextRequiredErrorMessage());
    }

    @Override
    public boolean isValid() {
        return !EditTextUtils.testIsEmpty(editText);
    }
}

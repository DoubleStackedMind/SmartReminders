package com.android.esprit.smartreminders.appcommons.validator;

import android.widget.EditText;

import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;


/**
 * Created by Daniel on 9/11/2015.
 */
public class EditTextEmailValidator extends EditTextValidator {

    public EditTextEmailValidator(EditText editText) {
        super(editText, AppCommons.getAppCommonsConfiguration().getEditTextInvalidEmailErrorMessage());
    }

    @Override
    public boolean isValid() {
        if (EditTextUtils.testIsEmpty(editText))
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(EditTextUtils.getText(editText)).matches();
    }
}

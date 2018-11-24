package com.android.esprit.smartreminders.appcommons.validator;

import android.view.View;
import android.widget.EditText;


import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;

/**
 * Created by bendaniel on 07/09/2016.
 */

public class EditTextPhoneNumberValidator extends EditTextValidator {
    public EditTextPhoneNumberValidator(EditText editText) {
        super(editText, AppCommons.getAppCommonsConfiguration().getEditTextPhoneNumberValidatorErrorMessage());
    }

    @Override
    public boolean isValid() {
        return editText.getVisibility() == View.GONE || android.util.Patterns.PHONE.matcher(EditTextUtils.getText(editText)).matches();
    }
}

package com.android.esprit.smartreminders.appcommons.validator;

import android.view.View;
import android.widget.EditText;


import com.google.common.primitives.Doubles;

import com.android.esprit.smartreminders.appcommons.AppCommons;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;

/**
 * Created by Daniel on 9/11/2015.
 */
public class EditTextPositiveNumberedInputValidator extends EditTextValidator {

    public EditTextPositiveNumberedInputValidator(EditText editText) {
        super(editText, AppCommons.getAppCommonsConfiguration().getEditTextInvalidPositiveNumberErrorMessage());
    }

    @Override
    public boolean isValid() {
        if (EditTextUtils.testIsEmpty(editText))
            return false;
        return editText.getVisibility() != View.VISIBLE ||
                (Doubles.tryParse(EditTextUtils.getText(editText)) != null && Double.parseDouble(EditTextUtils.getText(editText)) >= 0);
    }


}

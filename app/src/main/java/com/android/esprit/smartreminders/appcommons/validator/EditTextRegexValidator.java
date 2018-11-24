package com.android.esprit.smartreminders.appcommons.validator;

import android.widget.EditText;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;

/**
 * Created by bendaniel on 01/04/2016.
 */
public class EditTextRegexValidator extends EditTextValidator {
    private Pattern pattern;

    public EditTextRegexValidator(EditText editText, String regexPatter, int errorMessageId) {
        super(editText, errorMessageId);
        pattern = Pattern.compile(regexPatter);
    }

    @Override
    public boolean isValid() {
        if (EditTextUtils.testIsEmpty(editText))
            return false;
        Matcher matcher = pattern.matcher(EditTextUtils.getText(editText));
        return matcher.matches();
    }
}

package com.android.esprit.smartreminders.appcommons.activity.operations;

import android.content.Context;
import android.content.Intent;



import com.android.esprit.smartreminders.appcommons.AppCommonsContext;

/**
 * Created by Daniel on 07/11/2015.
 */
public interface ActivityOperations {

    public void startActivity(Intent intent);

    AppCommonsContext getAppCommonsContext();

    void setAppCommonsContext(AppCommonsContext appCommonsContext);

    void restartApp(Context context, Class starupClass);

    void hideKeyBoard();

    void hideToolBar();

    void showToolBar();

    void setActionBarTitle(int messageId);

    void setActionBarTitle(String message);
}

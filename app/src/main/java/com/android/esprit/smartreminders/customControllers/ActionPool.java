package com.android.esprit.smartreminders.customControllers;

import android.content.Context;

import com.android.esprit.smartreminders.Entities.Action;
import com.android.esprit.smartreminders.R;

public class ActionPool {
    private Context context;
    private Action actions[];
    private static ActionPool instance;

    private ActionPool(Context context) {
        this.context = context;
        actions = new Action[]
                {
                        new Action(1, "Enable Flash", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).open_flash();
                            }
                        },
                        new Action(2, "Disable Flash", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).close_flash();
                            }
                        },
                        new Action(3, "Enable Wifi", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).enable_wifi();
                            }
                        },
                        new Action(4, "Disable Wifi", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).disable_wifi();
                            }
                        },
                        new Action(5, "Set Vibrate only mode", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).set_on_vibrate_mode();
                            }
                        },
                        new Action(6, "Set Normal mode", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).set_on_normal_mode();
                            }
                        },
                        new Action(7, "Set Silent mode", R.drawable.app_logo) {
                            @Override
                            public void executeAction() {
                                MainController.getInstance(ActionPool.this.context).set_on_silent_mode();
                            }
                        }
                };
    }

    public Action[] getActions() {
        return this.actions;
    }

    public static ActionPool getInstance(Context context) {
        if (instance == null) {
            instance = new ActionPool(context);
        }
        return instance;
    }

}



package com.android.esprit.smartreminders.Fragments;

import android.app.Activity;
import android.os.Bundle;

import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.appcommons.fragment.AppCommonsFragment;

public abstract class FragmentChild extends AppCommonsFragment {
    protected MainFrame ParentActivity;

    public Activity getParentActivity() {
        return ParentActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ParentActivity=((MainFrame)getActivity());
    }
    
}

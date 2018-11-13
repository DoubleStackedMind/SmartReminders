package com.android.esprit.smartreminders.appcommons.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.esprit.smartreminders.appcommons.activity.AppCommonsActivity;

/**
 * Created by Daniel on 08/11/2015.
 */
public class AppCommonsFragment extends Fragment {

    protected AppCommonsActivity appCommonsActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appCommonsActivity = ((AppCommonsActivity) getActivity());
    }
}

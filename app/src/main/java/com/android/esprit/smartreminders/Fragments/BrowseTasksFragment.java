package com.android.esprit.smartreminders.Fragments;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.activities.MainFrame;


public class BrowseTasksFragment extends FragmentChild {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browsetasks, container, false);
    }
}

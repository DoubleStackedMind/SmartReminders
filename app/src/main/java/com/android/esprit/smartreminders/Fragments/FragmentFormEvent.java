package com.android.esprit.smartreminders.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.esprit.smartreminders.R;

public class FragmentFormEvent extends FragmentChild {
    private Button pushBSunday;
    private Button getPushBMonday;
    private Button pushBTuesday;
    private Button pushBWednsday;
    private Button pushBThursday;
    private Button pushBFriday;
    private Button pushBSaturday;
    private EditText title;
    private Spinner tasks;
    private EditText executionTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plan_type, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
    }

    private void initViews() {
          pushBSunday=this.ParentActivity.findViewById(R.id.pushb_sunday);
          getPushBMonday=this.ParentActivity.findViewById(R.id.pushb_monday);
          pushBTuesday=this.ParentActivity.findViewById(R.id.pushb_tuesday);
          pushBWednsday=this.ParentActivity.findViewById(R.id.pushb_wednesday);
          pushBThursday=this.ParentActivity.findViewById(R.id.pushb_thursday);
          pushBFriday=this.ParentActivity.findViewById(R.id.pushb_friday);
          pushBSaturday=this.ParentActivity.findViewById(R.id.pushb_saturday);
          title=this.ParentActivity.findViewById(R.id.title);
          tasks=this.ParentActivity.findViewById(R.id.spinnerTask);
          executionTime=this.ParentActivity.findViewById(R.id.executionTimePicker);
    }
    private void defineBehaviour() {
    }
}

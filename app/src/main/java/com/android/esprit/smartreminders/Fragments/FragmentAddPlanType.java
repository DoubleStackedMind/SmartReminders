package com.android.esprit.smartreminders.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.esprit.smartreminders.R;


public class FragmentAddPlanType extends FragmentChild {
    private Button TriggerTaskBtnSelect;
    private Button EventBtnSelect;
    private Button TimeTaskBtnSelect;

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
        TriggerTaskBtnSelect = this.ParentActivity.findViewById(R.id.TriggerTaskBtnSelect);
        EventBtnSelect = this.ParentActivity.findViewById(R.id.EventBtnSelect);
        TimeTaskBtnSelect = this.ParentActivity.findViewById(R.id.TimeTaskBtnSelect);
    }

    private void defineBehaviour() {
        TriggerTaskBtnSelect.setOnClickListener((view) ->
                FragmentAddPlanType.this.ParentActivity.goToUnStackedFragment(new FragmentFormTriggerTask())
        );
        EventBtnSelect.setOnClickListener((view) ->
                FragmentAddPlanType.this.ParentActivity.goToUnStackedFragment(new FragmentFormEvent())
        );
        TimeTaskBtnSelect.setOnClickListener((view) ->
                FragmentAddPlanType.this.ParentActivity.goToUnStackedFragment(new FragmentFormTimeTask())
        );
    }
}

package com.android.esprit.smartreminders.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.esprit.smartreminders.Adapters.PlansAdapter;
import com.android.esprit.smartreminders.Entities.AbstractEventOrTask;
import com.android.esprit.smartreminders.Entities.Action;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Entities.Trigger;
import com.android.esprit.smartreminders.Entities.TriggerTask;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.customControllers.ActionPool;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class PlansFragment extends FragmentChild {

    private ListView PlansListView;
    private FloatingActionButton AddNewFAB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dailyplans, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();

        /* test  */
        ArrayList<AbstractEventOrTask> plans = new ArrayList<>();
        Set<DayOfTheWeek> Days = new HashSet<DayOfTheWeek>();
        Days.add(DayOfTheWeek.Monday);
        Days.add(DayOfTheWeek.Thursday);

        TimeTask p = new TimeTask(StateOfTask.IN_PROGRESS, "Send Sms to Mah", Days, new Time(16, 30, 0));
        Event e = new Event(StateOfTask.IN_PROGRESS, "Quality Of Service", Days, new Time(17, 30, 0), new Time(19, 0, 0),7);
        Set<Trigger> triggers = new HashSet<>();
        triggers.add(new Trigger("Wifi Off", R.drawable.cellularon));
        triggers.add(new Trigger("cloud base", R.drawable.daily_plan));
        TriggerTask tt = new TriggerTask(StateOfTask.IN_PROGRESS, "Send Sms to Mah", Days, triggers);
        Set<Action> x = new HashSet<>();
        Action a = ActionPool.getInstance(this.getParentActivity()).getActions()[0];
        Action d = ActionPool.getInstance(this.getParentActivity()).getActions()[1];
        x.add(a);
        x.add(d);
        p.setActions(x);
        tt.setActions(x);

        plans.add(p);
        plans.add(p);
        plans.add(p);
        plans.add(e);
        plans.add(tt);

        PlansListView.setAdapter(new PlansAdapter(this.ParentActivity, plans));
    }

    private void initViews() {
        PlansListView = this.ParentActivity.findViewById(R.id.PlansListView);
        AddNewFAB = this.ParentActivity.findViewById(R.id.AddNewFAB);

    }

    private void defineBehaviour() {
        AddNewFAB.setOnClickListener((view) -> PlansFragment.this.ParentActivity.goToUnStackedFragment(new FragmentAddPlanType()));
    }


}

package com.android.esprit.smartreminders.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.esprit.smartreminders.Adapters.DailyScheduleAdapter;
import com.android.esprit.smartreminders.Entities.AbstractEventOrTask;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.sessions.Session;

import java.sql.Time;
import java.util.ArrayList;

public class ScheduleFragment extends FragmentChild {
    private ListView plansHolder;
    private TextView error_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        plansHolder = this.getParentActivity().findViewById(R.id.todays_schedule_lv);
        error_msg = this.getParentActivity().findViewById(R.id.error_msg);
        ArrayList<AbstractEventOrTask> lista = new ArrayList<>();
        //  lista.add(new Event(StateOfTask.CANCELED, "This is a second test", new Time(19, 30, 00), new Time(20, 30, 00)));
        //  lista.add(  new TriggerTask(StateOfTask.PENDING, " This is a Test", new Time(15, 30, 0)));
        //  lista.add(  new TriggerTask(StateOfTask.PENDING, " This is a Test", new Time(15, 30, 0)));

       if (Session.getSession(this.getParentActivity()).getSessionUser().getDailyplan().getTodaysPlans().size()==0) {
            plansHolder.setVisibility(View.GONE);
        } else {
            error_msg.setVisibility(View.GONE);
        }
        //  plansHolder.setAdapter(new DailyScheduleAdapter(this.getParentActivity().getApplicationContext(), lista));
    }
}

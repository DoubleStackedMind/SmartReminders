package com.android.esprit.smartreminders.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Adapters.TimeTaskAdapater;
import com.android.esprit.smartreminders.Adapters.ZonesAdapter;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Entities.Zone;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerGET;
import com.android.esprit.smartreminders.Services.WebServiceTimeTask;
import com.android.esprit.smartreminders.Services.WebServiceZone;
import com.android.esprit.smartreminders.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksFragment extends FragmentChild {

    private ListView timetasks_list;
    private FloatingActionButton addNewTimeTask;
    private TextView error_msg;
    private ArrayList<TimeTask> timetasks;
    private User sessionUser;
    private SwipeRefreshLayout srl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
        initData();
    }

    private void initViews() {
        timetasks_list = this.ParentActivity.findViewById(R.id.list_timeTasks);
        addNewTimeTask = this.ParentActivity.findViewById(R.id.addNewTimeTask);
        error_msg = this.ParentActivity.findViewById(R.id.error_msg);
        srl = this.ParentActivity.findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(this::initData);
        sessionUser = Session.getSession(this.getParentActivity()).getSessionUser();
    }

    private void defineBehaviour() {
        timetasks_list.setOverscrollHeader(this.getParentActivity().getDrawable(R.drawable.blue));
        timetasks_list.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        timetasks_list.setVerticalFadingEdgeEnabled(true);
    }

    public void initData() {
        srl.setRefreshing(true);
        timetasks = new ArrayList<>();
        WebServiceTimeTask WT = new WebServiceTimeTask(this.ParentActivity, new CallBackWSConsumerGET<TimeTask>() {
            @Override
            public void OnResultPresent(List<TimeTask> results) {

                timetasks = (ArrayList<TimeTask>) results;
                TimeTaskAdapater adapter = new TimeTaskAdapater(TasksFragment.this.ParentActivity, timetasks, R.layout.single_zone_layout, TasksFragment.this);
                TasksFragment.this.timetasks_list.setAdapter(adapter);
                srl.setRefreshing(false);
                error_msg.setVisibility(View.GONE);
            }

            @Override
            public void OnResultNull() {
                srl.setRefreshing(false);
                error_msg.setVisibility(View.VISIBLE);
            }


            @Override
            public void OnHostUnreachable() {

                CharSequence text = "Server is Down Try Again Later";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(TasksFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                srl.setRefreshing(false);
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("user", sessionUser.getId() + "");
        try {
            WT.findBy(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

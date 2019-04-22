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

import com.android.esprit.smartreminders.Adapters.EventsAdapter;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerGET;
import com.android.esprit.smartreminders.Services.WebServiceEvent;
import com.android.esprit.smartreminders.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventsndMettingsFragment extends FragmentChild {
    private ListView events_list;
    private FloatingActionButton addNewEvent;
    private TextView error_msg;
    private ArrayList<Event> events;
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
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
        initData();
    }

    private void initViews() {
        events_list = this.ParentActivity.findViewById(R.id.list_events);
        addNewEvent = this.ParentActivity.findViewById(R.id.addEventfab);
        error_msg = this.ParentActivity.findViewById(R.id.error_msg);
        srl = this.ParentActivity.findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(this::initData);
        sessionUser = Session.getSession(this.getParentActivity()).getSessionUser();
    }
    private void defineBehaviour() {
        events_list.setOverscrollHeader(this.getParentActivity().getDrawable(R.drawable.blue));
        events_list.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        events_list.setVerticalFadingEdgeEnabled(true);
        addNewEvent.setOnClickListener((view)->{this.ParentActivity.setEditedObject(null);this.ParentActivity.goToUnStackedFragment(new FragmentFormEvent());});

    }
    public void initData() {
        srl.setRefreshing(true);
        events = new ArrayList<>();
        WebServiceEvent WT = new WebServiceEvent(this.ParentActivity, new CallBackWSConsumerGET<Event>() {
            @Override
            public void OnResultPresent(List<Event> results) {

                events = (ArrayList<Event>) results;
                EventsAdapter adapter = new EventsAdapter(EventsndMettingsFragment.this.ParentActivity, events, R.layout.single_zone_layout, EventsndMettingsFragment.this);
                EventsndMettingsFragment.this.events_list.setAdapter(adapter);
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

                Toast toast = Toast.makeText(EventsndMettingsFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                srl.setRefreshing(false);
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("user", sessionUser.getId() + "");

            WT.findBy(map);


    }

}

package com.android.esprit.smartreminders.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Adapters.ZonesAdapter;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Entities.Zone;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerGET;
import com.android.esprit.smartreminders.Services.WebServiceZone;
import com.android.esprit.smartreminders.activities.MapsActivity;
import com.android.esprit.smartreminders.appcommons.App;
import com.android.esprit.smartreminders.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZonesFragment extends FragmentChild {
    private ListView zonelist;
    private FloatingActionButton addFab;
    private ArrayList<Zone> zones;
    private User sessionUser;
    private SwipeRefreshLayout srl;
    private TextView error_msg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zones, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
        initData();

    }

    private void initViews() {
        this.zonelist = this.ParentActivity.findViewById(R.id.list_zone);
        this.addFab = this.ParentActivity.findViewById(R.id.addZonefab);
        sessionUser = Session.getSession(this.getParentActivity()).getSessionUser();
        srl = this.ParentActivity.findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(this::initData);
        error_msg= this.ParentActivity.findViewById(R.id.error_msg);

    }

    private void defineBehaviour() {
        addFab.setOnClickListener((v) -> {
            Intent intent = new Intent(ZonesFragment.this.ParentActivity, MapsActivity.class);
            ZonesFragment.this.ParentActivity.startActivity(intent);
        });
        zonelist.setOverscrollHeader(this.getParentActivity().getDrawable(R.drawable.blue));
        zonelist.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        zonelist.setVerticalFadingEdgeEnabled(true);


    }

    public void initData() {
        srl.setRefreshing(true);
        zones = new ArrayList<>();
        WebServiceZone WZ = new WebServiceZone(this.ParentActivity, new CallBackWSConsumerGET<Zone>() {
            @Override
            public void OnResultPresent(List<Zone> results) {

                zones = (ArrayList<Zone>) results;
                ZonesAdapter adapter = new ZonesAdapter(ZonesFragment.this.ParentActivity, zones, R.layout.single_zone_layout,ZonesFragment.this);
                ZonesFragment.this.zonelist.setAdapter(adapter);
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

                Toast toast = Toast.makeText(ZonesFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                srl.setRefreshing(false);
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("user", sessionUser.getId() + "");
        try {
            WZ.findBy(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}

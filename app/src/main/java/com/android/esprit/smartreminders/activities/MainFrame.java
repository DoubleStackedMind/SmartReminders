package com.android.esprit.smartreminders.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.esprit.smartreminders.Fragments.BrowseTasksFragment;
import com.android.esprit.smartreminders.Fragments.EventsndMettingsFragment;
import com.android.esprit.smartreminders.Fragments.FragmentChild;
import com.android.esprit.smartreminders.Fragments.HomeFragment;
import com.android.esprit.smartreminders.Fragments.PlansFragment;
import com.android.esprit.smartreminders.Fragments.ProfileFragment;
import com.android.esprit.smartreminders.Fragments.ScheduleFragment;
import com.android.esprit.smartreminders.Fragments.SettingsFragment;
import com.android.esprit.smartreminders.Fragments.ShareTasksFragment;
import com.android.esprit.smartreminders.Fragments.TasksFragment;
import com.android.esprit.smartreminders.Fragments.ZonesFragment;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.appcommons.activity.AppCommonsActivity;
import com.android.esprit.smartreminders.broadcastrecivers.WifiStateReceiver;
import com.android.esprit.smartreminders.customControllers.CameraController;

public class MainFrame extends AppCommonsActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        clearBackStack();
        setContentView(R.layout.activity_main_frame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        this.setTitle(R.string.app_name);
        this.switchFragments(R.id.fragment_container, new HomeFragment());
        new Handler().postDelayed(this::init_Layout_data, 500);

    }



    private void init_Layout_data() {
        SharedPreferences sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
        String data = sharedPref.getString("Logged_user_data", "User name or data missing");
        TextView f = ((TextView) findViewById(R.id.tvusername));
        f.setText(data.substring(0, data.indexOf("\n")));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettingsFragment();
            return true;
        } else if (id == R.id.action_profile) {
            goToProfileFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            goToHomeFragment();
        } else if (id == R.id.nav_schedule) {
            goToScheduleFragment();
        } else if (id == R.id.nav_daily) {
            goToPlansFragment();
        } else if (id == R.id.nav_tasks) {
            goToTasksFragment();
        } else if (id == R.id.nav_events) {
            goToEventndMeetingsFragment();
        } else if (id == R.id.nav_share) {
            goToShareTasksFragment();
        } else if (id == R.id.nav_browse) {
            goToBrowseTasksFragment();
        }else if(id==R.id.nav_zones){
            goToZonesFragment();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToHomeFragment() {

        this.switchFragments(R.id.fragment_container, new HomeFragment());

        Log.d("Fragments Behavour", "goToHomeFragment: fragment changed !");
    }

    private void goToScheduleFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new ScheduleFragment());
        Log.d("Fragments Behavour", "goToScheduleFragment: fragment changed !");
    }

    private void goToPlansFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new PlansFragment());
        Log.d("Fragments Behavour", "goToPlansFragment: fragment changed !");
    }

    private void goToTasksFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new TasksFragment());
        Log.d("Fragments Behavour", "goToTasksFragment: fragment changed !");
    }

    private void goToEventndMeetingsFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new EventsndMettingsFragment());
        Log.d("Fragments Behavour", "goToEventndMeetingsFragment: fragment changed !");
    }

    private void goToShareTasksFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new ShareTasksFragment());
        Log.d("Fragments Behavour", "goToShareTasksFragment: fragment changed !");
    }

    private void goToBrowseTasksFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new BrowseTasksFragment());
        Log.d("Fragments Behavour", "goToBrowseTasksFragment: fragment changed !");
    }

    private void goToSettingsFragment() {

        switchFragmentsAddToBackStack(R.id.fragment_container, new SettingsFragment());
        Log.d("Fragments Behavour", "goToSettingsFragment: fragment changed !");
    }

    private void goToProfileFragment() {
        switchFragmentsAddToBackStack(R.id.fragment_container, new ProfileFragment());
        Log.d("Fragments Behavour", "goToProfileFragments: fragment changed !");
    }

    public void goToUnStackedFragment(FragmentChild Child) {
        switchFragments(R.id.fragment_container, Child);
        Log.d("Fragments Behavour", "goToUnStackedFragment: fragment changed !");
    }
    public void goToZonesFragment(){
        switchFragments(R.id.fragment_container,new ZonesFragment());
        Log.d("Fragments Behavour", "goToZonesFragment: fragment changed !");

    }



}

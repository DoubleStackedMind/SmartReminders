package com.android.esprit.smartreminders.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.Zone;
import com.android.esprit.smartreminders.Jobs.GpsJobService;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerSend;
import com.android.esprit.smartreminders.Services.WebServiceZone;
import com.android.esprit.smartreminders.appcommons.App;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;
import com.android.esprit.smartreminders.appcommons.validator.EditTextRequiredInputValidator;
import com.android.esprit.smartreminders.customControllers.CameraController;
import com.android.esprit.smartreminders.listeners.PinchCallBack;
import com.android.esprit.smartreminders.listeners.ToucheListener;
import com.android.esprit.smartreminders.permissionHandlers.PermissionHandler;
import com.android.esprit.smartreminders.sessions.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static android.os.SystemClock.sleep;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Circle mCircle;
    private PermissionHandler p;
    private View HelperView;
    private Marker currentMarker;
    private View mapView;
    private ToucheListener tl;
    private FloatingActionButton EditFab;
    private FloatingActionButton DeleteFab;
    private FloatingActionButton AddFab;
    private boolean editMode;
    private double radiusInMeters;
    private DecimalFormat df2;
    private CircleOptions zone;
    private String zonename;
    private AlertDialog dialog;
    private EditText input;
    private boolean updateMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initViews();
        defineBehaviour();


    }

    private void initViews() {
        mapView = findViewById(R.id.map);
        HelperView = findViewById(R.id.helperView);
        EditFab = findViewById(R.id.eDitfab);
        DeleteFab = findViewById(R.id.dEletefab);
        AddFab = findViewById(R.id.aDdfab);
        editMode = false;
        radiusInMeters = 5f;
        currentMarker = null;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this, R.style.AlertDialogCustom);
        input = new EditText(MapsActivity.this);
        input.setBackgroundColor(Color.CYAN);
        input.setGravity(Gravity.CENTER_HORIZONTAL);
        input.setHint("Zone name");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_zone_pin);
        alertDialog.setPositiveButton("Update", null);
        dialog = alertDialog.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

    }

    private void defineBehaviour() {
        df2 = new DecimalFormat(".##");
        tl = new ToucheListener(new PinchCallBack() {
            @Override
            public void onPinchWide() {
                radiusInMeters += 0.2;
                mCircle.setRadius(radiusInMeters);
                updateZoneInfo();
            }

            @Override
            public void onPinchNarrow() {
                if (radiusInMeters > 10) {
                    radiusInMeters -= 0.2;
                    mCircle.setRadius(radiusInMeters);
                    updateZoneInfo();
                }
            }
        });

        EditFab.setOnClickListener((v) -> {
            if (!editMode) {

                EditFab.setImageResource(R.drawable.ic_pin_drop_black_24dp);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(currentMarker.getPosition()));
                HelperView.setOnTouchListener(tl);
                editMode = true;
                DeleteFab.setVisibility(View.VISIBLE);
                AddFab.setVisibility(View.INVISIBLE);
            } else {
                editMode = false;
                HelperView.setOnTouchListener(null);
                DeleteFab.setVisibility(View.INVISIBLE);
                AddFab.setVisibility(View.VISIBLE);
                EditFab.setImageResource(R.drawable.ic_action_edit);
            }
        });
        DeleteFab.setOnClickListener((v) -> {
            if (editMode) {
                mMap.clear();
                DeleteFab.setVisibility(View.INVISIBLE);
                EditFab.setVisibility(View.INVISIBLE);
                AddFab.setVisibility(View.INVISIBLE);
                currentMarker = null;
                editMode = false;
                HelperView.setOnTouchListener(null);
                EditFab.setImageResource(R.drawable.ic_action_edit);
            }
        });
        AddFab.setOnClickListener((v) -> updateDataBase());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setOnMapLongClickListener(this);
        Intent intent = getIntent();
        if (intent.hasExtra("zone")) {
            updateMode = true;
            Zone z = new Zone();
            try {
                z.FromJsonObject(new JSONObject(intent.getExtras().get("zone").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(z.getLat(), z.getLng())).title("Zone Home : Radius = 5 m"));

            radiusInMeters = z.getRadius();
            zonename = z.getName();
            updateZoneInfo();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentMarker.getPosition(), 20));
            mMap.setOnInfoWindowLongClickListener(marker -> promptDialog("Change"));
            int strokeColor = 0xffff0000; //red outline
            int shadeColor = 0x44ff0000; //opaque red fill
            zone = new CircleOptions().center(currentMarker.getPosition()).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
            mCircle = mMap.addCircle(zone);
            EditFab.setVisibility(View.VISIBLE);
            AddFab.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (currentMarker == null) {
            EditFab.setVisibility(View.VISIBLE);
            AddFab.setVisibility(View.VISIBLE);
        } else{
            mMap.clear();
            drawMarkerWithCircle(latLng);
        }
    }

    private void drawMarkerWithCircle(LatLng position) {

        currentMarker = mMap.addMarker(new MarkerOptions().position(position).title("Zone Home : Radius = 5 m"));
        mMap.setOnInfoWindowLongClickListener(marker -> promptDialog("Change"));
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill
        zone = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(zone);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(position));
        if (!updateMode)
            promptDialog("Set");
    }

    private void promptDialog(String action) {
        dialog.setTitle(action + " Zone Name");
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v) -> {
            if (!EditTextUtils.isInValid(new EditTextRequiredInputValidator(input))) {
                zonename = input.getText().toString();
                updateZoneInfo();
                dialog.dismiss();
            }

        });
    }

    private void updateZoneInfo() {
        currentMarker.setTitle(zonename + " : Radius = " + df2.format(radiusInMeters) + " m");
        currentMarker.showInfoWindow();
    }

    private void updateDataBase() {

        ProgressBar bar = findViewById(R.id.loadingOverLay);
        bar.setVisibility(View.VISIBLE);
        AddFab.setEnabled(false);
        EditFab.setEnabled(false);
        WebServiceZone WZ = new WebServiceZone(this, new CallBackWSConsumerSend<Zone>() {
            @Override
            public void OnResultPresent() {
                Toast.makeText(MapsActivity.this, "Zone Added !", Toast.LENGTH_LONG).show();
                bar.setVisibility(View.INVISIBLE);
                new Thread(() -> {
                    sleep(1000);
                    onBackPressed();
                }).run();
            }

            @Override
            public void OnResultNull() {
                Toast.makeText(MapsActivity.this, "Something Went Wrong !", Toast.LENGTH_LONG).show();
                AddFab.setEnabled(true);
                EditFab.setEnabled(true);
                bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void OnHostUnreachable() {
                Toast.makeText(MapsActivity.this, "Host unreachable!", Toast.LENGTH_LONG).show();
                AddFab.setEnabled(true);
                EditFab.setEnabled(true);
                bar.setVisibility(View.INVISIBLE);

            }
        });
        Zone z = new Zone(MapsActivity.this.zonename, currentMarker.getPosition().longitude, currentMarker.getPosition().latitude, mCircle.getRadius(), Session.getSession(this).getSessionUser());
        if (!updateMode)
            WZ.insert(z);
        else
            WZ.update(z);
    }

}

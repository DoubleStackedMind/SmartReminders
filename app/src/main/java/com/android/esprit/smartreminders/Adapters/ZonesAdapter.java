package com.android.esprit.smartreminders.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.Zone;
import com.android.esprit.smartreminders.Fragments.ZonesFragment;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerGET;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerSend;
import com.android.esprit.smartreminders.Services.WebServiceZone;
import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.activities.MapsActivity;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ZonesAdapter extends CustomAdapter<Zone> {
    private Geocoder geocoder;
    private List<Address> addresses;
    private ZonesFragment z;

    public ZonesAdapter(@NonNull Context context, ArrayList<Zone> Array, int SingleLayout, ZonesFragment z) {
        super(context, Array, SingleLayout);
        geocoder = new Geocoder(context, Locale.getDefault());
        this.z = z;
    }

    @Override
    public void InflateInputs(View convertView) {
        double latitude = Array.get(position).getLat();
        double longitude = Array.get(position).getLng();
        double radius = Array.get(position).getRadius();


        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
       /* String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL */
        DecimalFormat df2 = new DecimalFormat(".##");

        TextView city_v = convertView.findViewById(R.id.city_zone);
        TextView radius_v = convertView.findViewById(R.id.radius_zone);
        TextView street_v = convertView.findViewById(R.id.street_zone);
        TextView name_v = convertView.findViewById(R.id.name_zone);
        Button deleteBtn = convertView.findViewById(R.id.deleteZone);
        Button editBtn = convertView.findViewById(R.id.updateZone);

        city_v.setText(city);
        street_v.setText(address);

        radius_v.setText(String.valueOf(df2.format(radius)) + " m");
        name_v.setText(Array.get(position).getName());

        deleteBtn.setOnClickListener((v) -> {
            confirmAndDelete();
        });
        editBtn.setOnClickListener((v) -> {
            Intent intent = new Intent((MainFrame) context, MapsActivity.class);
            try {
                intent.putExtra("zone", Array.get(position).ToJsonObject().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((MainFrame) context).startActivity(intent);
        });


    }

    private void confirmAndDelete() {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                .setIcon(R.drawable.ic_zone_pin)
                .setPositiveButton("Delete", (dialog1, which) -> {
                    WebServiceZone WZ = new WebServiceZone(ZonesAdapter.this.context, new CallBackWSConsumerSend<Zone>() {
                        @Override
                        public void OnResultPresent() {
                            ZonesAdapter.this.remove(ZonesAdapter.this.getItem(position));
                            z.initData();
                            Toast.makeText(context, "Zone Deleted !", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void OnResultNull() {
                            Toast.makeText(context, "Something Went Wrong !", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void OnHostUnreachable() {
                            Toast.makeText(context, "Host unreachable!", Toast.LENGTH_LONG).show();
                        }
                    });
                    WZ.remove(Array.get(position));
                })
                .setNegativeButton("Cancel", (dialog1, which) -> {
                    Toast.makeText(context, "Operation Canceled !", Toast.LENGTH_SHORT).show();
                })
                .setTitle("Remove Zone").setMessage("Are you sure you wante to delete this zone ? any Plans realted to this zone will be deleted as well ! ").create();
        dialog.show();


    }
}

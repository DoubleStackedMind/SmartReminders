package com.android.esprit.smartreminders.Test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.esprit.smartreminders.Fragments.EventFragment;
import com.android.esprit.smartreminders.Fragments.TaskFragment;
import com.android.esprit.smartreminders.R;

public class addPlan extends AppCompatActivity {

    Fragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        Spinner sp = findViewById(R.id.spinnerType);


        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(adapterView.getContext(),""+sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                switch (sp.getSelectedItem().toString()) {
                    case "Event" :
                       frag = new TaskFragment();
                       FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragmentPlace,frag);
                        ft.commit();
                    case "Task" :
                        frag = new EventFragment();
                        FragmentManager fme = getFragmentManager();
                        FragmentTransaction fte = fme.beginTransaction();
                        fte.replace(R.id.fragmentPlace,frag);
                        fte.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

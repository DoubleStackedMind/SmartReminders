package com.android.esprit.smartreminders.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.android.esprit.smartreminders.Entities.Trigger;
import com.android.esprit.smartreminders.R;

import java.util.ArrayList;

class TriggersAdapter extends CustomAdapter<Trigger> {
    public TriggersAdapter(@NonNull Context context, ArrayList<Trigger> triggers) {
        super(context,triggers, R.layout.single_trigger_layout);
    }

    @Override
    public void InflateInputs(View convertView) {

    }
}

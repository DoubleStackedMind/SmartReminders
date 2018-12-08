package com.android.esprit.smartreminders.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.esprit.smartreminders.Entities.Action;
import com.android.esprit.smartreminders.R;

import java.util.ArrayList;
import java.util.Set;

public class ActionsAdapter extends CustomAdapter<Action> {
    public ActionsAdapter(@NonNull Context context, ArrayList<Action> Array) {
        super(context, Array, R.layout.single_action_layout);
    }
    @Override
    public void InflateInputs(View convertView) {
        ImageView icon =convertView.findViewById(R.id.icon_action);
        TextView name=convertView.findViewById(R.id.actionName);
        Action a=Array.get(position);
        icon.setImageResource(a.getIcon());
        name.setText(a.getName());
    }
}

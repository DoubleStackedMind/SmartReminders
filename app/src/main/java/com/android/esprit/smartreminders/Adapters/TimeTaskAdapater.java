package com.android.esprit.smartreminders.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.Time;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Entities.Zone;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Fragments.FragmentChild;
import com.android.esprit.smartreminders.Fragments.FragmentFormTimeTask;
import com.android.esprit.smartreminders.Fragments.TasksFragment;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerSend;
import com.android.esprit.smartreminders.Services.WebServiceTimeTask;
import com.android.esprit.smartreminders.Services.WebServiceZone;
import com.android.esprit.smartreminders.activities.MainFrame;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.Set;

public class TimeTaskAdapater extends CustomAdapter<TimeTask> {
    private TasksFragment t;

    public TimeTaskAdapater(@NonNull Context context, ArrayList<TimeTask> Array, int SingleLayout, TasksFragment t) {
        super(context, Array, SingleLayout);
        this.t = t;
    }

    @Override
    public View inflateView(LayoutInflater inflater) {
        this.SingleLayOut = R.layout.single_timetask_layout;
        return super.inflateView(inflater);
    }

    @Override
    public void InflateInputs(View convertView) {

        TimeTask tt = Array.get(position);
        TextView ExecutionTime = convertView.findViewById(R.id.StartTime);
        ExecutionTime.setText(tt.getExecutionTime().toString());
        TextView Description = convertView.findViewById(R.id.titleView);
        TextView Mon = convertView.findViewById(R.id.mondayToggle);
        TextView Tue = convertView.findViewById(R.id.TuesdayToggle);
        TextView Wen = convertView.findViewById(R.id.wednesdayToggle);
        TextView Thu = convertView.findViewById(R.id.thursdayToggle);
        TextView Fri = convertView.findViewById(R.id.fridayToggle);
        TextView Sat = convertView.findViewById(R.id.saturdayToggle);
        TextView Sun = convertView.findViewById(R.id.sundayToggle);

        Set<DayOfTheWeek> Days = tt.getDays();
        Description.setText(tt.getDescription());

        Button updateBtn = convertView.findViewById(R.id.updatePlan_btn);
        Button deleteBtn = convertView.findViewById(R.id.deletePlan_btn);


        if (Days.contains(DayOfTheWeek.Monday)) {
            Mon.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Tuesday)) {
            Tue.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Wednesday)) {
            Wen.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Thursday)) {
            Thu.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Friday)) {
            Fri.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Saturday)) {
            Sat.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }
        if (Days.contains(DayOfTheWeek.Sunday)) {
            Sun.setBackground(convertView.getResources().getDrawable(R.drawable.roundbutton_active));
        }

        LinearLayout ActionLinearLayout = convertView.findViewById(R.id.ActionsLinearlayout);
        tt.getActions().forEach((a) -> {
            System.out.println(a);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.single_action_layout, null, true);
            ImageView icon = v.findViewById(R.id.icon_action);
            TextView name = v.findViewById(R.id.actionName);
            icon.setImageResource(a.getIcon());
            name.setText(a.getName());
            ActionLinearLayout.addView(v);
        });


        updateBtn.setOnClickListener((view) -> {
                    ((MainFrame) this.context).setEditedObject(tt);
                    ((MainFrame) this.context).goToUnStackedFragment(new FragmentFormTimeTask());
                });
        deleteBtn.setOnClickListener((view) -> {
            confirmAndDelete();
        });

    }

    private void confirmAndDelete() {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                .setIcon(R.drawable.icon_settings)
                .setPositiveButton("Delete", (dialog1, which) -> {
                    WebServiceTimeTask WT = new WebServiceTimeTask(TimeTaskAdapater.this.context, new CallBackWSConsumerSend<TimeTask>() {
                        @Override
                        public void OnResultPresent() {
                            TimeTaskAdapater.this.remove(TimeTaskAdapater.this.getItem(position));
                            t.initData();
                            Toast.makeText(context, "Time Task Deleted !", Toast.LENGTH_LONG).show();
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
                    WT.remove(Array.get(position));
                })
                .setNegativeButton("Cancel", (dialog1, which) -> {
                    Toast.makeText(context, "Operation Canceled !", Toast.LENGTH_SHORT).show();
                })
                .setTitle("Remove TimeTask").setMessage("Are you sure you wante to delete this TimeTask ?  ").create();
        dialog.show();


    }
}

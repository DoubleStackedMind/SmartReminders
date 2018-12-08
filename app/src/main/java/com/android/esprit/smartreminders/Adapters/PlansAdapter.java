package com.android.esprit.smartreminders.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.esprit.smartreminders.Entities.AbstractEventOrTask;
import com.android.esprit.smartreminders.Entities.Action;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.Task;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Entities.Trigger;
import com.android.esprit.smartreminders.Entities.TriggerTask;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.R;

import java.util.ArrayList;
import java.util.Set;

public class PlansAdapter extends CustomAdapter<AbstractEventOrTask> {
    public PlansAdapter(@NonNull Context context, ArrayList<AbstractEventOrTask> Array ) {
        super(context, Array, R.layout.single_event_layout);
    }

    @Override
    public View inflateView(LayoutInflater inflater) {

        if (Array.get(position) instanceof TimeTask) {
            this.SingleLayOut = R.layout.single_timetask_layout;
        } else if (Array.get(position) instanceof TriggerTask) {
            this.SingleLayOut = R.layout.single_triggertask_layout;

        } else if (Array.get(position) instanceof Event) {
            this.SingleLayOut = R.layout.single_event_layout;
        }
        return super.inflateView(inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void InflateInputs(View convertView) {
        AbstractEventOrTask instance = Array.get(position);
        TextView Description = convertView.findViewById(R.id.titleView);
        TextView Mon = convertView.findViewById(R.id.mondayToggle);
        TextView Tue = convertView.findViewById(R.id.TuesdayToggle);
        TextView Wen = convertView.findViewById(R.id.wednesdayToggle);
        TextView Thu = convertView.findViewById(R.id.thursdayToggle);
        TextView Fri = convertView.findViewById(R.id.fridayToggle);
        TextView Sat = convertView.findViewById(R.id.saturdayToggle);
        TextView Sun = convertView.findViewById(R.id.sundayToggle);

        Set<DayOfTheWeek> Days = instance.getDays();
        Description.setText(instance.getDescription());

        Button updateBtn=convertView.findViewById(R.id.updatePlan_btn);
        Button deleteBtn=convertView.findViewById(R.id.deletePlan_btn);

        updateBtn.setOnClickListener((view)->{
            // navigate to update page
        });
        deleteBtn.setOnClickListener((view)->{
           // prompt delete warning
        });

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

        if (instance instanceof Task) {
            Task t = (Task) instance;
            LinearLayout ActionLinearLayout = convertView.findViewById(R.id.ActionsLinearlayout);
            t.getActions().forEach((a)->{
                System.out.println(a);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View v =inflater.inflate(R.layout.single_action_layout,null,true);
                ImageView icon =v.findViewById(R.id.icon_action);
                TextView name=v.findViewById(R.id.actionName);
                icon.setImageResource(a.getIcon());
                name.setText(a.getName());
                ActionLinearLayout.addView(v);
                    });


            if (instance instanceof TimeTask) {
                TimeTask tt = (TimeTask) instance;
                TextView ExecutionTime = convertView.findViewById(R.id.StartTime);
                ExecutionTime.setText(tt.getExecutionTime().toString());
            } else if (instance instanceof TriggerTask) {
                TriggerTask trt = (TriggerTask) instance;
                LinearLayout TriggerLinearLayout = convertView.findViewById(R.id.triggerLinearlayout);
                trt.getTriggers().forEach((tr)->{
                    System.out.println(tr);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View v =inflater.inflate(R.layout.single_trigger_layout,null,true);
                    ImageView icon =v.findViewById(R.id.icon_trigger);
                    TextView name=v.findViewById(R.id.triggerName);
                    icon.setImageResource(tr.getIcon());
                    name.setText(tr.getName());
                    TriggerLinearLayout.addView(v);
                });
            }
        } else if (instance instanceof Event) {
            Event ev = (Event) Array.get(position);
            TextView StartTime = convertView.findViewById(R.id.StartTime);
            TextView EndTime = convertView.findViewById(R.id.EndTime);

            StartTime.setText(ev.getStartTime().toString());
            EndTime.setText(ev.getEndTime().toString());


        }

    }
}

package com.android.esprit.smartreminders.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Fragments.EventsndMettingsFragment;
import com.android.esprit.smartreminders.Fragments.FragmentFormEvent;
import com.android.esprit.smartreminders.Fragments.FragmentFormTimeTask;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerSend;
import com.android.esprit.smartreminders.Services.WebServiceEvent;
import com.android.esprit.smartreminders.Services.WebServiceTimeTask;
import com.android.esprit.smartreminders.activities.MainFrame;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Set;


public class EventsAdapter extends CustomAdapter<Event> {
    private EventsndMettingsFragment e;


    public EventsAdapter(@NonNull Context context, ArrayList<Event> Array, int SingleLayout ,EventsndMettingsFragment e) {
        super(context, Array, SingleLayout);
        this.e = e;
    }

    public View inflateView(LayoutInflater inflater) {


        this.SingleLayOut = R.layout.single_event_layout;

        return super.inflateView(inflater);
    }

    @Override
    public void InflateInputs(View convertView) {
        Event ev = Array.get(position);
        TextView Description = convertView.findViewById(R.id.descriptionView);
        TextView Title= convertView.findViewById(R.id.titleView);
        TextView Mon = convertView.findViewById(R.id.mondayToggle);
        TextView Tue = convertView.findViewById(R.id.TuesdayToggle);
        TextView Wen = convertView.findViewById(R.id.wednesdayToggle);
        TextView Thu = convertView.findViewById(R.id.thursdayToggle);
        TextView Fri = convertView.findViewById(R.id.fridayToggle);
        TextView Sat = convertView.findViewById(R.id.saturdayToggle);
        TextView Sun = convertView.findViewById(R.id.sundayToggle);
        TextView startTime=convertView.findViewById(R.id.StartTime);
        TextView endtime=convertView.findViewById(R.id.EndTime);
        TextView reminderETA=convertView.findViewById(R.id.reminderMinutesCount);

        reminderETA.setText("Remind me "+ev.getReminder()+" Minutes in advance");
        Set<DayOfTheWeek> Days = ev.getDays();
        Description.setText(ev.getDescription());
        Title.setText(ev.getTitle());
        startTime.setText("From "+ev.getStartTime().toString());
        endtime.setText(" To"+ev.getEndTime());



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

        updateBtn.setOnClickListener((view) -> {
            ((MainFrame) this.context).setEditedObject(ev);
            ((MainFrame) this.context).goToUnStackedFragment(new FragmentFormEvent());
        });
        deleteBtn.setOnClickListener((view) -> {
            confirmAndDelete();
        });

    }

    private void confirmAndDelete() {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                .setIcon(R.drawable.icon_calendar)
                .setPositiveButton("Delete", (dialog1, which) -> {
                    WebServiceEvent WE = new WebServiceEvent(EventsAdapter.this.context, new CallBackWSConsumerSend<Event>() {
                        @Override
                        public void OnResultPresent() {
                            EventsAdapter.this.remove(EventsAdapter.this.getItem(position));
                        //    e.initData();
                            Toast.makeText(context, "Event Deleted !", Toast.LENGTH_LONG).show();
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
                    WE.remove(Array.get(position));
                })
                .setNegativeButton("Cancel", (dialog1, which) -> {
                    Toast.makeText(context, "Operation Canceled !", Toast.LENGTH_SHORT).show();
                })
                .setTitle("Remove TimeTask").setMessage("Are you sure you wante to delete this Event ?  ").create();
        dialog.show();


    }

}

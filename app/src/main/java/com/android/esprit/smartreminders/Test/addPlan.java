package com.android.esprit.smartreminders.Test;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.esprit.smartreminders.Fragments.EventFragment;
import com.android.esprit.smartreminders.Fragments.TaskFragment;
import com.android.esprit.smartreminders.R;

import java.util.Calendar;

public class addPlan extends AppCompatActivity {

    Fragment frag;
    EditText title;
    EditText description;
    public String[] Days = new String[7];

    private NotificationHelper mNotificationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationHelper = new NotificationHelper(this);
        Spinner sp = findViewById(R.id.spinnerType);
        Behavior();
        getDays();
        addPlan();

    }
    public void Behavior() {
        frag = new EventFragment();
        FragmentManager fme = getFragmentManager();
        FragmentTransaction fte = fme.beginTransaction();
        fte.replace(R.id.fragmentPlace,frag);
        fte.commit();
        setContentView(R.layout.activity_add_plan);
        SpinnerBehavior();

    }

    public void SpinnerBehavior() {
        Spinner sp = findViewById(R.id.spinnerType);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(adapterView.getContext(),""+sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                switch (sp.getSelectedItem().toString()) {
                    case "Task" :
                        frag = new TaskFragment();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.fragmentPlace,frag);
                        ft.commit();
                        break;
                    case "Event" :
                        frag = new EventFragment();
                        FragmentManager fme = getFragmentManager();
                        FragmentTransaction fte = fme.beginTransaction();
                        fte.replace(R.id.fragmentPlace,frag);
                        fte.commit();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void addPlan() {
        Button Btn = findViewById(R.id.AddPlan);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDays();
                FragmentManager fm = getFragmentManager();
                TaskFragment fragment = (TaskFragment) fm.findFragmentById(R.id.fragment_Task);
                   for(int i=0; i<7;i++) {
                       System.out.println("Day : " + Days[i]);
                       if (Days[i] != null) {
                           setAlarm(i, fragment.hours, fragment.mins, 0);
                           SendNotification(((EditText)findViewById(R.id.Title)).getText().toString(), ((EditText)findViewById(R.id.description)).getText().toString());
                           Toast.makeText(addPlan.this, "Plan added", Toast.LENGTH_LONG).show();
                       }
                   }

            }

        });
    }

    public void getDays() {
        Button SundayButton = findViewById(R.id.pushb_sunday);
        Button MondayButton = findViewById(R.id.pushb_monday);
        Button TuesdayButton = findViewById(R.id.pushb_tuesday);
        Button WednesdayButton = findViewById(R.id.pushb_wednesday);
        Button ThursdayButton = findViewById(R.id.pushb_thursday);
        Button FridayButton = findViewById(R.id.pushb_friday);
        Button SaturdayButton = findViewById(R.id.pushb_saturday);

        SundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[0] = "Sunday";
                SundayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                SundayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        MondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[1] = "Monday";
                MondayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                MondayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        TuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[2] = "Tuesday";
                TuesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                TuesdayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        WednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[3] = "Wednesday";
                WednesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                WednesdayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        ThursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[4] = "Thursday";
                ThursdayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                ThursdayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        FridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[5] = "Friday";
                FridayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                FridayButton.setTextColor(getResources().getColor(R.color.white));
            }
        });
        SaturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Days[6] = "Saturday";
                SaturdayButton.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                SaturdayButton  .setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    public void setAlarm(int dayOfWeek, int AlarmHrsInInt, int AlarmMinsInInt,int amorpm) {
        // Add this day of the week line to your existing code
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt);
        alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.AM_PM, amorpm);

        Long alarmTime = alarmCalendar.getTimeInMillis();
        //Also change the time to 24 hours.
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        am.setExact(AlarmManager.RTC_WAKEUP,alarmCalendar.getTimeInMillis(),pendingIntent);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000 , pendingIntent);
    }

    public  void cancelAlarm() {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);

        am.cancel(pendingIntent);
    }
    public void SendNotification(String title,String description) {
        NotificationCompat.Builder nb  = mNotificationHelper.getChannelNotification(title,description);
        mNotificationHelper.getManager().notify(1, nb.build());
    }
}



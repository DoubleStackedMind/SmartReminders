package com.android.esprit.smartreminders.Fragments;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.esprit.smartreminders.Adapters.myDbAdapter;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Test.NotificationHelper;
import com.android.esprit.smartreminders.Test.Notification_reciever;

import java.util.Calendar;

public class FragmentFormTimeTask extends FragmentChild implements TimePickerDialog.OnTimeSetListener, View.OnClickListener{

    int hour,minute;
    public int hourFinal, minuteFinal;
    myDbAdapter helper;

    Button Timebtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_timetask, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
        mNotificationHelper = new NotificationHelper(FragmentFormTimeTask.this.ParentActivity);
        getDays();
        addPlan();
    }

    private void initViews() {

    }
    private void defineBehaviour() {
        Button Timebtn = this.ParentActivity.findViewById(R.id.TimeBtn);
        Timebtn.setOnClickListener(this);
//        Timebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//                hour = c.get(Calendar.HOUR_OF_DAY);
//                minute = c.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(getParentActivity().getApplicationContext(), FragmentFormTimeTask.this::onTimeSet
//                        ,hour, minute, DateFormat.is24HourFormat(getActivity()));
//                timePickerDialog.show();
//            }
//        });
    }

        Fragment frag;
        EditText title;
        EditText description;
        public String[] Days = new String[7];

        private NotificationHelper mNotificationHelper;


        public void addPlan() {

            Button Btn = this.ParentActivity.findViewById(R.id.AddPlan);
            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDays();
                    for(int i=0; i<7;i++) {
                        System.out.println("Day : " + Days[i]);
                        if (Days[i] != null) {
                            setAlarm(i+1, hourFinal, minuteFinal, 0);
                            long id = helper.insertData(((EditText)getActivity().getParent().findViewById(R.id.Title)).getText().toString(),((EditText)getActivity().getParent().findViewById(R.id.description)).getText().toString(),hourFinal+":"+minuteFinal);
                            Toast.makeText(getParentActivity(), "Plan added", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            });
        }

        public void getDays() {
            Button SundayButton = this.ParentActivity.findViewById(R.id.pushb_sunday);
            Button MondayButton = this.ParentActivity.findViewById(R.id.pushb_monday);
            Button TuesdayButton = this.ParentActivity.findViewById(R.id.pushb_tuesday);
            Button WednesdayButton = this.ParentActivity.findViewById(R.id.pushb_wednesday);
            Button ThursdayButton = this.ParentActivity.findViewById(R.id.pushb_thursday);
            Button FridayButton = this.ParentActivity.findViewById(R.id.pushb_friday);
            Button SaturdayButton = this.ParentActivity.findViewById(R.id.pushb_saturday);

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

        @TargetApi(Build.VERSION_CODES.M)
        public void setAlarm(int dayOfWeek, int AlarmHrsInInt, int AlarmMinsInInt, int amorpm) {
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

            alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt);
            alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt);
            alarmCalendar.set(Calendar.SECOND, 0);
            alarmCalendar.set(Calendar.AM_PM, amorpm);

            Long alarmTime = alarmCalendar.getTimeInMillis();

            AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
            Intent intent = new Intent(this.ParentActivity.getApplicationContext(),Notification_reciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);
            am.setExact(AlarmManager.RTC_WAKEUP,alarmTime,pendingIntent);
            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000 , pendingIntent);
            NotificationManager n = (NotificationManager) this.getParentActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if(n.isNotificationPolicyAccessGranted()) {
                AudioManager audioManager = (AudioManager) this.getParentActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }else{
                Intent intent2 = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent2);
            }
        }

        public  void cancelAlarm() {
            AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
            Intent intent = new Intent(this.ParentActivity.getApplicationContext(),Notification_reciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);

            am.cancel(pendingIntent);
        }
        public void SendNotification(String title,String description) {
            NotificationCompat.Builder nb  = mNotificationHelper.getChannelNotification(title,description);
            mNotificationHelper.getManager().notify(1, nb.build());
        }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Button Timebtn = this.ParentActivity.findViewById(R.id.TimeBtn);
        Timebtn.setText("Time set : "+hourOfDay + ":" +minute);
    }

    @Override
    public void onClick(View view) {

            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
            timePickerDialog.show();

    }

}





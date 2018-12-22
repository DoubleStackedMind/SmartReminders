package com.android.esprit.smartreminders.Fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Test.Notification_reciever;

import java.util.Calendar;

public class FragmentFormEvent extends FragmentChild implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Button pushBSunday;
    private Button getPushBMonday;
    private Button pushBTuesday;
    private Button pushBWednsday;
    private Button pushBThursday;
    private Button pushBFriday;
    private Button pushBSaturday;
    private Button StartTimeBtn;
    private Button EndTimeBtn;
    private EditText title;
    private Spinner tasks;
    private int hour;
    private int minute;
    private int StartHour;
    private int StartMin;
    private int EndHour;
    private int EndMin;
    private Button RemindMeOn;
    private String[] Days = new String[7];
    private EditText executionTime;
    int number=0;

    private NumberPicker np;

    private static final int Time_PICKER_ID = 0;

    TimePickerDialog.OnTimeSetListener StartListener, EndListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_event, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();

    }

    private void initViews() {

          pushBSunday=this.ParentActivity.findViewById(R.id.pushb_sunday);
          pushBSunday.setOnClickListener(this);
          getPushBMonday=this.ParentActivity.findViewById(R.id.pushb_monday);
        getPushBMonday.setOnClickListener(this);
          pushBTuesday=this.ParentActivity.findViewById(R.id.pushb_tuesday);
        pushBTuesday.setOnClickListener(this);
          pushBWednsday=this.ParentActivity.findViewById(R.id.pushb_wednesday);
        pushBWednsday.setOnClickListener(this);
          pushBThursday=this.ParentActivity.findViewById(R.id.pushb_thursday);
        pushBThursday.setOnClickListener(this);
          pushBFriday=this.ParentActivity.findViewById(R.id.pushb_friday);
        pushBFriday.setOnClickListener(this);
          pushBSaturday=this.ParentActivity.findViewById(R.id.pushb_saturday);
        pushBSaturday.setOnClickListener(this);

          title=this.ParentActivity.findViewById(R.id.title);
          tasks=this.ParentActivity.findViewById(R.id.spinnerTask);

      /*    StartTimeBtn = this.ParentActivity.findViewById(R.id.StartTimeBtn);
          StartTimeBtn.setOnClickListener(this);
          EndTimeBtn = this.ParentActivity.findViewById(R.id.EndTimeBtn);
          EndTimeBtn.setOnClickListener(this);

          RemindMeOn = this.ParentActivity.findViewById(R.id.RemindMeOn);
          RemindMeOn.setOnClickListener(this);
          np = new NumberPicker(getActivity());
          */
    }
    private void defineBehaviour() {
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pushb_sunday :
                    pushBSunday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                    pushBSunday.setTextColor(getResources().getColor(R.color.white));
                    Days[0] = "Sunday";
                break;
            case R.id.pushb_monday :
                getPushBMonday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                getPushBMonday.setTextColor(getResources().getColor(R.color.white));
                Days[1] = "Monday";
                break;
            case R.id.pushb_friday :
                pushBFriday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                pushBFriday.setTextColor(getResources().getColor(R.color.white));
                Days[5] = "Friday";
                break;
            case R.id.pushb_saturday :
                pushBSaturday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                pushBSaturday.setTextColor(getResources().getColor(R.color.white));
                Days[6] = "Saturday";
                break;
            case R.id.pushb_thursday :
                pushBThursday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                pushBThursday.setTextColor(getResources().getColor(R.color.white));
                Days[4] = "Thursday";
                break;
            case R.id.pushb_tuesday :
                pushBTuesday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                pushBTuesday.setTextColor(getResources().getColor(R.color.white));
                Days[2] = "Tuesday";
                break;
            case R.id.pushb_wednesday :
                pushBWednsday.setBackground(getResources().getDrawable(R.drawable.roundbuttonclicked));
                pushBWednsday.setTextColor(getResources().getColor(R.color.white));
                Days[3] = "Wednesday";
                break;
            case R.id.StartTimeBtn :
                Calendar c = Calendar.getInstance();
                StartHour = c.get(Calendar.HOUR_OF_DAY);
                StartMin = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), FragmentFormEvent.this::onTimeSet, StartHour, StartMin, DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
                break;
            case R.id.EndTimeBtn :
                Calendar c2 = Calendar.getInstance();
                EndHour = c2.get(Calendar.HOUR_OF_DAY);
                EndMin = c2.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(getActivity(), FragmentFormEvent.this::onTimeSet2, EndHour, EndMin, DateFormat.is24HourFormat(getActivity()));
                timePickerDialog2.show();
                break;
            case R.id.RemindMeOn :
//                Calendar c3 = Calendar.getInstance();
//                hour = c3.get(Calendar.HOUR_OF_DAY);
//                minute = c3.get(Calendar.MINUTE);
//                TimePickerDialog timePickerDialog3 = new TimePickerDialog(getActivity(), FragmentFormEvent.this::onTimeSet2, hour, minute, DateFormat.is24HourFormat(getActivity()));
//                timePickerDialog3.show();
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Remind Me Before");

                np.setMinValue(0);
                np.setMaxValue(59);
                np.setWrapSelectorWheel(true);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        number=0;
                        minute = i1;
                       number=i1;

                    }
                });
                alertDialog.setView(np);
             alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Choose", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     Toast.makeText(getActivity(), number+" Before the event", Toast.LENGTH_SHORT).show();
                 }
             });
             if(!alertDialog.isShowing())
             alertDialog.show();
             break;
        case R.id.AddPlan:
            for(int j=0;j<7;j++) {
                if(Days[j] != null) {
                    System.out.println("---------------------- Day : "+Days[j]);
                    setAlarm(j,StartHour,StartMin,1);
                }
            }
            break;
        }
    }



    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        StartTimeBtn.setText("Time set : "+i+":"+i1);


    }

    public void onTimeSet2(TimePicker timePicker, int i, int i1) {
        EndTimeBtn.setText("Time set : "+i+":"+i1);
    }


    public void setAlarm(int dayOfWeek, int AlarmHrsInInt, int AlarmMinsInInt,int amorpm) {
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt);
        System.out.println("########################### HOUR : "+alarmCalendar.get(Calendar.HOUR));
        alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt);
        alarmCalendar.add(Calendar.MINUTE,-number+2);
        System.out.println("########################### MINUTES : "+alarmCalendar.get(Calendar.MINUTE));
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.AM_PM, amorpm);

        Long alarmTime = alarmCalendar.getTimeInMillis();

        AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
        Intent intent = new Intent(this.ParentActivity.getApplicationContext(),Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);
        am.setExact(AlarmManager.RTC_WAKEUP,alarmTime,pendingIntent);
        Toast.makeText(getActivity(),"Notification Activated",Toast.LENGTH_SHORT).show();
        am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000 , pendingIntent);
    }
}

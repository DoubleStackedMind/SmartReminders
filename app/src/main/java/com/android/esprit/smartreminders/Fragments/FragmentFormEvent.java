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

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Test.Notification_reciever;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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
    private int hour;
    private int minute;
    private int StartHour;
    private int StartMin;
    private int EndHour;
    private int EndMin;
    private Button RemindMeOn;
    private Button AddPlan;
   private Set<DayOfTheWeek> SelectedDays;
    private String[] Days = new String[7];
    private EditText executionTime;
    int number = 0;

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
        np = new NumberPicker(this.getContext());
       SelectedDays = new HashSet<>();
        pushBSunday = this.ParentActivity.findViewById(R.id.pushb_sunday);
        pushBSunday.setOnClickListener(this);
        getPushBMonday = this.ParentActivity.findViewById(R.id.pushb_monday);
        getPushBMonday.setOnClickListener(this);
        pushBTuesday = this.ParentActivity.findViewById(R.id.pushb_tuesday);
        pushBTuesday.setOnClickListener(this);
        pushBWednsday = this.ParentActivity.findViewById(R.id.pushb_wednesday);
        pushBWednsday.setOnClickListener(this);
        pushBThursday = this.ParentActivity.findViewById(R.id.pushb_thursday);
        pushBThursday.setOnClickListener(this);
        pushBFriday = this.ParentActivity.findViewById(R.id.pushb_friday);
        pushBFriday.setOnClickListener(this);
        pushBSaturday = this.ParentActivity.findViewById(R.id.pushb_saturday);
        pushBSaturday.setOnClickListener(this);
        StartTimeBtn = this.ParentActivity.findViewById(R.id.StartTimeBtn);
        StartTimeBtn.setOnClickListener(this);
        EndTimeBtn = this.ParentActivity.findViewById(R.id.EndTimeBtn);
        EndTimeBtn.setOnClickListener(this);
        RemindMeOn = this.ParentActivity.findViewById(R.id.RemindMeOn);
        RemindMeOn.setOnClickListener(this);
        AddPlan = this.ParentActivity.findViewById(R.id.AddPlan);
        AddPlan.setOnClickListener(this);

    }

    private void defineBehaviour() {
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.pushb_sunday:
                if (SelectedDays.contains(DayOfTheWeek.Sunday)) {
                    pushBSunday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBSunday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Sunday);
                } else {
                    pushBSunday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBSunday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Sunday);
                }
                break;
            case R.id.pushb_monday:
                if (SelectedDays.contains(DayOfTheWeek.Monday)) {
                    getPushBMonday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    getPushBMonday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Monday);
                } else {
                    getPushBMonday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    getPushBMonday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Monday);
                }
                break;
            case R.id.pushb_friday:
                if (SelectedDays.contains(DayOfTheWeek.Friday)) {
                    pushBFriday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBFriday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Friday);
                } else {
                    pushBFriday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBFriday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Friday);
                }
                break;
            case R.id.pushb_saturday:
                if (SelectedDays.contains(DayOfTheWeek.Saturday)) {
                    pushBSaturday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBSaturday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Saturday);
                } else {
                    pushBSaturday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBSaturday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Saturday);
                }
                break;
            case R.id.pushb_thursday:
                if (SelectedDays.contains(DayOfTheWeek.Thursday)) {
                    pushBThursday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBThursday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Thursday);
                } else {
                    pushBThursday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBThursday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Thursday);
                }
                break;
            case R.id.pushb_tuesday:
                if (SelectedDays.contains(DayOfTheWeek.Tuesday)) {
                    pushBTuesday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBTuesday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Tuesday);
                } else {
                    pushBTuesday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBTuesday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Tuesday);
                }

                break;
            case R.id.pushb_wednesday:
                if (SelectedDays.contains(DayOfTheWeek.Wednesday)) {
                    pushBWednsday.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                    pushBWednsday.setTextColor(getResources().getColor(R.color.white));
                    SelectedDays.remove(DayOfTheWeek.Wednesday);
                } else {
                    pushBWednsday.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                    pushBWednsday.setTextColor(getResources().getColor(R.color.black));
                    SelectedDays.add(DayOfTheWeek.Wednesday);
                }

                break;
            case R.id.StartTimeBtn:
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), FragmentFormEvent.this::onTimeSet, StartHour, StartMin, DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
                System.out.println(" ####################### Start Min : "+StartMin);
                break;
            case R.id.EndTimeBtn:
                Calendar c2 = Calendar.getInstance();
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(getActivity(), FragmentFormEvent.this::onTimeSet2, EndHour, EndMin, DateFormat.is24HourFormat(getActivity()));
                timePickerDialog2.show();
                System.out.println(" ######################## End Min : "+EndMin);
                break;
            case R.id.RemindMeOn:
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
                        number = 0;
                        minute = i1;
                        number = i1;

                    }
                });
                if(np.getParent() == null) {
                    alertDialog.setView(np);
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Choose", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getActivity(), number + " minutes Before the event", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (!alertDialog.isShowing())
                        alertDialog.show();
                } else {
                    ((ViewGroup)np.getParent()).removeView(np);
                }
                break;
            case R.id.AddPlan:
                for(DayOfTheWeek d : SelectedDays) {
                    switch (d) {
                        case Sunday:
                            setAlarm(0, StartHour, StartMin,1);
                            break;
                        case Monday:
                            setAlarm(1, StartHour, StartMin,1);
                            break;
                        case Tuesday:
                            setAlarm(2, StartHour, StartMin,1);
                            break;
                        case Wednesday:
                            setAlarm(3, StartHour, StartMin,1);
                            break;
                        case Thursday:
                            setAlarm(4, StartHour, StartMin,1);
                            break;
                        case Friday:
                            setAlarm(5, StartHour, StartMin,1);
                            break;
                        case Saturday:
                            setAlarm(6, StartHour, StartMin,1);
                            break;
                    }
                }
                break;
        }
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        StartTimeBtn.setText("Time set : " + i + ":" + i1);
        StartHour = i;
        StartMin = i1-number;
    }

    public void onTimeSet2(TimePicker timePicker, int i, int i1) {
        EndTimeBtn.setText("Time set : " + i + ":" + i1);
        EndHour = i;
        EndMin = i1;
    }


    public void setAlarm(int dayOfWeek, int AlarmHrsInInt, int AlarmMinsInInt, int amorpm) {
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt);
        alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt);
        System.out.println("########## Reminder SET FOR : "+AlarmHrsInInt +" : "+AlarmMinsInInt);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.AM_PM, amorpm);

        Long alarmTime = alarmCalendar.getTimeInMillis();

        AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
        Intent intent = new Intent(this.ParentActivity.getApplicationContext(), Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        Toast.makeText(getActivity(), "Notification Activated", Toast.LENGTH_SHORT).show();
        am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000, pendingIntent);
    }
}

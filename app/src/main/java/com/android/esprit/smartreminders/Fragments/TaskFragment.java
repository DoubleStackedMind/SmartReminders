package com.android.esprit.smartreminders.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.text.format.DateFormat;

import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.activities.MainFrame;

import java.sql.Time;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    int hour,minute;
    public int hourFinal, minuteFinal;
    Button Timebtn;
    public static int hours ;
    public static  int mins ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        Timebtn = view.findViewById(R.id.TimeBtn);
        Timebtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Timebtn.setText(hourOfDay + ":" +minute);
        hours = hourOfDay;
        mins = minute;
    }

    @Override
    public void onClick(View view) {

     if(view.getId() == Timebtn.getId()) {
         Calendar c = Calendar.getInstance();
         hour = c.get(Calendar.HOUR_OF_DAY);
         minute = c.get(Calendar.MINUTE);

         TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
         timePickerDialog.show();
     }
    }

}

package com.android.esprit.smartreminders.Test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.esprit.smartreminders.Adapters.DailyScheduleAdapter;
import com.android.esprit.smartreminders.Entities.AbstractEventOrTask;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.Task;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.R;

import java.sql.Time;
import java.util.ArrayList;

public class ActivityTest extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
      ListView  plansHolder = (ListView) this.findViewById(R.id.todays_schedule_lv);
        ArrayList<AbstractEventOrTask> lista = new ArrayList<>();
        lista.add(new Event(StateOfTask.CANCELED, "This is a second test", new Time(19, 30, 00), new Time(20, 30, 00)));
        lista.add(  new Task(StateOfTask.PENDING, " This is a Test", new Time(15, 30, 0)));
        lista.add(  new Task(StateOfTask.PENDING, " This is a Test", new Time(15, 30, 0)));


        plansHolder.setAdapter(new DailyScheduleAdapter(this, lista));

    }
}

package com.android.esprit.smartreminders.customControllers;

import android.content.Context;

import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerGET;
import com.android.esprit.smartreminders.Services.WebServiceEvent;
import com.android.esprit.smartreminders.broadcastrecivers.AlarmReceiver;
import com.android.esprit.smartreminders.notifications.NotificationScheduler;
import com.android.esprit.smartreminders.sessions.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCustomController {


    private User sessionUser;

    private List<Event> myEvents = new ArrayList<>();

    public void TriggerAlarm(Context context) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("I'M INVOKED :D ");
        WebServiceEvent ws = new WebServiceEvent(context, new CallBackWSConsumerGET<Event>() {
            @Override
            public void OnResultPresent(List<Event> results) {
                myEvents = (ArrayList<Event>) results;
                for(Event e : myEvents) {
                    for(DayOfTheWeek d : e.getDays()) {
                        if(d == DayOfTheWeek.DayOfWeekForID(day)) {
                            if(calendar.getTime().getHours() <= e.getStartTime().getHour() && calendar.getTime().getMinutes() <= e.getStartTime().getMinute()) {
                                System.out.println("I'M INSIDE THE 3rd SCOOP");
                                System.out.println("EVENT NAME : " + e.getTitle() + " EVENT HOUR : " + e.getStartTime().getHour() + ":" + e.getStartTime().getMinute());
                                NotificationScheduler.setReminder(context, AlarmReceiver.class, e.getStartTime().getHour(), e.getStartTime().getMinute());
                            }
                        } else {
                            NotificationScheduler.setInExactReminder(context,AlarmReceiver.class, e.getStartTime().getHour(),e.getStartTime().getMinute());
                        }
                    }
                }
            }
            @Override
            public void OnResultNull() {
            }

            @Override
            public void OnHostUnreachable() {
            }
        });
        Map<String, String> map = new HashMap<>();
        map.put("user", Session.getSession(context).getSessionUser().getId() + "");
        try {
            ws.findBy(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

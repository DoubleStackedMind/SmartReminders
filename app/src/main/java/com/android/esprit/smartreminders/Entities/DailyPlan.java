package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;

import java.util.List;

public class DailyPlan {
    DayOfTheWeek Day;
    List<AbstractEventOrTask> Plans;
    public DailyPlan(DayOfTheWeek Day){
    this.Day=Day;
    }
    public DailyPlan(){}
}

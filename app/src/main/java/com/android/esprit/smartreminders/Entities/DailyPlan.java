package com.android.esprit.smartreminders.Entities;

import java.util.List;
import java.util.stream.Collectors;

public class DailyPlan {
    List<AbstractEventOrTask> Plans;
    public DailyPlan(){}
    public List<AbstractEventOrTask> getTodaysPlans(){
        return Plans.stream().filter((p)->p.isMemberOfToday()).collect(Collectors.toList());
    }
}

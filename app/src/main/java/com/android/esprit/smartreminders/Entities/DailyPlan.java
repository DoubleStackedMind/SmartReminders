package com.android.esprit.smartreminders.Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DailyPlan {
   private List<AbstractEventOrTask> Plans;
    public DailyPlan(){
        this.Plans=new ArrayList<>();
    }
    public List<AbstractEventOrTask> getTodaysPlans(){
        if(Plans.size()==0){
            System.out.println("true");
            return Plans;
        }

        return Plans.stream().filter((p)->p.isMemberOfToday()).collect(Collectors.toList());
    }
}

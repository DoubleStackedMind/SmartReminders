package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import java.util.Objects;

public abstract class  AbstractEventOrTask {
    protected StateOfTask state;
    protected String description;
    protected DayOfTheWeek day;
    public AbstractEventOrTask(){}
    public AbstractEventOrTask(StateOfTask state,String description,DayOfTheWeek day){
        this.state=state;
        this.description=description;
        this.day=day;
    }

    public StateOfTask getState() {
        return state;
    }

    public void setState(StateOfTask state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DayOfTheWeek getDay() {
        return day;
    }

    public void setDay(DayOfTheWeek day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEventOrTask that = (AbstractEventOrTask) o;
        return state == that.state &&
                Objects.equals(description, that.description) &&
                day == that.day;
    }

    @Override
    public int hashCode() {

        return Objects.hash(state, description, day);
    }

    @Override
    public String toString(){
        return "state="+
                state+
                ","+
                "Description="+
                description+
                ", "+
                "Day of Week="+
                day;
    }
}

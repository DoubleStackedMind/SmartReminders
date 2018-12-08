package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class  AbstractEventOrTask {
    protected StateOfTask state;
    protected String description;
    protected Set<DayOfTheWeek> days;
    public AbstractEventOrTask(){}
    public AbstractEventOrTask(StateOfTask state,String description,Set<DayOfTheWeek> days){
        this.state=state;
        this.description=description;
        this.days=days;
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

    public Set<DayOfTheWeek> getDays() {
        return days;
    }

    public void setDay(Set<DayOfTheWeek> days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEventOrTask that = (AbstractEventOrTask) o;
        return state == that.state &&
                Objects.equals(description, that.description) &&
                days == that.days;
    }

    @Override
    public int hashCode() {

        return Objects.hash(state, description, days);
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
                days;
    }
}

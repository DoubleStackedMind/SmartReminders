package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import java.util.Objects;

public abstract class  AbstractEventOrTask {
    protected StateOfTask state;
    protected String description;
    protected DayOfTheWeek day;
    public AbstractEventOrTask(){}
    public AbstractEventOrTask(StateOfTask state,String description){
        this.state=state;
        this.description=description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEventOrTask that = (AbstractEventOrTask) o;
        return state == that.state &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, description);
    }
    @Override
    public String toString(){
        return "state="+
                state+
                ","+
                "Description="+
                description;
    }
}

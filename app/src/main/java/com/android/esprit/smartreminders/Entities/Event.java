package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import java.sql.Time;
import java.util.Objects;

public class Event extends AbstractEventOrTask {
    protected Time StartTime ;
    protected Time EndTime ;


    public Event() {super();}
    public Event(StateOfTask state, String description, DayOfTheWeek day, Time StartTime, Time EndTime) {
        super(state,description,day);
        this.StartTime = StartTime;
        this.EndTime = EndTime;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time endTime) {
        EndTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Event event = (Event) o;
        return Objects.equals(StartTime, event.StartTime) &&
                Objects.equals(EndTime, event.EndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), StartTime, EndTime);
    }

    @Override
    public String toString() {
        return "Event{" +
                super.toString()+
                "StartTime=" + StartTime +
                ", EndTime=" + EndTime +
                '}';
    }
}

package com.android.esprit.smartreminders.Entities;

import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class  Task extends AbstractEventOrTask{
    protected Set<Action> actions;

    public Task() {
    }

    public Task(StateOfTask state, String description, Set<DayOfTheWeek> days) {
        super(state, description, days);
    }

    public Set<Action> getActions() {
        return actions;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "Task{" +
                "actions=" + actions +
                ", state=" + state +
                ", description='" + description + '\'' +
                ", days=" + days +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(actions, task.actions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), actions);
    }
}

package com.android.esprit.smartreminders.Fragments;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import com.android.esprit.smartreminders.Entities.Action;
import com.android.esprit.smartreminders.Entities.Time;
import com.android.esprit.smartreminders.Entities.TimeTask;
import com.android.esprit.smartreminders.Enums.DayOfTheWeek;
import com.android.esprit.smartreminders.Enums.StateOfTask;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumer;
import com.android.esprit.smartreminders.Services.WebServiceTimeTask;
import com.android.esprit.smartreminders.Test.NotificationHelper;
import com.android.esprit.smartreminders.Test.Notification_reciever;
import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;
import com.android.esprit.smartreminders.appcommons.validator.EditTextRequiredInputValidator;
import com.android.esprit.smartreminders.customControllers.ActionPool;
import com.android.esprit.smartreminders.notifications.Message;
import com.android.esprit.smartreminders.sessions.Session;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FragmentFormTimeTask extends FragmentChild implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private Set<DayOfTheWeek> SelectedDays;
    private Set<Action> SelectedActions;
    private Time executionTime;
    private TimeTask timeTask;
    private Button SundayButton;
    private Button MondayButton;
    private Button TuesdayButton;
    private Button WednesdayButton;
    private Button ThursdayButton;
    private Button FridayButton;
    private Button SaturdayButton;
    private Button Timebtn;
    private NotificationHelper mNotificationHelper;
    private Button addPlanbtn;
    private TextView actions;
    private TextInputLayout Title;
    private TextInputLayout Description;
    private boolean[] checkedItems;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;
    private String[] vals;
    private boolean updateMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_form_timetask, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        defineBehaviour();
        mNotificationHelper = new NotificationHelper(FragmentFormTimeTask.this.ParentActivity);
        updateMode = false;
        initForm();
    }

    private void initViews() {
        SelectedDays = new HashSet<>();
        SelectedActions = new HashSet<>();
        SundayButton = this.ParentActivity.findViewById(R.id.pushb_sunday);
        MondayButton = this.ParentActivity.findViewById(R.id.pushb_monday);
        TuesdayButton = this.ParentActivity.findViewById(R.id.pushb_tuesday);
        WednesdayButton = this.ParentActivity.findViewById(R.id.pushb_wednesday);
        ThursdayButton = this.ParentActivity.findViewById(R.id.pushb_thursday);
        FridayButton = this.ParentActivity.findViewById(R.id.pushb_friday);
        SaturdayButton = this.ParentActivity.findViewById(R.id.pushb_saturday);
        Timebtn = this.ParentActivity.findViewById(R.id.TimeBtn);
        addPlanbtn = this.ParentActivity.findViewById(R.id.AddPlan);
        timeTask = new TimeTask();
        actions = this.ParentActivity.findViewById(R.id.actionsText);
        Title = this.ParentActivity.findViewById(R.id.title);
        Description = this.ParentActivity.findViewById(R.id.description);
        vals = new String[ActionPool.getInstance(this.ParentActivity).getActions().length];
    }

    private void defineBehaviour() {
        Button Timebtn = this.ParentActivity.findViewById(R.id.TimeBtn);
        Timebtn.setOnClickListener(this);
        setExecutionDaysBehaviour();
        addPlanbtn.setOnClickListener(view -> addPlan());
        actions.setOnClickListener(view -> selectActions());
        vals = new String[ActionPool.getInstance(this.ParentActivity).getActions().length];
        Arrays.stream(ActionPool.getInstance(this.ParentActivity).getActions()).map(Action::getName).collect(Collectors.toList()).toArray(vals);
        checkedItems = new boolean[vals.length];
    }

    private void selectActions() {
        mBuilder = new AlertDialog.Builder(this.ParentActivity);
        mBuilder.setTitle("Select Actions you Want to Execute");


        Arrays.stream(ActionPool.getInstance(this.ParentActivity).getActions()).map(Action::getName).collect(Collectors.toList()).toArray(vals);

        mBuilder.setMultiChoiceItems(vals, checkedItems, (DialogInterface.OnMultiChoiceClickListener) (dialogInterface, position, isChecked) -> {
            Action a = ActionPool.getInstance(FragmentFormTimeTask.this.ParentActivity).getActions()[position];
            actions.setText("");
            if (isChecked) {
                SelectedActions.add(a);
                System.out.println(SelectedActions);
            } else {
                SelectedActions.remove(a);
            }
        });

        mBuilder.setCancelable(false);

        mBuilder.setPositiveButton("Done", (dialogInterface, which) -> {
            String value = "";
            actions.setText(value);
            for (Action e : SelectedActions) {
                value += e.getName() + "\n";
            }
            if (value.length() != 0)
                value = value.substring(0, value.length() - 1);
            actions.setText(value);

        });
        mBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        mBuilder.setNeutralButton("Clear All", (dialogInterface, which) -> {
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = false;
            }
            SelectedActions = new HashSet<>();
            actions.setText("");

        });

        mDialog = mBuilder.create();
        mDialog.show();
    }


    private void addPlan() {
        if (isFormValid()) {
            updateDataBase();
        } else {
            if (isTitleAlreadyTaken())
                Message.message(FragmentFormTimeTask.this.ParentActivity.getApplicationContext(), "The Time Task Name is already taken !");
            else
                Message.message(FragmentFormTimeTask.this.ParentActivity.getApplicationContext(), "Check your inputs please!");
        }

    }

    private void updateDataBase() {

        WebServiceTimeTask ws = new WebServiceTimeTask(FragmentFormTimeTask.this.ParentActivity, new CallBackWSConsumer<TimeTask>() {
            @Override
            public void OnResultPresent() {
                String msg="";
                if (updateMode)
                     msg = "Time Task Updated !";
                else
                     msg = "Time Task Added !";
                Message.message(FragmentFormTimeTask.this.ParentActivity.getApplicationContext(), msg);
            }
            @Override
            public void OnHostUnreachable() {
                Message.message(FragmentFormTimeTask.this.ParentActivity.getApplicationContext(), getString(R.string.hostunreachable));
            }
        });
        TimeTask t = new TimeTask();
        if(updateMode)
        t.setId(timeTask.getId());
        t.setExecutionTime(executionTime);
        t.setActions(SelectedActions);
        t.setDay(SelectedDays);
        t.setDescription(Description.getEditText().getText().toString());
        t.setState(StateOfTask.IN_PROGRESS);
        t.setOwner(Session.getSession(this.getParentActivity()).getSessionUser());
        t.setTitle(Title.getEditText().getText().toString());
        if (updateMode){
            ws.update(t);
        }

        else
            ws.insert(t);
    }

    private void setExecutionDaysBehaviour() {
        SundayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Sunday)) {
                SundayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                SundayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Sunday);
            } else {
                SundayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                SundayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Sunday);
            }
        });
        MondayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Monday)) {
                MondayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                MondayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Monday);
            } else {
                MondayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                MondayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Monday);
            }
        });
        TuesdayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Tuesday)) {
                TuesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                TuesdayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Tuesday);
            } else {
                TuesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                TuesdayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Tuesday);
            }

        });
        WednesdayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Wednesday)) {
                WednesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                WednesdayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Wednesday);
            } else {
                WednesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                WednesdayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Wednesday);
            }

        });
        ThursdayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Thursday)) {
                ThursdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                ThursdayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Thursday);
            } else {
                ThursdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                ThursdayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Thursday);
            }

        });
        FridayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Friday)) {
                FridayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                FridayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Friday);
            } else {
                FridayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                FridayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Friday);
            }

        });
        SaturdayButton.setOnClickListener(view -> {
            if (SelectedDays.contains(DayOfTheWeek.Saturday)) {
                SaturdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton));
                SaturdayButton.setTextColor(getResources().getColor(R.color.white));
                SelectedDays.remove(DayOfTheWeek.Saturday);
            } else {
                SaturdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
                SaturdayButton.setTextColor(getResources().getColor(R.color.black));
                SelectedDays.add(DayOfTheWeek.Saturday);
            }

        });
    }

    public void setAlarm(int dayOfWeek, int AlarmHrsInInt, int AlarmMinsInInt, int amorpm) {
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        alarmCalendar.set(Calendar.HOUR, AlarmHrsInInt);
        alarmCalendar.set(Calendar.MINUTE, AlarmMinsInInt);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.AM_PM, amorpm);

        Long alarmTime = alarmCalendar.getTimeInMillis();

        AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
        Intent intent = new Intent(this.ParentActivity.getApplicationContext(), Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        am.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, 24 * 60 * 60 * 1000, pendingIntent);
        NotificationManager n = (NotificationManager) this.getParentActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (n.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) this.getParentActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            Intent intent2 = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent2);
        }
    }

    public void cancelAlarm() {
        AlarmManager am = (AlarmManager) this.ParentActivity.getSystemService(this.ParentActivity.ALARM_SERVICE);
        Intent intent = new Intent(this.ParentActivity.getApplicationContext(), Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.ParentActivity.getApplicationContext(), 1, intent, 0);

        am.cancel(pendingIntent);
    }

    public void SendNotification(String title, String description) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(title, description);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String str = "Time set : " + hourOfDay + ":" + minute;
        Timebtn.setText(str);
        executionTime = new Time(hourOfDay, minute);
    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }

    private boolean isFormValid() {
        return !(EditTextUtils.isInValid(new EditTextRequiredInputValidator(this.Title.getEditText())) && (EditTextUtils.isInValid(new EditTextRequiredInputValidator(this.Description.getEditText())) &&
                SelectedActions.size() == 0 && SelectedDays.size() == 0 && executionTime == null));
    }


    private boolean isTitleAlreadyTaken() {

        return false;
    }

    private void initForm() {
        this.timeTask = (TimeTask) ((MainFrame) getParentActivity()).getEditedObject();
        if (this.timeTask != null) {
            updateMode = true;
            this.SelectedDays = timeTask.getDays();
            this.SelectedActions = timeTask.getActions();
            this.Title.getEditText().setText(timeTask.getTitle());
            this.Description.getEditText().setText(timeTask.getDescription());
            this.executionTime = timeTask.getExecutionTime();
            String str = "Time set : " + timeTask.getExecutionTime().getHour() + ":" + timeTask.getExecutionTime().getMinute();
            Timebtn.setText(str);

            for (int i = 0; i < checkedItems.length; i++) {
                for (Action e : SelectedActions)
                    if (vals[i].equals(e.getName()))
                        checkedItems[i] = true;
            }
            String value = "";
            for (Action e : SelectedActions) {
                value += e.getName() + "\n";
            }
            value = value.substring(0, value.length() - 1);
            actions.setText(value);
            updateDaysButtons();

            this.addPlanbtn.setText("UPDATE TIME TASK");

        }
    }

    private void updateDaysButtons() {
        if (timeTask.getDays().contains(DayOfTheWeek.Monday)) {

            MondayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            MondayButton.setTextColor(getResources().getColor(R.color.black));

        }
        if (timeTask.getDays().contains(DayOfTheWeek.Sunday)) {

            SundayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            SundayButton.setTextColor(getResources().getColor(R.color.black));
        }
        if (timeTask.getDays().contains(DayOfTheWeek.Tuesday)) {

            TuesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            TuesdayButton.setTextColor(getResources().getColor(R.color.black));
        }
        if (timeTask.getDays().contains(DayOfTheWeek.Thursday)) {

            ThursdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            ThursdayButton.setTextColor(getResources().getColor(R.color.black));
        }
        if (timeTask.getDays().contains(DayOfTheWeek.Friday)) {

            FridayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            FridayButton.setTextColor(getResources().getColor(R.color.black));
        }
        if (timeTask.getDays().contains(DayOfTheWeek.Saturday)) {

            SaturdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            SaturdayButton.setTextColor(getResources().getColor(R.color.black));
        }
        if (timeTask.getDays().contains(DayOfTheWeek.Wednesday)) {

            WednesdayButton.setBackground(getResources().getDrawable(R.drawable.roundbutton_active));
            WednesdayButton.setTextColor(getResources().getColor(R.color.black));
        }


    }

}





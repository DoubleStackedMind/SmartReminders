package com.android.esprit.smartreminders.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.esprit.smartreminders.Entities.AbstractEventOrTask;
import com.android.esprit.smartreminders.Entities.Event;
import com.android.esprit.smartreminders.Entities.TriggerTask;
import com.android.esprit.smartreminders.R;

import java.util.ArrayList;

public class DailyScheduleAdapter extends CustomAdapter<AbstractEventOrTask> {

    public DailyScheduleAdapter(@NonNull Context context, ArrayList<AbstractEventOrTask> Array) {
        super(context, Array, R.layout.today_schedule_single);
    }

    @Override
    public void InflateInputs(View convertView) {

        ImageView recydledImaveView = (ImageView) convertView.findViewById(R.id.state);

        TextView recycleddescription = (TextView) convertView.findViewById(R.id.description);

        TextView recycledtime = (TextView) convertView.findViewById(R.id.time);

    /*    if (this.Array.get(position) instanceof TriggerTask) {
            TriggerTask t = (TriggerTask) this.Array.get(position);
            recycledtime.setText(t.getExecutionTime().toString());
            recydledImaveView.setImageResource(t.getState().StateForIcon(this.context));
            recycleddescription.setText(t.getDescription());
        } else if (this.Array.get(position) instanceof Event) {
            Event t = (Event) this.Array.get(position);
            recycledtime.setText(t.getStartTime().toString()+"-"+t.getEndTime().toString());
            recydledImaveView.setImageResource(t.getState().StateForIcon(this.context));
            recycleddescription.setText(t.getDescription());
        }
*/

    }
}

package com.android.esprit.smartreminders.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.esprit.smartreminders.R;

import java.util.ArrayList;

public abstract class CustomAdapter<T> extends ArrayAdapter {
    private ArrayList<T> Array;
    private Context context;
    private int SingleLayOut;// Reference to Layout for Single item

    public CustomAdapter(@NonNull Context context, ArrayList<T>Array,int SingleLayOut) {
        super(context ,SingleLayOut,Array);
        this.context=context;
        this.Array=Array;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        assert inflater != null;
        @SuppressLint("ViewHolder") final View rowView = inflater.inflate(SingleLayOut, null, true);
        //this code gets references to objects in the listview_row.xml file
        InflateInputs();
        return rowView;
    }
    public abstract void InflateInputs();// must define a behavour for each inflator

}

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
    protected ArrayList<T> Array;
    protected Context context;
    protected int SingleLayOut;// Reference to Layout for Single item

    public CustomAdapter(@NonNull Context context, ArrayList<T>Array,int SingleLayOut) {
        super(context ,SingleLayOut,Array);
        this.context=context;
        this.Array=Array;
        this.SingleLayOut=SingleLayOut;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        assert inflater != null;
        final View rowView = inflater.inflate(SingleLayOut, null, true);
        //this code gets references to objects in the listview_row.xml file
        System.out.println("position "+position);
        InflateInputs(rowView,position);
        return rowView;
    }
    public abstract void InflateInputs(View convertView,int pos);// must define a behaviour for each inflater

}

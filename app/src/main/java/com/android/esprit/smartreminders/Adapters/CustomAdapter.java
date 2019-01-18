package com.android.esprit.smartreminders.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public abstract class CustomAdapter<T> extends ArrayAdapter {
    protected ArrayList<T> Array;
    protected Context context;
    protected int position;
    protected int SingleLayOut;// Reference to Layout for Single item

    public CustomAdapter(@NonNull Context context, ArrayList<T>Array,int SingleLayout) {
        super(context ,SingleLayout,Array);
        this.context=context;
        this.Array=Array;
        this.position=0;
        this.SingleLayOut=SingleLayout;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        assert inflater != null;
        this.position=position;

        System.out.println(this.position);
        final View rowView=inflateView(inflater);
        //this code gets references to objects in the listview_row.xml file
        InflateInputs(rowView);
        return rowView;
    }
    public View inflateView(LayoutInflater inflater){
        return inflater.inflate(SingleLayOut,null,true);
    }
    public abstract void InflateInputs(View convertView);// must define a behaviour for each inflater

}

package com.todos.hkboam.todos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.todos.hkboam.todos.R;

import java.util.ArrayList;

/**
 * Created by HK-Lab on 18/10/2015.
 */
public class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(Context context, ArrayList<String> textList) {
        super(context, 0, textList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String text = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        String text2;

        if (text.length() > 100) {
            text2 = text.substring(0, 97) + "...";
        } else {
            text2 = text;
        }
        Log.d("Essai", text2);
        // Lookup view for data population
        TextView myText = (TextView) convertView.findViewById(R.id.myText);
        // Populate the data into the template view using the data object
        myText.setText(text2);
        // Return the completed view to render on screen
        return convertView;
    }

}

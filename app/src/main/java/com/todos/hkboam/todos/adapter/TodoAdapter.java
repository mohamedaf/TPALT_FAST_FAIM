package com.todos.hkboam.todos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.modal.Todo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hassan K on 30/10/2015.
 */
public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(Context context, java.util.List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Get the data item for this position
        Todo todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        convertView.setId((int) todo.getId());

        Date date = new Date(todo.getModification_date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String sDate = "";
        sDate += cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);

        Log.i("Dates", sDate);

        TextView listItem_content = (TextView) convertView.findViewById(R.id.listItem_content);
        TextView listItem_date = (TextView) convertView.findViewById(R.id.listItem_date);

        listItem_content.setText(todo.getContent());
        listItem_date.setText(sDate);

        return convertView;
    }

}

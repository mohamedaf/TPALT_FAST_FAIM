package com.todos.hkboam.todos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.Todo;
import com.todos.hkboam.todos.bdd.modal.TodoList;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hassan K on 30/10/2015.
 */
public class TodoListAdapter extends ArrayAdapter<Todo> {
    public TodoListAdapter(Context context, java.util.List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Todo item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        convertView.setId((int) item.getId());

        Date date = new Date(item.getModification_date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String sDate = "";
        sDate += cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);

        Log.i("Dates", sDate);

        CheckBox memo_date = (CheckBox) convertView.findViewById(R.id.checkbox);
        memo_date.setChecked(item.getDone() == 1);

        memo_date.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setDone(1);
                        } else {
                            item.setDone(0);
                        }
                        ListItemDAO listItemDAO = new ListItemDAO(getContext());
                        listItemDAO.open();
                        listItemDAO.modifier(item);
                        listItemDAO.close();

                        Log.i(item.getId() + "", "checked : " + isChecked);
                    }
                }
        );

        TextView memo_title = (TextView) convertView.findViewById(R.id.listItem_content);


        memo_title.setText(item.getContent());


        return convertView;
    }

}

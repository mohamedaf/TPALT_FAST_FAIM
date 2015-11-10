package com.todos.hkboam.todos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.Todo;
import com.todos.hkboam.todos.bdd.modal.TodoList;
import com.todos.hkboam.todos.bdd.modal.UserTodoList;

import java.util.ArrayList;

/**
 * Created by HK-Lab on 18/10/2015.
 */
public class MainAdapter extends ArrayAdapter<TodoList> {
    ListItemDAO listItemDAO = new ListItemDAO(super.getContext());

    public MainAdapter(Context context, ArrayList<TodoList> textList) {
        super(context, 0, textList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoList item = getItem(position);

        String text = item.getTitle();

        listItemDAO.open();
        ArrayList<Todo> todos = listItemDAO.getItemsByListId(item.getId());
        listItemDAO.close();

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
        TextView myText = (TextView) convertView.findViewById(R.id.myText);
        myText.setText(text2);

        int nb = nbChecked(todos);
        ImageView myImage = (ImageView) convertView.findViewById(R.id.myImage);

        //Si aucun ToDo a été fait
        if (nb == 0) {
            myImage.setImageResource(R.drawable.not_done);
        } //Si tous les ToDo ont été faitss
        else if (nb == todos.size()) {
            myImage.setImageResource(R.drawable.done);
        } //Sinon
        else {
            myImage.setImageResource(R.drawable.edit);
        }


        return convertView;
    }

    private boolean allChecked(ArrayList<Todo> todos) {
        boolean ch = true;
        for (Todo todo : todos) {
            ch &= (todo.getDone() == 1);
        }
        return ch;
    }

    private boolean noOneChecked(ArrayList<Todo> todos) {
        boolean ch = true;
        for (Todo todo : todos) {
            ch &= (todo.getDone() == 0);
        }
        return ch;
    }

    private int nbChecked(ArrayList<Todo> todos) {
        int cpt = 0;
        for (Todo todo : todos) {
            if (todo.getDone() == 1)
                cpt++;
        }
        return cpt;
    }

}

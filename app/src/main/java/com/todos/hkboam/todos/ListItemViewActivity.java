package com.todos.hkboam.todos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.List;
import com.todos.hkboam.todos.bdd.modal.ListItem;

public class ListItemViewActivity extends AppCompatActivity {
    private ListItem listItem;
    private ListItemDAO listItemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);
        Intent intent = getIntent();
        listItemDAO = new ListItemDAO(this);
        listItem = listItemDAO.selectionner(intent.getLongExtra("list_id",0));
        TextView edit = (TextView) findViewById(R.id.memo_view);


        if (listItem != null) {
            edit.setText(listItem.getContent());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.memo_edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListItemViewActivity.this, ListItemEditActivity.class);
                intent.putExtra("list_id", listItem.getId());
                startActivity(intent);
            }
        });
    }

}

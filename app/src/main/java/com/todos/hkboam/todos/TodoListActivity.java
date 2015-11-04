package com.todos.hkboam.todos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.todos.hkboam.todos.adapter.MainAdapter;
import com.todos.hkboam.todos.adapter.TodoListAdapter;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.Todo;
import com.todos.hkboam.todos.bdd.modal.TodoList;
import com.todos.hkboam.todos.dialog.ToDoDialogFragment;
import com.todos.hkboam.todos.dialog.ToDoListDialogFragment;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {
    private TodoList todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoDialogFragment toDoDialogFragment = new ToDoDialogFragment();
                Bundle b = new Bundle();
                b.putLong("listId", todoList.getId());
                toDoDialogFragment.setArguments(b);
                toDoDialogFragment.show(getFragmentManager(), "ToDo dialog");
            }
        });

        this.todoList = (TodoList) getIntent().getSerializableExtra("todoList");

    }

    @Override
    protected void onResume() {
        super.onResume();
        ListItemDAO listItemDAO = new ListItemDAO(this);
        listItemDAO.open();
        ArrayList<Todo> todos = listItemDAO.getItemsByListId(todoList.getId());
        listItemDAO.close();

        final ListView listView = (ListView) findViewById(R.id.mainListView);

        TodoListAdapter mA = new TodoListAdapter(this, todos);
        listView.setAdapter(mA);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}

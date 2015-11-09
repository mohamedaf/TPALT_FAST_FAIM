package com.todos.hkboam.todos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.todos.hkboam.todos.adapter.MainAdapter;
import com.todos.hkboam.todos.adapter.TodoListAdapter;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.Todo;
import com.todos.hkboam.todos.bdd.modal.TodoList;
import com.todos.hkboam.todos.dialog.ToDoDialogFragment;
import com.todos.hkboam.todos.dialog.ToDoListDialogFragment;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private TodoList todoList;
    ListItemDAO listItemDAO = new ListItemDAO(this);

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
        init();
    }

    private void init() {

        listItemDAO.open();
        ArrayList<Todo> todos = listItemDAO.getItemsByListId(todoList.getId());
        listItemDAO.close();

        final ListView listView = (ListView) findViewById(R.id.mainListView);

        TodoListAdapter mA = new TodoListAdapter(this, todos);


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                int cpt = listView.getCheckedItemCount();
                String s = listView.getCheckedItemCount() + " ";
                if (cpt == 1) {
                    s += getResources().getString(R.string.selected_item);
                }
                if (cpt > 1) {
                    s += getResources().getString(R.string.selected_items);
                }
                mode.setTitle(s);

                if (checked) {
                    listView.getChildAt(position).setBackgroundColor(0x6633b5e5);
                } else {
                    listView.getChildAt(position).setBackgroundColor(0x80ffffff);
                    listView.getChildAt(position).getBackground().setAlpha(0);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_remove:
                        long[] ids = listView.getCheckedItemIds();
                        SparseBooleanArray checked = listView.getCheckedItemPositions();
                        listItemDAO.open();
                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.valueAt(i) == true) {
                                Todo m = (Todo) listView.getItemAtPosition(checked.keyAt(i));

                                long userId = CurrentUser.getInstance().getUser().getId();
                                long listId = m.getId();

                                if (m.getAuthor() == userId) {
                                    listItemDAO.supprimer(m.getId());
                                }

                            }
                        }
                        listItemDAO.close();
                        init();
                        mode.finish();
                        return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


        listView.setAdapter(mA);

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

    @Override
    public void onDismiss(final DialogInterface dialog) {
        Log.i("Act", "dismiss");
        init();
    }
}

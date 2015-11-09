package com.todos.hkboam.todos;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.dao.TodoListDAO;
import com.todos.hkboam.todos.bdd.dao.UserTodoListDAO;
import com.todos.hkboam.todos.bdd.modal.TodoList;
import com.todos.hkboam.todos.bdd.modal.UserTodoList;
import com.todos.hkboam.todos.dialog.ToDoListDialogFragment;
import com.todos.hkboam.todos.dialog.UsersChoiceDialogFragment;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogInterface.OnDismissListener {
    private UserTodoListDAO userTodoListDAO = new UserTodoListDAO(this);
    private TodoListDAO todoListDAO = new TodoListDAO(this);
    private ListItemDAO listItemDAO = new ListItemDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                ToDoListDialogFragment toDoListDialogFragment = new ToDoListDialogFragment();
                toDoListDialogFragment.show(getFragmentManager(), "toDoListDialogFragment");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        Log.i("Act", "resume");
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Act", "pause");
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        Log.i("Act", "dismiss");
        init();
    }

    private void init() {
        long authorId = CurrentUser.getInstance().getUser().getId();
        userTodoListDAO.open();
        ArrayList<UserTodoList> ut_list = userTodoListDAO.getByUserId(authorId);
        userTodoListDAO.close();
        todoListDAO.open();
        final ArrayList<TodoList> todo_list = todoListDAO.selectionner(utlToListId(ut_list));
        todo_list.addAll(todoListDAO.getByAuthorId(authorId));
        todoListDAO.close();

        Collections.sort(todo_list);

        final ListView listView = (ListView) findViewById(R.id.mainListView);

        MainAdapter mA = new MainAdapter(this, todo_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
                intent.putExtra("todoList", todo_list.get(position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = UsersChoiceDialogFragment.newInstance(todo_list.get(position).getId());
                newFragment.show(ft, "dialog");
                return true;
            }
        });

//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
//
//
//            @Override
//            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
//                int cpt = listView.getCheckedItemCount();
//                String s = cpt + " ";
//                if (cpt == 1) {
//                    s += getResources().getString(R.string.selected_item);
//                }
//                if (cpt > 1) {
//                    s += getResources().getString(R.string.selected_items);
//                }
//                mode.setTitle(s);
//
//                if (checked) {
//                    listView.getChildAt(position).setBackgroundColor(0x6633b5e5);
//                } else {
//                    listView.getChildAt(position).setBackgroundColor(0x80ffffff);
//                    listView.getChildAt(position).getBackground().setAlpha(0);
//                }
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                MenuInflater inflater = mode.getMenuInflater();
//                int checkedCount = listView.getCheckedItemCount();
//                if (checkedCount == 1)
//                    inflater.inflate(R.menu.context_menu, menu);
//                else
//                    inflater.inflate(R.menu.context_menu2, menu);
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.context_share:
//                        SparseBooleanArray ch = listView.getCheckedItemPositions();
//                        todoListDAO.open();
//                        TodoList m1 = null;
//                        for (int i = 0; i < ch.size(); i++) {
//                            if (ch.valueAt(i) == true) {
//                                m1 = (TodoList) listView.getItemAtPosition(ch.keyAt(i));
//                                break;
//                            }
//                        }
//                        todoListDAO.close();
//
//                        UsersChoiceDialogFragment userChoiceDialogFragment = new UsersChoiceDialogFragment();
//                        Bundle b = new Bundle();
//                        b.putLong("listId", m1.getId());
//                        userChoiceDialogFragment.show(getFragmentManager(), "toDoListDialogFragment");
//                        return true;
//                    case R.id.context_remove:
//                        long[] ids = listView.getCheckedItemIds();
//                        SparseBooleanArray checked = listView.getCheckedItemPositions();
//                        todoListDAO.open();
//                        listItemDAO.open();
//                        userTodoListDAO.open();
//                        for (int i = 0; i < checked.size(); i++) {
//                            if (checked.valueAt(i) == true) {
//                                TodoList m = (TodoList) listView.getItemAtPosition(checked.keyAt(i));
//                                Log.i("Checked : ", m.getTitle());
//
//                                long userId = CurrentUser.getInstance().getUser().getId();
//                                long listId = m.getId();
//
//                                if (m.getAuthor() == userId) {
//                                    listItemDAO.deleteInList(listId);
//                                    userTodoListDAO.deleteInList(listId);
//                                    todoListDAO.supprimer(listId);
//                                } else {
//                                    userTodoListDAO.deleteUserFromList(userId, listId);
//                                }
//
//                            }
//                        }
//                        todoListDAO.close();
//                        listItemDAO.close();
//                        userTodoListDAO.close();
//                        init();
//                        mode.finish();
//                        return true;
//                }
//
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//                listView.clearChoices();
//            }
//        });

        listView.setAdapter(mA);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            CurrentUser.getInstance().setUser(null);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.nav_my_lists) {

        } else if (id == R.id.nav_synchronize) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<Long> utlToListId(ArrayList<UserTodoList> utl) {
        ArrayList<Long> res = new ArrayList<Long>(utl.size());
        for (UserTodoList u : utl) {
            res.add(u.getList());
        }
        return res;
    }


}

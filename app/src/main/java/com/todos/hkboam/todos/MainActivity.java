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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    private final ArrayList<TodoList> todo_list = new ArrayList<>();


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
        todo_list.clear();
        long authorId = CurrentUser.getInstance().getUser().getId();

        userTodoListDAO.open();
        ArrayList<UserTodoList> ut_list = userTodoListDAO.getByUserId(authorId);
        userTodoListDAO.close();
        todoListDAO.open();
        //On recupere les ToDoList partagées avec le user actuel
        todo_list.addAll(todoListDAO.selectionner(utlToListId(ut_list)));

        for (TodoList td : todo_list) {
            Log.i("shared with me", td.getId() + " " + td.getTitle() + " " + td.getAuthor());
        }

        //On recupere les ToDoList du user actuel
        todo_list.addAll(todoListDAO.getByAuthorId(authorId));
        todoListDAO.close();

        Collections.sort(todo_list);

        for (TodoList td : todo_list) {
            Log.i("todoList", td.getId() + " " + td.getTitle() + " " + td.getAuthor());
        }

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

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);


                DialogFragment newFragment = UsersChoiceDialogFragment.newInstance(todo_list.get(position).getId());
                newFragment.show(ft, "dialog");
                return true;
            }
        });*/

        listView.setAdapter(mA);
        registerForContextMenu(listView);
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
            case R.id.action_synchronize:
                init();
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
            init();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This will be invoked when an item in the listview is long pressed
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    /**
     * This will be invoked when a menu item is selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        TodoList tdl = todo_list.get(info.position);
        long userId = CurrentUser.getInstance().getUser().getId();
        long listId = tdl.getId();

        switch (item.getItemId()) {
            case R.id.context_share:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment newFragment = UsersChoiceDialogFragment.newInstance(
                        todo_list.get(info.position).getId());
                newFragment.show(ft, "dialog");
                break;
            case R.id.context_edit:
                if (tdl.getAuthor() == userId) {
                    ToDoListDialogFragment toDoListDialogFragment = new ToDoListDialogFragment();
                    Bundle b = new Bundle();
                    b.putLong(ToDoListDialogFragment.LIST_ID, listId);
                    toDoListDialogFragment.setArguments(b);
                    toDoListDialogFragment.show(getFragmentManager(), "ToDoList dialog");
                    init();
                } else {
                    Toast.makeText(this, R.string.no_action_edit, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.context_remove:

                todoListDAO.open();
                listItemDAO.open();
                userTodoListDAO.open();

                if (tdl.getAuthor() == userId) {
                    listItemDAO.deleteInList(listId);
                    userTodoListDAO.deleteInList(listId);
                    todoListDAO.supprimer(listId);
                } else {
                    userTodoListDAO.deleteUserFromList(userId, listId);
                }

                todoListDAO.close();
                listItemDAO.close();
                userTodoListDAO.close();
                init();
                break;

        }
        return true;
    }

    private ArrayList<Long> utlToListId(ArrayList<UserTodoList> utl) {
        ArrayList<Long> res = new ArrayList<>(utl.size());
        for (UserTodoList u : utl) {
            res.add(u.getList());
        }
        return res;
    }


}

package com.todos.hkboam.todos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.todos.hkboam.todos.adapter.TodoListAdapter;
import com.todos.hkboam.todos.bdd.dao.TodoListDAO;
import com.todos.hkboam.todos.bdd.modal.TodoList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TodoListDAO todoListDAO;

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
                Intent intent = new Intent(MainActivity.this, null);
                startActivity(intent);
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
    protected void onStart() {
        super.onStart();

        init();

    }

    private void init() {
        if (todoListDAO == null) {
            todoListDAO = new TodoListDAO(this);
        }
        final ArrayList<TodoList> tl_list = todoListDAO.toutSelectionner();

        final ListView listView = (ListView) findViewById(R.id.listView);

        TodoListAdapter mA = new TodoListAdapter(this, tl_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
                intent.putExtra("tl_id", tl_list.get(position).getId());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

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
                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.valueAt(i) == true) {
                                TodoList m = (TodoList) listView.getItemAtPosition(checked.keyAt(i));
                                Log.i("Checked : ", m.getTitle());
                                todoListDAO.open();
                                todoListDAO.supprimer(m.getId());
                                todoListDAO.close();
                                init();
                            }
                        }

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
        if (id == R.id.action_settings) {
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
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
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

}

package com.todos.hkboam.todos;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.todos.hkboam.todos.bdd.dao.ListDAO;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.List;
import com.todos.hkboam.todos.bdd.modal.ListItem;

import java.util.Calendar;

public class ListItemEditActivity extends AppCompatActivity {
    private ListItem listItem;
    private ListItemDAO listItemDAO;
    private EditText edit;
    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);
        Intent intent = getIntent();
        listItemDAO = new ListItemDAO(this);
        listItem = listItemDAO.selectionner(intent.getLongExtra("list_id",0));
        edit = (EditText) findViewById(R.id.memo_edit_edit);


        if (listItem != null) {
            edit.setText(listItem.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.memo_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_validate_edit || id == android.R.id.home) {

            save();
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        save();
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed();

    }

    private void save() {
        if (edit.getText().length() == 0) {
            t = Toast.makeText(this, R.string.memo_empty, Toast.LENGTH_SHORT);
            t.show();
        } else {
            if (listItem == null) {
                listItem = new ListItem(edit.getText().toString());
                listItem.setModification_date(Calendar.getInstance().getTimeInMillis());
                listItemDAO.ajouter(listItem);
            } else {
                listItem.setContent(edit.getText().toString());
                listItemDAO.modifier(listItem);
            }
            t = Toast.makeText(this, R.string.memo_save, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}

package com.todos.hkboam.todos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.todos.hkboam.todos.bdd.dao.MemoDAO;
import com.todos.hkboam.todos.bdd.modal.Memo;

import java.util.Calendar;

public class MemoEditActivity extends AppCompatActivity {
    private Memo memo;
    private MemoDAO memoDao;
    private EditText edit;
    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);
        Intent intent = getIntent();
        memo = (Memo) intent.getSerializableExtra("memo");
        edit = (EditText) findViewById(R.id.memo_edit_edit);


        if (memo != null) {
            edit.setText(memo.getContent());
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
            memoDao = new MemoDAO(this);
            memoDao.open();
            if (memo == null) {
                memo = new Memo(edit.getText().toString());
                memo.setModification_date(Calendar.getInstance().getTimeInMillis());
                memoDao.ajouter(memo);
            } else {
                memo.setContent(edit.getText().toString());
                memoDao.modifier(memo);
            }
            memoDao.close();
            t = Toast.makeText(this, R.string.memo_save, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}

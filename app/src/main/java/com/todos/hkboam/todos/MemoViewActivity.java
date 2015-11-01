package com.todos.hkboam.todos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.todos.hkboam.todos.bdd.modal.Memo;

public class MemoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);
        Intent intent = getIntent();
        final Memo memo = (Memo) intent.getSerializableExtra("memo");
        TextView edit = (TextView) findViewById(R.id.memo_view);


        if (memo != null) {
            edit.setText(memo.getContent());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.memo_edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoViewActivity.this, MemoEditActivity.class);
                intent.putExtra("memo", memo);
                startActivity(intent);
            }
        });
    }

}

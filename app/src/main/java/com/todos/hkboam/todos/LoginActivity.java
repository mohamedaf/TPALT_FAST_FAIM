package com.todos.hkboam.todos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.todos.hkboam.todos.Utils.Util;
import com.todos.hkboam.todos.bdd.dao.UserDAO;
import com.todos.hkboam.todos.bdd.modal.User;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView, mPasswordView;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        Button login = (Button) findViewById(R.id.btnLogin);

        mEmailView = (EditText) findViewById(R.id.log_email);
        mPasswordView = (EditText) findViewById(R.id.log_password);
        mError = (TextView) findViewById(R.id.login_field_warning);

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String mailV = isEmailValid(mEmailView.getText().toString());
                String passV = isPasswordValid(mPasswordView.getText().toString());

                // vider le champs d'erreur
                mError.setText("");

                if (mailV == null && passV == null) {
                    // find User
                    UserDAO userd = new UserDAO(LoginActivity.this);
                    userd.open();
                    User u = userd.selectionner(mEmailView.getText().toString(), Util.MD5(mPasswordView.getText().toString()));
                    userd.close();

                    if (u != null) {
                        CurrentUser.getInstance().setUser(u);
                        // Switching to main screen
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else
                        mError.setText(getString(R.string.error_login));
                } else if (mailV == null) {
                    mError.setText(passV);
                } else {
                    mError.setText(mailV);
                }
            }
        });
    }


    private String isEmailValid(String email) {
        String mailRegex = "^[a-zA-Z0-9\\.\\-_]+@[a-zA-Z0-9\\-_]+\\.[a-zA-Z]{2,4}$";

        if (email == null || email.equals("")) {
            return getString(R.string.error_fields);
        }
        if (email.length() > 40 || !email.matches(mailRegex)) {
            return getString(R.string.error_invalid_email);
        }

        return null;
    }

    private String isPasswordValid(String password) {
        if (password == null || password.equals("")) {
            return getString(R.string.error_fields);
        }
        if (password.length() < 6) {
            return getString(R.string.error_invalid_password);
        }

        return null;
    }
}


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
import android.widget.ListView;
import android.widget.TextView;

import com.todos.hkboam.todos.Utils.Util;
import com.todos.hkboam.todos.bdd.dao.UserDAO;
import com.todos.hkboam.todos.bdd.modal.User;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private EditText mFullName, mEmailView, mPasswordView;
    private TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button register = (Button) findViewById(R.id.btnRegister);

        mFullName = (EditText) findViewById(R.id.reg_fullname);
        mEmailView = (EditText) findViewById(R.id.reg_email);
        mPasswordView = (EditText) findViewById(R.id.reg_password);
        mError = (TextView) findViewById(R.id.register_field_warning);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String fullNameV = isFullNameValid(mFullName.getText().toString());
                String mailV = isEmailValid(mEmailView.getText().toString());
                String passV = isPasswordValid(mPasswordView.getText().toString());

                // vider le champs d'erreur
                mError.setText("");

                if(fullNameV != null){
                    Log.d("deb", "full name: " + mFullName.getText().toString());
                    Log.d("deb", "err full name: " + fullNameV);
                }
                if(mailV != null){
                    Log.d("deb", "mail: " + mEmailView.getText().toString());
                    Log.d("deb", "err mail: " + mailV);
                }
                if(passV != null){
                    Log.d("deb", "pass: " + mPasswordView.getText().toString());
                    Log.d("deb", "err pass: " + passV);
                }

                if(fullNameV == null && mailV == null && passV == null){
                    // register User
                    UserDAO userd = new UserDAO(RegisterActivity.this);
                    userd.open();
                    User u = new User(1, mFullName.getText().toString(), mEmailView.getText().toString(), mPasswordView.getText().toString());

                    // verifier si le mail existe déjà
                    User u2 = userd.selectionner(mEmailView.getText().toString());

                    if(u2 != null){
                        // adresse mail déjà existante erreur !
                        mError.setText(R.string.error_user_exist);
                        userd.close();
                    }
                    else{
                        userd.ajouter(u);
                        userd.close();

                        // debug
                        Log.d("deb", "deb register");
                        Log.d("deb", u.getUsername());
                        Log.d("deb", u.getMail());
                        Log.d("deb", u.getPassword());

                        // Switching to main screen
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
                if(fullNameV != null)
                    mError.setText(fullNameV);
                else if(mailV != null)
                    mError.setText(mailV);
                else if(passV != null)
                    mError.setText(passV);
            }
        });
    }

    private String isFullNameValid(String fullName){
        if (fullName == null || fullName.equals("")){
            return getString(R.string.error_fields);
        }
        if (fullName.length() > 40){
            return getString(R.string.error_fullName);
        }

        return null;
    }

    private String isEmailValid(String email) {
        String mailRegex = "^[a-zA-Z0-9\\.\\-_]+@[a-zA-Z0-9\\-_]+\\.[a-zA-Z]{2,4}$";

        if (email == null || email.equals("")){
            return getString(R.string.error_fields);
        }
        if (email.length() > 40 || !email.matches(mailRegex)){
            return getString(R.string.error_invalid_email);
        }

        return null;
    }

    private String isPasswordValid(String password) {
        if (password == null || password.equals("")){
            return getString(R.string.error_fields);
        }
        if (password.length() < 6){
            return getString(R.string.error_invalid_password);
        }

        return null;
    }
}


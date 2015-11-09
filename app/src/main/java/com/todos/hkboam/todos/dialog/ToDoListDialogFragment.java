package com.todos.hkboam.todos.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.todos.hkboam.todos.MainActivity;
import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.dao.TodoListDAO;
import com.todos.hkboam.todos.bdd.modal.TodoList;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.Calendar;

/**
 * Created by HK-Lab on 04/11/2015.
 */
public class ToDoListDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_edit, null));

        builder.setTitle(R.string.new_todo_list);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) getDialog().findViewById(R.id.edit);
                String text = editText.getText().toString();
                Log.i("Texte entre", text);
                if (text.length() > 0) {

                    TodoList td = new TodoList(Calendar.getInstance().getTimeInMillis(),
                            CurrentUser.getInstance().getUser().getId(), text);

                    TodoListDAO todoListDAO = new TodoListDAO(getActivity());
                    todoListDAO.open();
                    todoListDAO.ajouter(td);
                    todoListDAO.close();
                } else {
                    Toast.makeText(getActivity(), R.string.cannot_have_empty_title, Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


}

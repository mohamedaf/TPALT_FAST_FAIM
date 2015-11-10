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
    public final static String LIST_ID = "list_id";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final TodoListDAO todoListDAO = new TodoListDAO(getActivity());
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_edit, null));
        long id = -1;
        Bundle b = getArguments();
        if (b != null)
            id = getArguments().getLong(LIST_ID, -1);


        if (id == -1) {
            builder.setTitle(R.string.new_todo_list);
        } else {
            builder.setTitle(R.string.action_edit);
            todoListDAO.open();
            TodoList tdl = todoListDAO.selectionner(id);
            todoListDAO.close();
            EditText editText = (EditText) getView().findViewById(R.id.edit);
            editText.setText(tdl.getTitle());
        }


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) getDialog().findViewById(R.id.edit);
                String text = editText.getText().toString();
                Log.i("Texte entre", text);
                if (text.length() > 0) {


                    todoListDAO.open();
                    long time = Calendar.getInstance().getTimeInMillis();
                    long userId = CurrentUser.getInstance().getUser().getId();
                    if (id == -1) {
                        TodoList td = new TodoList(time,
                                userId, text);
                        todoListDAO.ajouter(td);
                    } else {
                        TodoList td = new TodoList(id, time,
                                userId, text);
                        todoListDAO.modifier(td);

                    }
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

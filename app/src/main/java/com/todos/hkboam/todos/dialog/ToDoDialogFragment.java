package com.todos.hkboam.todos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.dao.ListItemDAO;
import com.todos.hkboam.todos.bdd.modal.Todo;
import com.todos.hkboam.todos.persistent.CurrentUser;

import java.util.Calendar;

/**
 * Created by HK-Lab on 04/11/2015.
 */
public class ToDoDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_edit, null));

        builder.setTitle(R.string.new_todo);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = (EditText) getDialog().findViewById(R.id.edit);
                String text = editText.getText().toString();
                if (text.length() > 0) {

                    Todo td = new Todo(Calendar.getInstance().getTimeInMillis(),
                            getArguments().getLong("listId", -1),
                            CurrentUser.getInstance().getUser().getId(), text);

                    ListItemDAO listItemDAO = new ListItemDAO(getActivity());
                    listItemDAO.open();
                    listItemDAO.ajouter(td);
                    listItemDAO.close();
                } else {
                    Toast.makeText(getActivity(), R.string.cannot_have_empty_todo, Toast.LENGTH_SHORT).show();
                }
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}

package com.todos.hkboam.todos.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.dao.UserDAO;
import com.todos.hkboam.todos.bdd.dao.UserTodoListDAO;
import com.todos.hkboam.todos.bdd.modal.User;
import com.todos.hkboam.todos.bdd.modal.UserTodoList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HK-Lab on 08/11/2015.
 */
public class UsersChoiceDialogFragment extends DialogFragment {

    private final static String LIST_ID_ARGUMENT = "list_id";

    public static UsersChoiceDialogFragment newInstance(long listId) {
        UsersChoiceDialogFragment f = new UsersChoiceDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong(LIST_ID_ARGUMENT, listId);
        f.setArguments(args);

        return f;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Long> sharedWith = new ArrayList<>();  // Originaly selected items
        final ArrayList<Long> toAdd = new ArrayList<>();       // New items added
        final ArrayList<Long> toRemove = new ArrayList<>();    // items removed

        final long listId = getArguments().getLong(LIST_ID_ARGUMENT);

        UserDAO userDAO = new UserDAO(getActivity());
        UserTodoListDAO userTodoListDAO = new UserTodoListDAO(getActivity());

        userDAO.open();
        final ArrayList<User> users = userDAO.getAllExceptCurrent();
        userDAO.close();

        userTodoListDAO.open();
        final ArrayList<UserTodoList> sharedUsers = userTodoListDAO.getByListId(listId);
        userTodoListDAO.close();

        final int size = users.size();

        CharSequence[] items = new CharSequence[size];
        boolean[] checkedItems = new boolean[size];

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            items[i] = u.getUsername();
            checkedItems[i] = false;
            for (UserTodoList utl : sharedUsers) {
                if (u.getId() == utl.getUser()) {
                    checkedItems[i] = true;
                    sharedWith.add(u.getId());
                    break;
                }
            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.share)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(items, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                long id = users.get(which).getId();
                                if(sharedWith.contains(id)){
                                    if(isChecked){
                                        toRemove.remove(id);
                                    } else {
                                        toRemove.add(id);
                                    }
                                } else {
                                    if(isChecked){
                                        toAdd.add(id);
                                    } else {
                                        toAdd.remove(id);
                                    }
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        UserTodoListDAO userTodoListDAO = new UserTodoListDAO(getActivity());
                        userTodoListDAO.open();

                       for(long userId : toAdd){
                                userTodoListDAO.ajouter(new UserTodoList(0, userId, listId, 0));
                            }

                        //Si un user a été décoché et qu'il était coché auparavant
                        for(long userId : toRemove) {
                            userTodoListDAO.deleteUserFromList(userId, listId);
                        }

                        userTodoListDAO.close();
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}

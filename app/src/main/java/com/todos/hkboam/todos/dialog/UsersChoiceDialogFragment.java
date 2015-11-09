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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayList<Integer> mSelectedItems = new ArrayList<>();  // Where we track the selected items

        final long listId = getArguments().getInt("listId");

        HashMap<Integer, boolean[]> map = new HashMap<>();
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
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
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


                        for (int i = 0; i < size; i++) {
                            User u = users.get(i);
                            UserTodoList utl = null;
                            long userId = u.getId();

                            //On cherche si on la ToDo List a déjà été partagée avec ce user
                            for (UserTodoList tmp : sharedUsers) {
                                if (tmp.getUser() == userId) {
                                    utl = tmp;
                                    break;
                                }
                            }

                            boolean contains = mSelectedItems.contains(i);

                            //Si un user a été coché et qu'il ne l'était pas auparavant
                            if (contains && utl == null) {
                                userTodoListDAO.ajouter(new UserTodoList(0, userId, listId, 0));
                            }
                            //Si un user a été décoché et qu'il était coché auparavant
                            else if (utl != null && !contains) {
                                userTodoListDAO.deleteUserFromList(userId, listId);
                            }
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

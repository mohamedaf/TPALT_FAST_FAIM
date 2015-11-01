package com.todos.hkboam.todos.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.todos.hkboam.todos.R;
import com.todos.hkboam.todos.bdd.modal.Memo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Hassan K on 30/10/2015.
 */
public class MemoAdapter extends ArrayAdapter<Memo> {
    public MemoAdapter(Context context, List<Memo> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Get the data item for this position
        Memo memo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.memo_item, parent, false);
        }
        convertView.setId((int) memo.getId());

        Date date = new Date(memo.getModification_date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String sDate = "";
        sDate += cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);

        Log.i("Dates", sDate);

        TextView memo_title = (TextView) convertView.findViewById(R.id.memo_title);
        TextView memo_content = (TextView) convertView.findViewById(R.id.memo_content);
        TextView memo_date = (TextView) convertView.findViewById(R.id.memo_date);

        memo_title.setText(memo.getTitle());
        memo_content.setText(memo.getContent());
        memo_date.setText(sDate);

        return convertView;
    }

}

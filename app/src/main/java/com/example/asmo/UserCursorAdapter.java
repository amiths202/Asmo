package com.example.asmo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class UserCursorAdapter extends CursorAdapter {
    public UserCursorAdapter(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_template,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dateTime = view.findViewById(R.id.dateTimeTemplate);
        TextView comment = view.findViewById(R.id.commentTemplate);
        TextView flow = view.findViewById(R.id.peakFlowTemplate);

        String udate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String utime = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        String uflow = cursor.getString(cursor.getColumnIndexOrThrow("peakFlow"));
        String ucomment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
        String datetime = udate + " " + utime;
        dateTime.setText(datetime);
        comment.setText(ucomment);
        flow.setText(uflow);
    }
}

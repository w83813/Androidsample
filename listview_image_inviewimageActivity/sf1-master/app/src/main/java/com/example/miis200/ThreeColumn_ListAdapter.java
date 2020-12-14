package com.example.miis200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mitch on 2016-05-06.
 */
public class ThreeColumn_ListAdapter extends ArrayAdapter<User> {

    private LayoutInflater mInflater;
    private ArrayList<User> users;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        User user = users.get(position);

        if (user != null) {
            TextView patientName = (TextView) convertView.findViewById(R.id.textFirstName);
            TextView patientID = (TextView) convertView.findViewById(R.id.textLastName);
            TextView patientGender = (TextView) convertView.findViewById(R.id.textPatientGender);

            if (patientName != null) {
                patientName.setText(user.getPatientName());
            }
            if (patientID != null) {
                patientID.setText((user.getPatientID()));
            }
            if (patientGender != null) {
                patientGender.setText((user.getPatientGender()));
            }

            if(patientName.length()>16 || patientID.length()>16){
                patientName.setHeight(140);
                patientID.setHeight(140);
                patientGender.setHeight(140);
            }else if(patientName.length()>32 || patientID.length()>32){
                patientName.setHeight(210);
                patientID.setHeight(210);
                patientGender.setHeight(210);
            }else {
                patientName.setHeight(100);
                patientID.setHeight(100);
                patientGender.setHeight(100);
            }

        }

        return convertView;
    }
}
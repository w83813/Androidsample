package com.example.miis200;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mitch on 2016-05-06.
 */
public class Viewimage_ListAdapter extends ArrayAdapter<ViewimageItemrecycler> {

    private LayoutInflater mInflater;
    private ArrayList<ViewimageItemrecycler> users;
    private int mViewResourceId;

    public Viewimage_ListAdapter(Context context, int textViewResourceId, ArrayList<ViewimageItemrecycler> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        ViewimageItemrecycler user = users.get(position);

        if (user != null) {
            TextView imagepath = (TextView) convertView.findViewById(R.id.viewimage_imagepath);
            TextView moemo = (TextView) convertView.findViewById(R.id.viewimage_memo);
            ImageView image = (ImageView) convertView.findViewById(R.id.viewimage_image);

            if (imagepath != null) {
                imagepath.setText(user.getImagepath());
            }
            if (moemo != null) {
                moemo.setText((user.getMemo()));
            }
            if (image != null) {
                image.setImageBitmap((user.getImage()));
            }

        }

        return convertView;
    }
}
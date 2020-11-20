package com.example.login_register.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.login_register.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class arrayAdapter extends ArrayAdapter<Users> {
    ArrayList<Users> coll;

    public arrayAdapter(@NonNull Context context, int resourceid, ArrayList<Users> items) {
        super(context, resourceid,items);
        coll = items;

    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        Users card_item = coll.get(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView name = (TextView)convertView.findViewById(R.id.helloText);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_card_view);

        name.setText(card_item.getfName());
        image.setImageResource(R.mipmap.ic_launcher);

        if(card_item.getImageURL().equals("default")){
            image.setImageResource(R.mipmap.ic_launcher);
        }
        else{

            Picasso.get().load(card_item.getImageURL()).into(image);
        }









        return  convertView;

    }
}

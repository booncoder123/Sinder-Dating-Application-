package com.example.login_register.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter  extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<ItemModel> items;
    private  ArrayList<Users>users;

    public CardStackAdapter( ArrayList<Users> users) {

        this.items = items;
        this.users = users;
    }

    @NonNull
    @Override
    public CardStackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardStackAdapter.ViewHolder holder, int position) {
        holder.setData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.pic_person);
//            name = itemView.findViewById(R.id.name_person);
//            usia = itemView.findViewById(R.id.item_age);
//            kota = itemView.findViewById(R.id.item_city);
        }

        void setData(Users data) {
            Picasso.get()
                    .load(data.getImageURL())
                    .fit()
                    .centerCrop()
                    .into(image);

            name.setText(data.getfName());
//            usia.setText(data.getPhone());
//            kota.setText(data.getEmail());


        }
    }


    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}

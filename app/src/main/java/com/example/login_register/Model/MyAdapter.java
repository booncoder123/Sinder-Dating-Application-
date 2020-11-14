package com.example.login_register.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> data1;
    ArrayList<Integer> images;
    Context conText;
    public MyAdapter(Context ct,ArrayList<String> s1,ArrayList<Integer> img){
        conText = ct;
        images = img;
        data1 = s1;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(conText);
        View view = inflater.inflate(R.layout.user_display_layout,parent,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        holder.myIm.setImageResource(images.get(position));
        holder.butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Click!");
            }
        }
        );
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1,myText2;
        ImageView myIm;
        Button butt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.textviewid);
            myText2 = itemView.findViewById(R.id.textviewname);
            myIm = itemView.findViewById(R.id.imageView10);
            butt = itemView.findViewById(R.id.add_button);
        }
    }
}

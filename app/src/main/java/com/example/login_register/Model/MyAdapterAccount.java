package com.example.login_register.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.MessasgeActivity;
import com.example.login_register.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapterAccount extends RecyclerView.Adapter<MyAdapterAccount.MyViewHolder> {
    ArrayList<String> data1;
    ArrayList<String> data2;
    ArrayList<Integer> images;
    ArrayList<String> stranger_id;
    Context conText;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;
    public MyAdapterAccount(Context ct,ArrayList<String> s1,ArrayList<String> s2){
        conText = ct;
        //images = img;
        data1 = s1;
        data2 = s2;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(conText);
        View view = inflater.inflate(R.layout.account_bar,parent,false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        //holder.myIm.setImageResource(images.get(position));
//        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    System.out.println(holder.itemView.getId());
                        Intent intent = new Intent(conText, MessasgeActivity.class);
                        intent.putExtra("userid",data2.get(position));
                        conText.startActivity(intent);
                    }

                }


        );



    }




    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1;

//        ImageView myIm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.Name_account);
            //myIm = itemView.findViewById(R.id.URL_account);


        }
    }
}
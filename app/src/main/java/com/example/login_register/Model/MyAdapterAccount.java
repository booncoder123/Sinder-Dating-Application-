package com.example.login_register.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.login_register.MessasgeActivity;
import com.example.login_register.R;
import com.example.login_register.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterAccount extends RecyclerView.Adapter<MyAdapterAccount.MyViewHolder> {
    ArrayList<Users> data1;
    Context conText;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;
    public MyAdapterAccount(Context ct,ArrayList<Users> s1){
        conText = ct;;
        data1 = s1;



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

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(conText, MessasgeActivity.class);
                        intent.putExtra("userid",data1.get(position).getUserid());
                        conText.startActivity(intent);
                    }

                }


        );
        Users user = data1.get(position);
        holder.myText1.setText(user.getfName());
        if(user.getImageURL().equals("default")){
            holder.myIm.setImageResource(R.mipmap.ic_launcher);
        }
       else{
//            Glide.with(conText).load(user.getImageURL()).into(holder.myIm);

            Picasso.get().load(user.getImageURL()).into(holder.myIm);
        }



    }




    @Override
    public int getItemCount() {
        return data1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText1;
        CircleImageView myIm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.Name_account);
            myIm = itemView.findViewById(R.id.URL_account);


        }
    }



}
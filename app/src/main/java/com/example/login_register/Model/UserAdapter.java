package com.example.login_register.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.MessasgeActivity;
import com.example.login_register.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Users> mUsers;

    public UserAdapter(Context mContext, ArrayList<Users> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public CircleImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Name_account);
            profile_img = itemView.findViewById(R.id.URL_account);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.account_bar,parent,false);
        return new UserAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = mUsers.get(position);
        holder.username.setText(user.getfName());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, MessasgeActivity.class);
                intent.putExtra("userid",mUsers.get(position).getUserid());
                mContext.startActivity(intent);
            }

        });


        if(user.getImageURL().equals("default")){
            holder.profile_img.setImageResource(R.mipmap.ic_launcher);
        }
        else{
//            Glide.with(conText).load(user.getImageURL()).into(holder.myIm);

            Picasso.get().load(user.getImageURL()).into(holder.profile_img);
        }



    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}

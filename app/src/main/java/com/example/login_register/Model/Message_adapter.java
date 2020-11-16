package com.example.login_register.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class Message_adapter extends RecyclerView.Adapter<Message_adapter.MyViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static  final  int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private ArrayList<Chat> mChat;
    private String imageurl;
    FirebaseUser fuser;

    //can add image here
    public Message_adapter(Context mContext, ArrayList<Chat> mChat){
        this.mChat = mChat;
        this.mContext = mContext;






    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        if (viewType == MSG_TYPE_RIGHT) {
            View view = inflater.inflate(R.layout.chat_item_right, parent, false);
            return new MyViewHolder(view);
        }
        else {
            View view = inflater.inflate(R.layout.chat_item_left, parent, false);
            return new MyViewHolder(view);

        }


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
//        if(imageurl.equals("default")){
//            holder.profile_image.setImageResource(R.drawable.ic_account);
//        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView show_message;
        ImageView profile_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image3);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;

        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}
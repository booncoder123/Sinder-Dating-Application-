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
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Users> mUsers;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userid;

    public MyAdapter(Context mContext, ArrayList<Users> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ID,Name;
        Button button;
        public CircleImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.textviewname);
            button = itemView.findViewById(R.id.button);
            profile_img = itemView.findViewById(R.id.photo_card);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_display_layout,parent,false);
        return new MyAdapter.ViewHolder(view);


    }


    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Users user = mUsers.get(position);
//        holder.ID.setText(user.getUserid());
        holder.Name.setText(user.getfName());
        fAuth = FirebaseAuth.getInstance();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteFromStrangers(user,position);
               MyAdapter.this.notifyDataSetChanged();
                notifyDataSetChanged();

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

    private void addNodeatFriend(Users user, int position) {
        String id = user.getUserid();
        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode").child(fAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("friends").exists()){
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("friends","default");
                    myRef.updateChildren(userUpdates);
                }
                if(!snapshot.child("friends").child(id).exists()){
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put(id,id);
                    myRef.child("friends").updateChildren(userUpdates);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void deleteFromStrangers(Users user,Integer position) {

        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode").child(fAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Strangers").exists()){
                    myRef.child("Strangers").child(user.getUserid()).removeValue();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addNodeatFriend(user,position);


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}

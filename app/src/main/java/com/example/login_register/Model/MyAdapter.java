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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<String> data1;
    ArrayList<Integer> images;
    ArrayList<String> stranger_id;
    Context conText;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;
    public MyAdapter(Context ct,ArrayList<String> s1,ArrayList<Integer> img,ArrayList<String> s2){
        conText = ct;
        images = img;
        data1 = s1;
        stranger_id = s2;


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
                add_data_to_account(stranger_id,data1,position);
            }
        }
        );
    }

    private void add_data_to_account(ArrayList<String> ids, ArrayList<String> names,int position) {
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        System.out.println(ids);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.child("peopleNode").child(userID).hasChild("friends"))
                {
                    myRef = myRef.child("peopleNode").child(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("friends","default");
                    myRef.updateChildren(user);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        
                    //add node to account node
                    myRef = myRef.child("peopleNode").child(userID).child("friends");
                    Map<String,Object> user = new HashMap<>();
                    user.put(ids.get(position),ids.get(position));
                    myRef.updateChildren(user);
                    //delete node
                    FirebaseDatabase.getInstance().getReference().child("peopleNode").child(userID).child("Strangers").child(ids.get(position)).removeValue();
                    sendUserTochat();



    }

    private void sendUserTochat() {
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

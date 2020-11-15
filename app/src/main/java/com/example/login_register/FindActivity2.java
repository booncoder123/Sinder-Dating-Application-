package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_register.Model.MyAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FindActivity2 extends AppCompatActivity {


    private RecyclerView FindFriendRecycleList;

    FirebaseAuth fAuth;
    DatabaseReference myRef, myRef2;
    String userID;


    String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
//        myRef  = FirebaseDatabase.getInstance().getReference().child("peopleNode").child(userID).child("Strangers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Find Friends");



    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> Stranger_List = new ArrayList<String>();
                for (DataSnapshot i : snapshot.child(userID).child("Strangers").getChildren()) {
//                    System.out.println(i.getValue().toString());
                    Stranger_List.add(i.getValue().toString());
                    sendToMem(Stranger_List);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void sendToMem(ArrayList<String> stranger_list) {
        ArrayList<String> Stranger_name = new ArrayList<String>();
        ArrayList<String> Stranger_id = stranger_list;
        for (String id : stranger_list) {
            myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Stranger_name.add(snapshot.child(id).child("fName").getValue().toString());
                    sendToShowOutput(Stranger_name,Stranger_id);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
    }

    private void sendToShowOutput(ArrayList<String> stranger_name,ArrayList<String> stranger_id) {
        System.out.println(stranger_name);
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.ic_account);
        images.add(R.drawable.ic_confirm);
        images.add(R.drawable.ic_email);

        MyAdapter myAdapter = new MyAdapter(FindActivity2.this,stranger_name,images,stranger_id);
        FindFriendRecycleList = findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setAdapter(myAdapter);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(FindActivity2.this));
    }
}




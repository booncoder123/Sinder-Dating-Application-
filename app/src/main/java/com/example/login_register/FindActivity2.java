package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.login_register.Model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    ArrayList<String> id_collertor;
    ArrayList<Users> users_collector;


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

        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode").child(userID).child("Strangers");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id_collertor = new ArrayList<String>();
                for( DataSnapshot snap : snapshot.getChildren()){
                    id_collertor.add(snap.getValue().toString());
                }
                SearchIDUsers(id_collertor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void SearchIDUsers(ArrayList<String> id_collertor) {
        users_collector = new ArrayList<Users>();
//        System.out.println(id_collertor);
        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(String item : id_collertor){
                   for (DataSnapshot snap : snapshot.getChildren()){
                       Users person = snap.getValue(Users.class);
                      if(item.equals(String.valueOf(person.getUserid()))){
                           users_collector.add(person);
                      }

                   }
               }
//                System.out.println(users_collector);
                show_output(users_collector);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void show_output(ArrayList<Users> users_collector) {
        MyAdapter myAdapter = new MyAdapter(FindActivity2.this,users_collector);
        FindFriendRecycleList = findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setAdapter(myAdapter);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(FindActivity2.this));
    }


    @Override
    protected void onStart() {
        super.onStart();


    }




}




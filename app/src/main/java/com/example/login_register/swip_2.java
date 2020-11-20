package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.login_register.Model.Users;
import com.example.login_register.Model.arrayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;


public class swip_2 extends AppCompatActivity {

    private ArrayList<Users> al;
    private com.example.login_register.Model.arrayAdapter arrayAdapter;
    private arrayAdapter arrayAdapter2;
    private int i;
    private String user_sex;
    private String opposite_user_sex;
    private DatabaseReference usersdb;


    //server side

//    String this_user_id = mAuth.getCurrentUser().getUid();
    String this_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_2);
        checkUserSex();
        al = new ArrayList<Users>();
        usersdb = FirebaseDatabase.getInstance().getReference().child("peopleNode");



        arrayAdapter = new arrayAdapter(this,R.layout.item, al );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
//                Do something on the left!
//                You also have access to the original object.
//                If you want to use it just cast it (String) dataObject
                Users obj = (Users) dataObject;
                String userid = obj.getUserid();
                usersdb.child(userid).child("connections").child("nope").child(this_user_id).setValue(true);


                Toast.makeText(swip_2.this,"Left",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                Users obj = (Users) dataObject;
                System.out.println(obj.getfName());
                String userid = obj.getUserid();
                usersdb.child(userid).child("connections").child("yeps").child(this_user_id).setValue(true);
                isConnectionMatch(userid);
                Toast.makeText(swip_2.this,"Right",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //Users test = new Users("None","None", "None", "None", "None", "None");
                //al.add(test);
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
                //i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                Toast.makeText(swip_2.this,"Click",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void isConnectionMatch(String userid) {
        DatabaseReference currentUserConnectionDb = usersdb.child(this_user_id).child("connections").child("yeps").child(userid);
        currentUserConnectionDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    DatabaseReference currentUserConnectionDb = usersdb.child(this_user_id).child("Strangers");
                    currentUserConnectionDb.push().setValue(userid);
                    currentUserConnectionDb = usersdb.child(userid).child("Strangers");
                    currentUserConnectionDb.push().setValue(this_user_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUserSex() {
        ArrayList<String> id_collector = new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        DatabaseReference maleDb = FirebaseDatabase.getInstance().getReference().child("sexNode").child("male");
        maleDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    id_collector.add(snap.getValue().toString());
                }
                if(id_collector.contains(id)){
                    System.out.println("Male!");
                    user_sex = "male";
                    opposite_user_sex = "female";
                    getOppositeSexUser();

                }
                else {
                    System.out.println("Female!");
                    user_sex = "female";
                    opposite_user_sex = "male";
                    getOppositeSexUser();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public  void getOppositeSexUser(){
        ArrayList<String> opposite_id_collectors = new ArrayList<>();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        DatabaseReference oppositeSexDb = FirebaseDatabase.getInstance().getReference().child("sexNode").child(opposite_user_sex);
        oppositeSexDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren())
                {
                    opposite_id_collectors.add(snap.getValue().toString());
                }
                DatabaseReference oppositeSexDb = FirebaseDatabase.getInstance().getReference().child("peopleNode");
                oppositeSexDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap2 : snapshot.getChildren()){
                            Users user = snap2.getValue(Users.class);
                            if(opposite_id_collectors.contains(user.getUserid()) &&  !snap2.child("connections").child("nope").hasChild(this_user_id) && !snap2.child("connections").child("yeps").hasChild(this_user_id))
                            {
                                al.add(user);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }


}
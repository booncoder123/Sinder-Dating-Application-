package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindActivity2 extends AppCompatActivity {


    private RecyclerView FindFriendRecycleList;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);
        UserRef = FirebaseDatabase.getInstance().getReference().child("MyUsers");



        FindFriendRecycleList = (RecyclerView) findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setHasFixedSize(true);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(this));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        super.onStart();



        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(UserRef,Contacts.class).build();
        FirebaseRecyclerAdapter<Contacts,FindFriendViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, int position, @NonNull Contacts model) {
                        getRef(position).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(!snapshot.child("checked").exists()){
                                    holder.userName.setText(""+model.getName());
                                    holder.userStatus.setText(model.getStatus());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
//                        Toast.makeText(FindActivity2.this,x,Toast.LENGTH_SHORT).show();


                        holder.add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                String eiei = getRef(position).child("sex").getKey();
//                                Toast.makeText(FindActivity2.this,eiei,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                        return  new FindFriendViewHolder(v);
                    }
                };
        adapter.startListening();
        FindFriendRecycleList.setAdapter(adapter);
    }

//    @Override
//    protected void onStart() {
//
//
//
//    }
    public  static class FindFriendViewHolder extends RecyclerView.ViewHolder{
        TextView userName,userStatus;
        Button add_button;
//        CircleImageView profileImage;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textviewid);
            userStatus = itemView.findViewById(R.id.textviewname);
            add_button = itemView.findViewById(R.id.add_button);
//

        }
    }


}
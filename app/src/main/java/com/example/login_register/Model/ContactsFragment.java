package com.example.login_register.Model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login_register.FindActivity2;
import com.example.login_register.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Integer> pics = new ArrayList<Integer>();
    ArrayList<String> Names = new ArrayList<String>();
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;


    private  View ContactsView;
    private RecyclerView myContactList;


    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> Stranger_List = new ArrayList<String>();
                for (DataSnapshot i : snapshot.child(userID).child("friends").getChildren()) {
                   System.out.println(i.getValue().toString());
                    Stranger_List.add(i.getValue().toString());
                    System.out.println(i.getValue().toString());
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

        MyAdapterAccount myAdapter = new MyAdapterAccount(getContext(),stranger_name,stranger_id);
        myContactList= (RecyclerView) ContactsView.findViewById(R.id.Recycle_contacts);
        myContactList.setAdapter(myAdapter);
        myContactList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    //@Override
//    public void onStart() {
//        super.onStart();
//
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ContactsView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return ContactsView;
    }



}
package com.example.tutormanagerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.StudentUserAdapter;


public class StudentUserFragment extends Fragment {

    private ListView listView;
    private RecyclerView recyclerView;
    private StudentUserAdapter studentUserAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    private List<StudentUser> users;
    //private ArrayList<StudentUser> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_user, container, false);

        recyclerView = view.findViewById(R.id.studentUserRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //listView = view.findViewById(R.id.listview);
        //database = FirebaseDatabase.getInstance();
        //reference = database.getReference("Students");

       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        users = new ArrayList<>();

        readStudents();

        return view;
    }
    private void readStudents()
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                //Log.i( "onDataChange: ",dataSnapshot.getChildren().toString());
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    StudentUser studentUser = snapshot.getValue(StudentUser.class);

                    assert studentUser != null;
                    assert firebaseUser != null;
                    if(!studentUser.getId().equals(firebaseUser.getUid()))
                   {
                        Log.i("onDataChange: ",studentUser.getName());
                        users.add(studentUser);
                    }
                        //users.add(studentUser);

                }
                studentUserAdapter = new StudentUserAdapter(getContext(),users,false);
                recyclerView.setAdapter(studentUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

package com.example.tutormanagerapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.MessageAdapter;
import Adapter.StudentUserAdapter;
import Adapter.TutorUserAdapter;


public class TutorListChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TutorUserAdapter userAdapter;

    private List<TutorUser> musers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_list_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();


        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Chat chat = snapshot.getValue(Chat.class);
                    assert chat != null;
                    if (chat.getSender().equals(fuser.getUid()))
                    {

                        usersList.add(chat.getReceiver());
                        //Log.d("onDataChange: ",usersList.toString());
                    }
                    if (chat.getReceiver().equals(fuser.getUid()))
                    {
                        usersList.add(chat.getSender());
                    }
                }
                readChats();
                userAdapter = new TutorUserAdapter(getContext(),musers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    private void readChats()
    {
        musers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Tutors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                musers.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    TutorUser user = snapshot.getValue(TutorUser.class);

                    //display 1 user from the chats
                    for (String id: usersList)
                    {
                        assert user != null;
                        if (user.getId().equals(id))
                        {
                            if (musers.size() != 0)
                            {
                                for (TutorUser user1 :musers)
                                {
                                    if (!user.getId().equals(user1.getId()))
                                    {
                                        musers.add(user);
                                        Log.d(musers.toString(), "onDataChange: ");
                                    }
                                }
                            }else {
                                musers.add(user);
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

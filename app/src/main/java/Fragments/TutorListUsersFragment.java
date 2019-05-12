/* Written by: Pooja Seth */


package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutormanagerapp.R;
import Helper.TutorUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.TutorUserAdapter;


public class TutorListUsersFragment extends Fragment {

    //private ListView listView;
    private RecyclerView recyclerView;
    private TutorUserAdapter tutorUserAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    private List<TutorUser> users;

    //EditText search_users;
    //private ArrayList<StudentUser> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_list_users, container, false);

        recyclerView = view.findViewById(R.id.tutorUserRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //search_users = view.findViewById(R.id.search_users);

      /* search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
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

    private void searchUsers(String s)
    {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Students").orderByChild("name")
                .startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    TutorUser user = snapshot.getValue(TutorUser.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getId().equals(fuser.getUid()))
                    {
                        users.add(user);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TutorUser tutorUser = snapshot.getValue(TutorUser.class);

                    assert tutorUser != null;
                    assert firebaseUser != null;
                   /*if (!tutorUser.getId().equals(firebaseUser.getUid())) {
                        Log.i("onDataChange: ", tutorUser.getName());
                        users.add(tutorUser);
                    }*/
                    users.add(tutorUser);

                }
                tutorUserAdapter = new TutorUserAdapter(getContext(), users);
                recyclerView.setAdapter(tutorUserAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

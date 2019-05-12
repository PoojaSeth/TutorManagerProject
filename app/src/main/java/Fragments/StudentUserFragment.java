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
import android.widget.ListView;

import com.example.tutormanagerapp.R;

import Adapter.TutorUserAdapter;
import Helper.StudentUser;
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

import Adapter.StudentUserAdapter;
import Helper.TutorUser;


public class StudentUserFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentUserAdapter studentUserAdapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    private List<StudentUser> users;

    //EditText search_users;
    //private ArrayList<StudentUser> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_user, container, false);

        recyclerView = view.findViewById(R.id.studentUserRV);
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

        readTutors();

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
                    StudentUser user = snapshot.getValue(StudentUser.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getId().equals(fuser.getUid()))
                    {
                        users.add(user);
                    }
                }
                studentUserAdapter = new StudentUserAdapter(getContext(),users);
                recyclerView.setAdapter(studentUserAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void readTutors()
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tutors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    users.clear();
                    //Log.i( "onDataChange: ",dataSnapshot.getChildren().toString());
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        StudentUser studentUser = snapshot.getValue(StudentUser.class);

                        //assert studentUser != null;
                        //assert firebaseUser != null;
                        /*if (!studentUser.getId().equals(firebaseUser.getUid())) {
                            Log.i("onDataChange: ", studentUser.getName());
                            users.add(studentUser);
                        }*/
                        users.add(studentUser);

                    }
                    studentUserAdapter = new StudentUserAdapter(getContext(), users);
                    recyclerView.setAdapter(studentUserAdapter);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

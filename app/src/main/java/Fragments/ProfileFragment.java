package Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tutormanagerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Fragments.UpdateStudentProfileFragment;

public class ProfileFragment extends Fragment {

    private ImageView profilePic;
    private TextView nameTV, emailTV, passwordTV;
    private Button updateProfilebtn;
    private DatabaseReference dbRef;
    private FirebaseAuth mauth;
    private String currentUserID;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mauth = FirebaseAuth.getInstance();
        currentUserID = mauth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUserID);

        profilePic = view.findViewById(R.id.profilepic);
        nameTV = view.findViewById(R.id.emailTV2);
        emailTV = view.findViewById(R.id.emailTV);
        passwordTV = view.findViewById(R.id.passwordTV);

        updateProfilebtn = view.findViewById(R.id.updateProfilebtn);

        updateProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new UpdateStudentProfileFragment());
                ft.commit();
            }
        });
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String email = dataSnapshot.child("email").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    //**Get profile image later on**

                    nameTV.setText(name);
                    emailTV.setText(email);
                    passwordTV.setText(password);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    }

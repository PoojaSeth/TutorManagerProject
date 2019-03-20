package com.example.tutormanagerapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class ProfileFragment extends Fragment {

    private ImageView profilePic;
    private TextView nameTV, emailTV;
    private Button editProfilebtn;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        nameTV = (TextView)view.findViewById(R.id.nameTV);
        emailTV = (TextView)view.findViewById(R.id.emailTV);

        editProfilebtn = (Button)view.findViewById(R.id.editProfilebtn);
        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //Student student = new Student();
        //final String uid = currentUser.getUid();
       // Log.d(TAG, "Id: "+uid);

        //loadUserInformation();
        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new EditStudentProfile());
                fr.commit();




                //Log.d(TAG, "Data: "+databaseReference);

                //startActivity(new Intent(ProfileFragment.this, StudentHomeActivity.class));
            }
        });

        return view;

    }

    FirebaseAuth mauth;

    public void loadUserInformation()
    {
        FirebaseUser user = mauth.getCurrentUser();

        if (user != null) {
            if (user.getEmail()!=null)
            {
                emailTV.setText(user.getEmail());
            }
            if (user != null)
            {
                nameTV.setText(user.getDisplayName());
            }
        }

        //String name = user.getDisplayName().toString();

    }


}

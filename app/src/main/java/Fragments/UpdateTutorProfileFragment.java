package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tutormanagerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class UpdateTutorProfileFragment extends Fragment {

    private ImageView profilePic;
    private EditText nameET, emailET, passwordET;
    private Button updateProfileBtn;
    private Button cancelUpdateBtn;
    private DatabaseReference dbRef;
    private StorageReference storeRef;
    private FirebaseAuth mauth;
    private String currentUserID;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_tutor_profile, container, false);


        mauth = FirebaseAuth.getInstance();
        currentUserID = mauth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Tutors").child(currentUserID);
        storeRef = FirebaseStorage.getInstance().getReference();
        profilePic = view.findViewById(R.id.profilepic);
        nameET = view.findViewById(R.id.editNameET);
        emailET = view.findViewById(R.id.editEmailET);
        passwordET = view.findViewById(R.id.editPasswordET);
        updateProfileBtn = view.findViewById(R.id.updateProfileBtn);
        cancelUpdateBtn = view.findViewById(R.id.cancelUpdateBtn);

        cancelUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorProfileFragment());
                ft.commit();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String name = nameET.getText().toString();
                String pass = passwordET.getText().toString();

                dbRef.child("name").setValue(name);
                dbRef.child("email").setValue(email);
                dbRef.child("password").setValue(pass);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorProfileFragment());
                ft.commit();
            }
        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String email = dataSnapshot.child("email").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String pass = dataSnapshot.child("password").getValue().toString();
                    //String imageStr = dataSnapshot.child("ImageURL").getValue().toString();

                    //**Get Profile Image**
                    //Uri imageURL = storeRef.child("tutorPhotos/" + imageStr).getDownloadUrl().getResult();
                    //Glide.with(getActivity()).load(imageURL).into(profilePic);

                    nameET.setText(name);
                    emailET.setText(email);
                    passwordET.setText(pass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }


}

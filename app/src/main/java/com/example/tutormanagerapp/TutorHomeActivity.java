package com.example.tutormanagerapp;

import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Import UI elements
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

// Import Firebase
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import de.hdodenhof.circleimageview.CircleImageView;


public class TutorHomeActivity extends AppCompatActivity {

    // Firebase
    private FirebaseUser user;
    private DatabaseReference reference;

    // Navigation
    private Fragment selectedFragment;

    // Profile
    CircleImageView profilepic;
    TextView nameTV;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);

        nameTV = (TextView) findViewById(R.id.nameTV);

        profilepic = (CircleImageView) findViewById(R.id.profilepic);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid());

        //reference.addValueEventListener(new ValueEventListener());

        name = user.getDisplayName(); // getting the name from database

        nameTV.setText(name); // displaying it in the textview


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, new TutorHomeFragment()).commit();

    }

        //@Override
        public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
        Student student = dataSnapshot.getValue(Student.class);
        assert student != null;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //navListener = OnMenuItemClickListener();
        //bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new TutorHomeFragment()).commit();

    }


    //@Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                //case R.id.nav_home:
                //selectedFragment = new TutorHomeFragment();
                //break;
                case R.id.nav_profile:
                    selectedFragment = new TutorProfileFragment();
                    break;
                case R.id.nav_chat:
                    selectedFragment = new TutorChatFragment();
                    break;
                default: // Tutor's home
                    selectedFragment = new TutorHomeFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , selectedFragment).commit();

            return true;
        }
    };
}

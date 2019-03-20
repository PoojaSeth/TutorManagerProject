package com.example.tutormanagerapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class TutorHomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    CircleImageView profilepic;
    TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        nameTV = (TextView)findViewById(R.id.nameTV);
        profilepic = (CircleImageView)findViewById(R.id.profilepic);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tutors").child(user.getUid());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, new TutorHomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new TutorHomeFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new TutorProfileFragment();
                    break;
                case R.id.nav_chat:
                    selectedFragment = new TutorChatFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    ,selectedFragment).commit();

            return true;
        }
    };
}

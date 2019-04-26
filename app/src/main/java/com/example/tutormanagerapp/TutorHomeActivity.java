package com.example.tutormanagerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        nameTV = (TextView)findViewById(R.id.username);
        profilepic = (CircleImageView)findViewById(R.id.profilepic);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tutors").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tutor tutor = dataSnapshot.getValue(Tutor.class);
                assert tutor != null;

                Log.i("onDataChange: ",tutor.getName());
                String name = tutor.getName();
                Log.i( "onDataChange: ",name);
                Log.i("onDataChange: ",tutor.getImageURL());
                nameTV.setText(name);
                if (tutor.getImageURL().equals("default"))
                {
                    profilepic.setImageResource(R.mipmap.ic_launcher);
                }
                else {
                    Glide.with(TutorHomeActivity.this).load(tutor.getImageURL()).into(profilepic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

                case  R.id.nav_courses:
                    selectedFragment = new TutorAddCourse();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TutorHomeActivity.this,TutorLoginActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }
        return false;

    }
}


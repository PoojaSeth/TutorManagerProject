package com.example.tutormanagerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TutorRegisterActivity extends AppCompatActivity {

    private EditText emailET, nameET, passwordET;
    private Button registerbtn;
    private CircleImageView profilepic;

    FirebaseAuth auth;
    DatabaseReference reference;

    String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_register);

        emailET = (EditText)findViewById(R.id.emailET);
        nameET = (EditText)findViewById(R.id.nameET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        registerbtn = (Button)findViewById(R.id.registerbtn);
        profilepic = (CircleImageView) findViewById(R.id.profilepic);

        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Tutors");

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()){
                    final String email = emailET.getText().toString().trim();
                    final String name = nameET.getText().toString().trim();
                    final String password = passwordET.getText().toString().trim();

                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser user = auth.getCurrentUser();
                                        assert user != null;
                                        String userId = user.getUid();


                                        reference = FirebaseDatabase.getInstance().
                                                getReference("Tutors").child(userId);

                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("id",userId);
                                        hashMap.put("name",name);
                                        hashMap.put("email",email);
                                        hashMap.put("password",password);
                                        hashMap.put("imageURL","default");

                                        // sending data to tutor table
                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(TutorRegisterActivity.this,
                                                            "Successfully registered,data saved!!",
                                                            Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(TutorRegisterActivity.this,TutorHomeActivity.class));
                                                }
                                            }
                                        });

                                        //sendEmailVerification();
                                        //sendUserData();
                                    }
                                    else {
                                        Toast.makeText(TutorRegisterActivity.this,
                                                "Registration failed!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });


    }

    private Boolean validate()
    {
        Boolean result = false;

        name = nameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();


        if (name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(TutorRegisterActivity.this,
                    "Enter all the fields",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;

    }

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        sendUserData();
                        Toast.makeText(TutorRegisterActivity.this,
                                "Successfully registered and verification email has been sent!!!",
                                Toast.LENGTH_SHORT).show();
                        auth.signOut();
                        finish();
                        startActivity(new Intent(TutorRegisterActivity.this,TutorLoginActivity.class));
                    }
                    else {
                        Toast.makeText(TutorRegisterActivity.this,
                                "Verification Email couldnt be sent!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData()
    {
        String email = emailET.getText().toString().trim();
        String name = nameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        String id = reference.push().getKey();

        /*Tutor tutor = new Tutor(id,email,name,password);
        assert id != null;
        reference.child(id).setValue(tutor);
        emailET.setText("");
        nameET.setText("");
        passwordET.setText("");*/

    }
}

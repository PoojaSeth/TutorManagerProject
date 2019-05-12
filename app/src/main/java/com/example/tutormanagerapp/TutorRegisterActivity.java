/* Written by: Pooja Seth */

package com.example.tutormanagerapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TutorRegisterActivity extends AppCompatActivity {


    private EditText emailET, nameET, passwordET;
    private Button registerbtn;
    private ImageView profilepic;

    FirebaseAuth tutorAuth;
    DatabaseReference tutorRef;

    String email,name,password;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_register);

        emailET = (EditText)findViewById(R.id.emailET);
        nameET = (EditText)findViewById(R.id.nameET);
        passwordET = (EditText)findViewById(R.id.passwordET);


        registerbtn = (Button)findViewById(R.id.registerbtn);
        profilepic = (ImageView)findViewById(R.id.profilepic);
        tutorAuth = FirebaseAuth.getInstance();

        user = tutorAuth.getInstance().getCurrentUser();

        tutorRef = FirebaseDatabase.getInstance().getReference("Tutors");

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validate())
                {
                    final String email = emailET.getText().toString().trim();
                    final String name = nameET.getText().toString().trim();
                    final String password = passwordET.getText().toString().trim();

                    tutorAuth.createUserWithEmailAndPassword(email,password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser tutoruser = tutorAuth.getCurrentUser();
                                        assert tutoruser != null;
                                        String userId = tutoruser.getUid();

                                        tutorRef = FirebaseDatabase.getInstance().
                                                getReference("Tutors").child(userId);

                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("id",userId);
                                        hashMap.put("name",name);
                                        hashMap.put("email",email);
                                        hashMap.put("password",password);
                                        hashMap.put("imageURL","default");

                                        tutorRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(TutorRegisterActivity.this,
                                                            "Successfully registered,data saved!!",
                                                            Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(TutorRegisterActivity.this,TutorLoginActivity.class));
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
        FirebaseUser firebaseUser = tutorAuth.getInstance().getCurrentUser();
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
                        tutorAuth.signOut();
                        finish();
                        //startActivity(new Intent(StudentRegisterActivity.this,StudentLoginActivity.class));
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
    private void sendUserData() {
        String email = emailET.getText().toString().trim();
        String name = nameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        String id = tutorRef.push().getKey();

        //Student student = new Student(id, email, name, password);
        assert id != null;
        //studentref.child(id).setValue(student);
    }
}

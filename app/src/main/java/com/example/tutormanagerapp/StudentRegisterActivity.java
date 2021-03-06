/* Written by: Pooja Seth */


//uncomment emailVerification;
package com.example.tutormanagerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class StudentRegisterActivity extends AppCompatActivity {

    private EditText emailET, nameET, passwordET;
    private Button registerbtn;
    private ImageView profilepic;

    FirebaseAuth studentAuth;
    DatabaseReference studentref;

    String email,name,password;

    int PICK_IMAGE_REQUEST = 1;

    Bitmap bitmap;
    Uri uri;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        emailET = (EditText)findViewById(R.id.emailET);
        nameET = (EditText)findViewById(R.id.nameET);
        passwordET = (EditText)findViewById(R.id.passwordET);


        registerbtn = (Button)findViewById(R.id.registerbtn);
        profilepic = (ImageView)findViewById(R.id.profilepic);
        studentAuth = FirebaseAuth.getInstance();

        user = studentAuth.getInstance().getCurrentUser();

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        studentref = FirebaseDatabase.getInstance().getReference("Students");

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validate())
                {
                    final String email = emailET.getText().toString().trim();
                    final String name = nameET.getText().toString().trim();
                    final String password = passwordET.getText().toString().trim();

                    studentAuth.createUserWithEmailAndPassword(email,password).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser studentuser = studentAuth.getCurrentUser();
                                        assert studentuser != null;
                                        String userId = studentuser.getUid();

                                        studentref = FirebaseDatabase.getInstance().
                                                getReference("Students").child(userId);

                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("id",userId);
                                        hashMap.put("name",name);
                                        hashMap.put("email",email);
                                        hashMap.put("password",password);
                                        hashMap.put("imageURL","default");

                                        studentref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(StudentRegisterActivity.this,
                                                            "Successfully registered,data saved!!",
                                                            Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(StudentRegisterActivity.this,StudentLoginActivity.class));
                                                }
                                            }
                                        });
                                        //sendEmailVerification();
                                        //sendUserData();

                                    }
                                    else {
                                        Toast.makeText(StudentRegisterActivity.this,
                                                "Registration failed!!!",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(this, "hey you selected image" + bitmap, Toast.LENGTH_SHORT).show();
                profilepic.setImageBitmap(bitmap);
                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean validate()
    {
        Boolean result = false;

        name = nameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();


        if (name.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(StudentRegisterActivity.this,
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
        FirebaseUser firebaseUser = studentAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        sendUserData();
                        Toast.makeText(StudentRegisterActivity.this,
                                "Successfully registered and verification email has been sent!!!",
                                Toast.LENGTH_SHORT).show();
                        studentAuth.signOut();
                        finish();
                        //startActivity(new Intent(StudentRegisterActivity.this,StudentLoginActivity.class));
                    }
                    else {
                        Toast.makeText(StudentRegisterActivity.this,
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

        String id = studentref.push().getKey();

        //Student student = new Student(id, email, name, password);
        assert id != null;
        //studentref.child(id).setValue(student);
    }
}

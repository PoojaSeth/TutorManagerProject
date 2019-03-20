package com.example.tutormanagerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class TutorLoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginbtn,registerbtn,forgotpasswordbtn;
    private CircleImageView profilepic;

    private FirebaseAuth tutorAuth;

    private FirebaseUser user;

    private DatabaseReference reference;

    private ProgressDialog progressDialog;

    //@Override
   /* protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //check if user is already logged in
        if (user != null)
        {
            startActivity(new Intent(TutorLoginActivity.this,TutorHomeActivity.class));
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_login);

        emailET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);


        loginbtn = (Button)findViewById(R.id.loginbtn);
        registerbtn = (Button)findViewById(R.id.registerbtn);
        forgotpasswordbtn = (Button)findViewById(R.id.forgotPasswordbtn);
        profilepic = (CircleImageView)findViewById(R.id.profilepic);

        tutorAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        user = FirebaseAuth.getInstance().getCurrentUser();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TutorLoginActivity.this,TutorRegisterActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()){
                    Toast.makeText(TutorLoginActivity.this,
                            "Add all the details",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Validate(emailET.getText().toString(),passwordET.getText().toString());

                }
            }
        });

        forgotpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TutorLoginActivity.this,TutorForgotPasswordActivity.class));
            }
        });


    }
    private void Validate(String email, String password)
    {


        progressDialog.setMessage("Logging In....");
        progressDialog.show();

        tutorAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            //checkEmailVerification();
                            Toast.makeText(TutorLoginActivity.this,
                                    "Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TutorLoginActivity.this, TutorHomeActivity.class));

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(TutorLoginActivity.this,
                                    "Login Failed!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void checkEmailVerification(){
        FirebaseUser user = tutorAuth.getInstance().getCurrentUser();
        Boolean emailFlag = user.isEmailVerified();

        if (emailFlag)
        {
            finish();
            startActivity(new Intent(TutorLoginActivity.this, TutorHomeActivity.class));
        }
        else {
            Toast.makeText(TutorLoginActivity.this,
                    "Verify your email!!!",
                    Toast.LENGTH_SHORT).show();
            tutorAuth.signOut();
        }
    }
}

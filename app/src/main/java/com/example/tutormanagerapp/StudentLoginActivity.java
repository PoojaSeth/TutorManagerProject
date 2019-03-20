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
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginbtn,registerbtn,forgotpasswordbtn;
    private CircleImageView profilepic;

    private FirebaseAuth studentAuth;

    private FirebaseUser studentuser;

    private DatabaseReference studentref;

    private ProgressDialog progressDialog;

   /* @Override
    protected void onStart() {
        super.onStart();

        studentuser = FirebaseAuth.getInstance().getCurrentUser();
        //check if user is already logged in
       if (studentuser != null)
        {
            startActivity(new Intent(StudentLoginActivity.this,StudentHomeActivity.class));
            finish();
        }


    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        emailET = (EditText)findViewById(R.id.emailET);
        passwordET = (EditText)findViewById(R.id.passwordET);

        loginbtn = (Button)findViewById(R.id.loginbtn);
        registerbtn = (Button)findViewById(R.id.registerbtn);
        forgotpasswordbtn = (Button)findViewById(R.id.forgotPasswordbtn);
        profilepic = (CircleImageView)findViewById(R.id.profilepic);

        studentAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        studentuser = FirebaseAuth.getInstance().getCurrentUser();
        //studentref = FirebaseDatabase.getInstance().

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this,StudentRegisterActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()){
                    Toast.makeText(StudentLoginActivity.this,
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
                startActivity(new Intent(StudentLoginActivity.this,StudentForgotPasswordActivity.class));
            }
        });


    }

    private void Validate(String email, String password)
    {


        progressDialog.setMessage("Logging In....");
        progressDialog.show();

        studentAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            //checkEmailVerification();
                            startActivity(new Intent(StudentLoginActivity.this, StudentHomeActivity.class));

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(StudentLoginActivity.this,
                                    "Login Failed!!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void checkEmailVerification(){
        FirebaseUser user = studentAuth.getInstance().getCurrentUser();
        Boolean emailFlag = user.isEmailVerified();

        if (emailFlag)
        {
            finish();
            startActivity(new Intent(StudentLoginActivity.this, StudentHomeActivity.class));
        }
        else {
            Toast.makeText(StudentLoginActivity.this,
                    "Verify your email!!!",
                    Toast.LENGTH_SHORT).show();
            studentAuth.signOut();
        }
    }
}

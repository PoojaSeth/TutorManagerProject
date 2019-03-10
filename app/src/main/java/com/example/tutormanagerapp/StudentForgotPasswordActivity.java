package com.example.tutormanagerapp;

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
import com.google.firebase.auth.FirebaseAuth;

public class StudentForgotPasswordActivity extends AppCompatActivity {

    private EditText emailET;
    private Button resetpswdbtn;
    private FirebaseAuth studentAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_forgot_password);

        emailET = (EditText)findViewById(R.id.emailET);
        resetpswdbtn = (Button)findViewById(R.id.resetpswdbtn);

        studentAuth = FirebaseAuth.getInstance();

        resetpswdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailET.getText().toString().trim();

                if (email.equals(""))
                {
                    Toast.makeText(StudentForgotPasswordActivity.this,
                            "Please enter your registered email ID!!!",
                            Toast.LENGTH_SHORT).show();
                }else {
                    studentAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(StudentForgotPasswordActivity.this,
                                        "Reset password email sent",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(StudentForgotPasswordActivity.this, StudentLoginActivity.class));

                            }
                            else {
                                Toast.makeText(StudentForgotPasswordActivity.this,
                                        "Reset password email cannot be sent!!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}

package com.example.tutormanagerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;



public class TuorAddCourseForm extends Fragment {

    public DatabaseReference reference;

    public FirebaseAuth auth;

    public Spinner spinner;

    public EditText experience;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuor_add_course_form,container,false);



        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        final String userId = firebaseUser.getUid();

        experience = (EditText)view.findViewById(R.id.experience);

        Button submit = (Button)view.findViewById(R.id.submitCourse);

        spinner = (Spinner)view.findViewById(R.id.spinnerCourse);

        ArrayList<String> courses = new ArrayList<>();
        courses.add("Select a course");
        courses.add("Math");
        courses.add("Science");
        courses.add("History"); ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String exp = experience.getText().toString();

                reference = FirebaseDatabase.getInstance().getReference("Courses");

                final String id = reference.push().getKey();

                String courseName = spinner.getSelectedItem().toString();

                HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("Added by",userId);
                            hashMap.put("courseName",courseName);
                            hashMap.put("experience",exp);

                            reference.child(id).setValue(hashMap);

                Toast.makeText(getContext(),"Added values",Toast.LENGTH_SHORT).show();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorAddCourse());
                ft.commit();
            }
        });
        return view;
    }
}

/* Written by: Pooja Seth and Kameng Thao */

package Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutormanagerapp.R;
import Helper.TutorUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class TuorAddCourseForm extends Fragment {

    public DatabaseReference reference;

    public FirebaseAuth auth;

    public Spinner spinner;

    public EditText experience;

    public Button addMonTimes,addTuesTimes,addWedTimes,addThursTimes,addFriTimes;

    public TextView monAvail,tuesAvail,wedAvail,thurAvail,friAvail;

    String[] availabilities;
    boolean[] checkedTimes;

    ArrayList<Integer> muserAvail = new ArrayList<>();


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

        addMonTimes = (Button)view.findViewById(R.id.addMonTimes);
        addTuesTimes = (Button)view.findViewById(R.id.addTuesTimes);
        addWedTimes = (Button)view.findViewById(R.id.addWedTimes);
        addThursTimes = (Button)view.findViewById(R.id.addThursTimes);
        addFriTimes = (Button)view.findViewById(R.id.addFriTimes);

        monAvail = (TextView)view.findViewById(R.id.monAvail);

        tuesAvail = (TextView)view.findViewById(R.id.tuesAvail);
        wedAvail = (TextView)view.findViewById(R.id.wedAvail);
        thurAvail = (TextView)view.findViewById(R.id.thursAvail);
        friAvail = (TextView)view.findViewById(R.id.friAvail);





        availabilities = getResources().getStringArray(R.array.availabilities);
        checkedTimes = new boolean[availabilities.length];



        ArrayList<String> courses = new ArrayList<>();
        courses.add("Select a course");
        courses.add("COMPSCI162 COMPUTER APPLICATIONS");
        courses.add("COMPSCI170 INTRODUCTION TO PYTHON PROGRAMMING");
        courses.add("COMPSCI172 INTRODUCTION TO JAVA (GM) ");
        courses.add("COMPSCI174 INTRODUCTION TO C++ (GM) ");
        courses.add("COMPSCI180 DATA SCIENCE FOR EVERYONE");
        courses.add("COMPSCI181 INTRODUCTION TO DATABASE AND THE WEB");
        courses.add("COMPSCI476 SOFTWARE ENGINEERING");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addMonTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle(getString(R.string.dialog_title));
                mbuilder.setMultiChoiceItems(availabilities, checkedTimes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (!muserAvail.contains(position)) {
                                muserAvail.add(position);
                            }
                        }
                        else if(muserAvail.contains(position)){
                            muserAvail.remove(position);
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";

                        for (int i = 0;i < muserAvail.size(); i++)
                        {
                            item = item + availabilities[muserAvail.get(i)];
                            if (i != muserAvail.size() -1)
                            {
                                item = item + ", ";
                            }
                        }
                        monAvail.setText(item);
                    }
                });

                mbuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mbuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedTimes.length; i++){
                            checkedTimes[i] = false;
                            muserAvail.clear();
                            monAvail.setText("Not Available");
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });


        addTuesTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle(getString(R.string.dialog_title));
                mbuilder.setMultiChoiceItems(availabilities, checkedTimes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (!muserAvail.contains(position)) {
                                muserAvail.add(position);
                            }
                        }
                        else if(muserAvail.contains(position)){
                            muserAvail.remove(position);
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";

                        for (int i = 0;i < muserAvail.size(); i++)
                        {
                            item = item + availabilities[muserAvail.get(i)];
                            if (i != muserAvail.size() -1)
                            {
                                item = item + ", ";
                            }
                        }
                        tuesAvail.setText(item);
                    }
                });

                mbuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mbuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedTimes.length; i++){
                            checkedTimes[i] = false;
                            muserAvail.clear();
                            tuesAvail.setText("Not Available");
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });

        addWedTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle(getString(R.string.dialog_title));
                mbuilder.setMultiChoiceItems(availabilities, checkedTimes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (!muserAvail.contains(position)) {
                                muserAvail.add(position);
                            }
                        }
                        else if(muserAvail.contains(position)){
                            muserAvail.remove(position);
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";

                        for (int i = 0;i < muserAvail.size(); i++)
                        {
                            item = item + availabilities[muserAvail.get(i)];
                            if (i != muserAvail.size() -1)
                            {
                                item = item + ", ";
                            }
                        }
                        wedAvail.setText(item);
                    }
                });

                mbuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mbuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedTimes.length; i++){
                            checkedTimes[i] = false;
                            muserAvail.clear();
                            wedAvail.setText("Not Available");
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });

        addThursTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle(getString(R.string.dialog_title));
                mbuilder.setMultiChoiceItems(availabilities, checkedTimes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (!muserAvail.contains(position)) {
                                muserAvail.add(position);
                            }
                        }
                        else if(muserAvail.contains(position)){
                            muserAvail.remove(position);
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";

                        for (int i = 0;i < muserAvail.size(); i++)
                        {
                            item = item + availabilities[muserAvail.get(i)];
                            if (i != muserAvail.size() -1)
                            {
                                item = item + ", ";
                            }
                        }
                        thurAvail.setText(item);
                    }
                });

                mbuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mbuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedTimes.length; i++){
                            checkedTimes[i] = false;
                            muserAvail.clear();
                            thurAvail.setText("Not Available");
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });

        addFriTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
                mbuilder.setTitle(getString(R.string.dialog_title));
                mbuilder.setMultiChoiceItems(availabilities, checkedTimes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if (!muserAvail.contains(position)) {
                                muserAvail.add(position);
                            }
                        }
                        else if(muserAvail.contains(position)){
                            muserAvail.remove(position);
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton(getString(R.string.ok_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";

                        for (int i = 0;i < muserAvail.size(); i++)
                        {
                            item = item + availabilities[muserAvail.get(i)];
                            if (i != muserAvail.size() -1)
                            {
                                item = item + ", ";
                            }
                        }
                        friAvail.setText(item);
                    }
                });

                mbuilder.setNegativeButton(getString(R.string.dismiss_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mbuilder.setNeutralButton(getString(R.string.clear_all_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedTimes.length; i++){
                            checkedTimes[i] = false;
                            muserAvail.clear();
                            friAvail.setText("Not Available");
                        }
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference tutorNameref = FirebaseDatabase.getInstance().getReference("Tutors").child(userId);

                tutorNameref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        TutorUser tutorUser = dataSnapshot.getValue(TutorUser.class);

                        assert tutorUser != null;
                        final String tutorName = tutorUser.getName();
                        String tutorEmail = tutorUser.getEmail();

                        String mondayAvailability = monAvail.getText().toString();
                        String tuesdayAvailability = tuesAvail.getText().toString();
                        String wednesdayAvailability = wedAvail.getText().toString();
                        String thursdayAvailability = thurAvail.getText().toString();
                        String fridayAvailability = friAvail.getText().toString();


                        final String exp = experience.getText().toString();

                        reference = FirebaseDatabase.getInstance().getReference("Courses");

                        final String id = reference.push().getKey();

                        String courseName = spinner.getSelectedItem().toString();

                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("courseName",courseName);
                        hashMap.put("experience",exp);
                        hashMap.put("id",id);
                        hashMap.put("tutorName",tutorName);
                        hashMap.put("tutorEmail",tutorEmail);
                        hashMap.put("mondayAvailability",mondayAvailability);
                        hashMap.put("tuesdayAvailability",tuesdayAvailability);
                        hashMap.put("wednesdayAvailability",wednesdayAvailability);
                        hashMap.put("thursdayAvailability",thursdayAvailability);
                        hashMap.put("fridayAvailability",fridayAvailability);
                        hashMap.put("tutorId",userId);

                        reference.child(id).setValue(hashMap);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getContext(),"Added values",Toast.LENGTH_SHORT).show();


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorAddCourse());
                ft.commit();
            }
        });
        return view;
    }
}

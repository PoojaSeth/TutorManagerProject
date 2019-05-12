package com.example.tutormanagerapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import Helper.StudentUser;

public class StudentPopupActivityAppointment extends AppCompatActivity {

    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference coursesRef;
    public DatabaseReference appSubmit;
    public Spinner ds;
    public Spinner ts;
    public TextView te;
    public TextView tn;
    public Button cb;
    public Button sub;
    public ArrayList<String> dsl = new ArrayList<String>();
    public ArrayList<String> tsl = new ArrayList<String>();
    public ArrayList<String> at = new ArrayList<String>();
    public String day;
    public String time;
    public int checkD;
    public int checkT;
    public String tutorName;
    public String tutorId;
    public String courseID;
    public String courseName;

    public Query select;

    public String si;
    public String sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_popup_appointment);

        ds = (Spinner) findViewById(R.id.daySpinner);
        ts = (Spinner) findViewById(R.id.timeSpinner);
        te = (TextView) findViewById(R.id.tutorE);
        tn = (TextView) findViewById(R.id.tutorN);
        cb = (Button) findViewById(R.id.close);
        sub = (Button) findViewById(R.id.sub);

        coursesRef = FirebaseDatabase.getInstance().getReference().child("Courses");
        appSubmit = FirebaseDatabase.getInstance().getReference();

        Bundle tutorThings = getIntent().getExtras();
        tutorName = tutorThings.getString("Name");
        courseID = tutorThings.getString("ID");
        courseName = tutorThings.getString("Course");

        tn.setText(tutorName);

        Query teq = ref.child("Courses/"+courseID+"/experience");
        teq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                te.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query tiq = ref.child("Courses/"+courseID+"/tutorId");
        tiq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tutorId = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final FirebaseUser student = FirebaseAuth.getInstance().getCurrentUser();

        if(student != null)
        {
            si = student.getUid();
            Query siq = ref.child("Students/"+si+"/name");
            siq.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    sn = dataSnapshot.getValue().toString();
                    Log.d("WHAT IS THIS: ","THIS IS THE DATA: "+dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        Log.d("IMPORTANT STUDENT", "THIS IS THE DATA FROM STUDENT"+sn);
        dsl.add(0, "Select A Day");
        dsl.add("Monday");
        dsl.add("Tuesday");
        dsl.add("Wednesday");
        dsl.add("Thursday");
        dsl.add("Friday");

        final ArrayAdapter<String> dsa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsl);
        dsa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ds.setAdapter(dsa);

        tsl.add(0, "Select A Time");
        final ArrayAdapter<String> tsa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tsl);
        tsa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ts.setAdapter(tsa);

        ds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(checkD>=1)
                {
                    day = ds.getSelectedItem().toString();
                    tsl.clear();
                    tsl.add("Select A Time");



                    if(ds.getSelectedItem().toString().equalsIgnoreCase("Select A Day"))
                    {
                        //for Testing Purposes
                        // Toast.makeText(parent.getContext(), "Nothing to Add", Toast.LENGTH_SHORT);

                    }
                    else{

                        // For Testing purposes
                        //Toast.makeText(parent.getContext(), "Currently Selected is: "+selection, Toast.LENGTH_SHORT).show();
                        Log.d("SELECTION SELECTION", "selection is currently"+day);
                        switch (day){
                            case "Monday":
                                select = ref.child("Courses/"+courseID+"/mondayAvailability");
                                select.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("NOTICEME", "The data is "+dataSnapshot);

                                        String times = dataSnapshot.getValue().toString();
                                        String bits = ",";
                                        String[] lilBits = times.split(bits);
                                        for(int i=0;i<lilBits.length;i++){
                                            tsl.add(lilBits[i]);
                                        }

                                        Log.d("IS IT HERE YET: ", "TSL IS CURRENTLY: "+tsl);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });



                                break;
                            case "Tuesday":
                                select = ref.child("Courses/"+courseID+"/tuesdayAvailability");
                                select.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("NOTICEME", "The data is "+dataSnapshot);

                                        String times = dataSnapshot.getValue().toString();
                                        String bits = ",";
                                        String[] lilBits = times.split(bits);
                                        for(int i=0;i<lilBits.length;i++){
                                            tsl.add(lilBits[i]);
                                        }

                                        Log.d("IS IT HERE YET: ", "TSL IS CURRENTLY: "+tsl);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case "Wednesday":
                                select = ref.child("Courses/"+courseID+"/wednesdayAvailability");
                                select.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("NOTICEME", "The data is "+dataSnapshot);

                                        String times = dataSnapshot.getValue().toString();
                                        String bits = ",";
                                        String[] lilBits = times.split(bits);
                                        for(int i=0;i<lilBits.length;i++){
                                            tsl.add(lilBits[i]);
                                        }

                                        Log.d("IS IT HERE YET: ", "TSL IS CURRENTLY: "+tsl);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case "Thursday":
                                select = ref.child("Courses/"+courseID+"/thursdayAvailability");
                                select.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("NOTICEME", "The data is "+dataSnapshot);

                                        String times = dataSnapshot.getValue().toString();
                                        String bits = ",";
                                        String[] lilBits = times.split(bits);
                                        for(int i=0;i<lilBits.length;i++){
                                            tsl.add(lilBits[i]);
                                        }

                                        Log.d("IS IT HERE YET: ", "TSL IS CURRENTLY: "+tsl);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case "Friday":
                                select = ref.child("Courses/"+courseID+"/fridayAvailability");
                                select.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("NOTICEME", "The data is "+dataSnapshot);

                                        String times = dataSnapshot.getValue().toString();
                                        String bits = ",";
                                        String[] lilBits = times.split(bits);
                                        for(int i=0;i<lilBits.length;i++){
                                            tsl.add(lilBits[i]);
                                        }

                                        Log.d("IS IT HERE YET: ", "TSL IS CURRENTLY: "+tsl);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                break;
                        }

                    }

                }

                else
                {
                    checkD++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (checkT >=1){
                    time = ts.getSelectedItem().toString();

                }
                else{
                    checkT++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (day.equals("Select A Day")){
                    Toast.makeText(StudentPopupActivityAppointment.this,"Please Select a Day for the Appointment", Toast.LENGTH_LONG);
                }
                else{
                    if (time.equals("Select A Time") || time.equals("Not Available")){
                        Toast.makeText(StudentPopupActivityAppointment.this,"Please Select a Time for the Appointment", Toast.LENGTH_LONG);
                    }
                    else{

                        DatabaseReference referenceAppointment = FirebaseDatabase.getInstance().getReference("Appointments");

                        final String id = referenceAppointment.push().getKey();

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("tutorName",tutorName);
                        hashMap.put("studentName",sn);
                        hashMap.put("courseName",courseName);
                        hashMap.put("appointmentTime", time);
                        hashMap.put("appointmentDay", day);
                        hashMap.put("TutorId", tutorId);
                        hashMap.put("StudentId", si);
                        hashMap.put("id",id);

                        Log.d("STUDENT ID IS: ", "THIS IS THE STUDENT NAME: "+sn);

                        referenceAppointment.child(id).setValue(hashMap);

                        Toast.makeText(StudentPopupActivityAppointment.this, "You have made an appointment!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }


            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = dm.widthPixels;
        int h = dm.heightPixels;

        getWindow().setLayout((int)(w*.8),(int)(h*.8));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}

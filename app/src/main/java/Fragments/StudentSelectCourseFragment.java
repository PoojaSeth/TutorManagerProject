

package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import Helper.Course;
import com.example.tutormanagerapp.R;
import com.example.tutormanagerapp.StudentPopupActivityAppointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Helper.Tutor;


public class StudentSelectCourseFragment extends Fragment {

    public DatabaseReference reference;

    public FirebaseAuth auth;

    public ArrayAdapter test;

    public Spinner spinner;

    public ArrayList<String> tutors = new ArrayList<String>();

    public Tutor tutor;

    public ListView lv;

    public String id;

    public TextView tv;

    public String newt = "Current Data";

    public ArrayList<String> courses;

    public String selection;

    public int check;

    public String cID;

    public String tutorN;

    public String tn;

    public ArrayList<String> tutorId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_select_course, container, false);

        lv = (ListView) view.findViewById(R.id.tutorList);

        spinner = (Spinner) view.findViewById(R.id.coursesSearch);

        reference = FirebaseDatabase.getInstance().getReference().child("Courses");


        tv = (TextView) view.findViewById(R.id.textView4);

        courses = new ArrayList<>();
        courses.add(0, "Select a course");
        courses.add("COMPSCI162 COMPUTER APPLICATIONS");
        courses.add("COMPSCI170 INTRODUCTION TO PYTHON PROGRAMMING");
        courses.add("COMPSCI172 INTRODUCTION TO JAVA (GM) ");
        courses.add("COMPSCI174 INTRODUCTION TO C++ (GM) ");
        courses.add("COMPSCI180 DATA SCIENCE FOR EVERYONE");
        courses.add("COMPSCI181 INTRODUCTION TO DATABASE AND THE WEB");
        courses.add("COMPSCI476 SOFTWARE ENGINEERING");


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tutorId = new ArrayList<String>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(check>=1)
                {
                    tutors.clear();
                    selection = spinner.getSelectedItem().toString();



                    if(spinner.getSelectedItem().toString().equalsIgnoreCase("Select a course"))
                    {
                        //for Testing Purposes
                        // Toast.makeText(parent.getContext(), "Nothing to Add", Toast.LENGTH_SHORT);

                    }
                    else{

                        // For Testing purposes
                        //Toast.makeText(parent.getContext(), "Currently Selected is: "+selection, Toast.LENGTH_SHORT).show();
                        tv.setText(selection);

                        Query select = reference.orderByChild("courseName").equalTo(selection);

                        Log.d("SELECTION SELECTION", "selection is currently"+selection);
                        select.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("NOTICEME", "The data is "+dataSnapshot);
                                for(DataSnapshot childSnapShot: dataSnapshot.getChildren())
                                {
                                    Course c = childSnapShot.getValue(Course.class);

                                    tutorN = c.getTutorName();
                                    tutors.add(tutorN);
                                    String ci = c.getId();
                                    tutorId.add(ci);
                                    String cid = childSnapShot.getKey();
                                    //Log.d("ID of Original: ", cid);
                                }
                                test = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,tutors);
                                lv.setAdapter(test);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                }

                else
                {
                    check++;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), StudentPopupActivityAppointment.class);
                Log.d("CURRENT SELECTION IS: ","Position is set as: "+selection);
                tn = tutors.get(position);
                cID = tutorId.get(position);
                String cN = tv.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("Name", tn);
                bundle.putString("ID", cID);
                bundle.putString("Course", cN);
                i.putExtras(bundle);
                Log.d("ID for Course is her: ", "Id for course name is: "+tn);
                Log.d("Tutor's Name: ",tn);
                Log.d("ID of course: ", cID);
                startActivity(i);
            }
        });

        return view;
    }
}


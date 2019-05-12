package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutormanagerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.TutorCoursesAdapter;
import Helper.Course;


public class TutorDeleteCourseFragment extends Fragment {

    ArrayList<Course> coursesList;
    TutorCoursesAdapter tutorCoursesAdapter;

    DatabaseReference reference;
    RecyclerView deleteCourseRV;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_tutor_delete_course,container,false);

       deleteCourseRV = (RecyclerView)view.findViewById(R.id.coursesRV);
       deleteCourseRV.setLayoutManager(new LinearLayoutManager(getContext()));
       coursesList = new ArrayList<Course>();

       reference = FirebaseDatabase.getInstance().getReference("Courses");

       reference.addValueEventListener(new ValueEventListener() {

           FirebaseAuth auth = FirebaseAuth.getInstance();
           FirebaseUser firebaseUser = auth.getCurrentUser();
           final String userId = firebaseUser.getUid();

           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               coursesList.clear();

               for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
               {
                   Course course = dataSnapshot1.getValue(Course.class);

                   assert course != null;
                   if (course.getTutorId().equals(userId))
                   {
                       coursesList.add(course);
                   }

               }

               tutorCoursesAdapter = new TutorCoursesAdapter(getContext(),coursesList);
               deleteCourseRV.addItemDecoration(new DividerItemDecoration(deleteCourseRV.getContext(),DividerItemDecoration.VERTICAL));
               deleteCourseRV.setAdapter(tutorCoursesAdapter);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


       return view;
    }
}

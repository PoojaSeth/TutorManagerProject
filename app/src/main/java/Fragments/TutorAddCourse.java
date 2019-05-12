/* Written by: Kameng Thao */

package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tutormanagerapp.R;


public class TutorAddCourse extends Fragment {

    Button add_a_course,delete_a_course;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_add_course,container,false);

        add_a_course = (Button)view.findViewById(R.id.add_a_course);

        delete_a_course = (Button)view.findViewById(R.id.delete_a_course);

        add_a_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TuorAddCourseForm());
                ft.commit();
            }
        });

        delete_a_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorDeleteCourseFragment());
                ft.commit();
            }
        });

        return view;
    }
}

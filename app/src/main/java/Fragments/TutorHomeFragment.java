package Fragments;

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
import android.widget.ListView;

import com.example.tutormanagerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.AppointmentsAdapter;
import Adapter.TutorAppointmentAdapter;
import Helper.Appointments;
import Helper.TutorAppointments;


public class TutorHomeFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;

    ArrayList<TutorAppointments> tutorappointmentsList;

    TutorAppointmentAdapter tutorAppointmentAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_home, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.appointRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tutorappointmentsList = new ArrayList<TutorAppointments>();

        reference = FirebaseDatabase.getInstance().getReference().child("Appointments");

        reference.addValueEventListener(new ValueEventListener() {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            final String userId = firebaseUser.getUid();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tutorappointmentsList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {

                    TutorAppointments tutorAppointments = dataSnapshot1.getValue(TutorAppointments.class);
                    assert tutorAppointments != null;
                    if (tutorAppointments.getTutorId().equals(userId))
                    {
                        tutorappointmentsList.add(tutorAppointments);
                    }

                }
                tutorAppointmentAdapter = new TutorAppointmentAdapter(getContext(),tutorappointmentsList);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(tutorAppointmentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }

}

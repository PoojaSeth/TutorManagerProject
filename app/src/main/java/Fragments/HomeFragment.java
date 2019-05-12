/* Written by: Pooja Seth */

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
import android.widget.Toast;

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
import Helper.Appointments;

public class HomeFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;

    ArrayList<Appointments> appointmentsList;

    AppointmentsAdapter appointmentsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.appointRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appointmentsList = new ArrayList<Appointments>();

        reference = FirebaseDatabase.getInstance().getReference().child("Appointments");

        reference.addValueEventListener(new ValueEventListener() {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            final String userId = firebaseUser.getUid();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                appointmentsList.clear();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {


                    Appointments appointments = dataSnapshot1.getValue(Appointments.class);
                    assert appointments != null;
                    if (appointments.getStudentId().equals(userId))
                    {
                        appointmentsList.add(appointments);
                    }

                }

                appointmentsAdapter = new AppointmentsAdapter(getContext(),appointmentsList);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(appointmentsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"oops something is wrong",Toast.LENGTH_SHORT).show();
            }
        });



        return view;

    }
}

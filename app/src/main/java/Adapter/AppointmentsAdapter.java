package Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutormanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Helper.Appointments;


public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appointments;

    public AppointmentsAdapter(Context context, ArrayList<Appointments> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.appointment_item,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tutorName.setText(appointments.get(i).getTutorName());
        myViewHolder.courseName.setText(appointments.get(i).getCourseName());
        myViewHolder.day.setText(appointments.get(i).getAppointmentDay());
        myViewHolder.time.setText(appointments.get(i).getAppointmentTime());

        myViewHolder.deleteAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemSelected = appointments.get(i).toString();

                Log.d("item Selected is", itemSelected);

                //appointments.remove(i);

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                String id = appointments.get(i).getId();

                Log.d("Id is:", id);
//
                DatabaseReference reference = database.getReference("Appointments").child(id);

                Log.d("Done reference", id);

                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(context,"Appointment deleted",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                notifyItemRemoved(i);

                notifyItemRangeChanged(i,appointments.size());


                Toast.makeText(context,"delete clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tutorName, courseName,day,time;
        Button deleteAppBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tutorName = (TextView)itemView.findViewById(R.id.tutorName);
            courseName = (TextView)itemView.findViewById(R.id.courseName);
            day = (TextView) itemView.findViewById(R.id.appointDay);
            time = (TextView) itemView.findViewById(R.id.appointTime);

            deleteAppBtn = (Button)itemView.findViewById(R.id.deleteAppBtn);





        }
    }
}

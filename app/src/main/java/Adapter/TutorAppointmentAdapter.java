package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tutormanagerapp.R;

import java.util.ArrayList;

import Helper.Appointments;
import Helper.TutorAppointments;

public class TutorAppointmentAdapter extends RecyclerView.Adapter<TutorAppointmentAdapter.MyViewHolder> {


    Context context;
    ArrayList<TutorAppointments> tutorAppointments;

    public TutorAppointmentAdapter(Context context, ArrayList<TutorAppointments> tutorAppointments)
    {
        this.context = context;
        this.tutorAppointments = tutorAppointments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TutorAppointmentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tutor_appointment_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.studentName.setText(tutorAppointments.get(i).getStudentName());
        myViewHolder.courseName.setText(tutorAppointments.get(i).getCourseName());
        myViewHolder.day.setText(tutorAppointments.get(i).getAppointmentDay());
        myViewHolder.time.setText(tutorAppointments.get(i).getAppointmentTime());

    }

    @Override
    public int getItemCount() {
        return tutorAppointments.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView studentName, courseName,day,time;
        ImageView deleteBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            studentName = (TextView)itemView.findViewById(R.id.studentName);
            courseName = (TextView)itemView.findViewById(R.id.courseName);
            day = (TextView) itemView.findViewById(R.id.appointDay);
            time = (TextView) itemView.findViewById(R.id.appointTime);

            //deleteBtn = (ImageView)itemView.findViewById(R.id.deleteBtn);





        }
    }
}

package Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutormanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Helper.Course;
import Helper.TutorAppointments;

public class TutorCoursesAdapter extends RecyclerView.Adapter<TutorCoursesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Course> coursesList;

    Dialog mydialog, editDialog;

    String[] availabilities;
    boolean[] checkedTimes;

    ArrayList<Integer> muserAvail = new ArrayList<>();


    public TutorCoursesAdapter(Context context, ArrayList<Course> coursesList)
    {
        this.context = context;
        this.coursesList = coursesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tutor_course_item,viewGroup,false));

        View view = LayoutInflater.from(context).inflate(R.layout.tutor_course_item,viewGroup,false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);

        mydialog = new Dialog(context);

        mydialog.setContentView(R.layout.dialog_delete_course);



        myViewHolder.deleteCourseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView courseName = (TextView)mydialog.findViewById(R.id.courseName);

                TextView tutorexpList = (TextView)mydialog.findViewById(R.id.tutorexpList);

                TextView mondayAvailTimes = (TextView)mydialog.findViewById(R.id.mondayAvailTimes);
                TextView tuesdayAvailTimes = (TextView)mydialog.findViewById(R.id.tuesdayAvailTimes);
                TextView wednesdayAvailTimes = (TextView)mydialog.findViewById(R.id.wednesdayAvailTimes);
                TextView thursdayAvailTimes = (TextView)mydialog.findViewById(R.id.thursdayAvailTimes);
                TextView fridayAvailTimes = (TextView)mydialog.findViewById(R.id.fridayAvailTimes);



                courseName.setText(coursesList.get(myViewHolder.getAdapterPosition()).getCourseName());
                tutorexpList.setText(coursesList.get(myViewHolder.getAdapterPosition()).getExperience());
                mondayAvailTimes.setText(coursesList.get(myViewHolder.getAdapterPosition()).getMondayAvailability());
                tuesdayAvailTimes.setText(coursesList.get(myViewHolder.getAdapterPosition()).getTuesdayAvailability());
                wednesdayAvailTimes.setText(coursesList.get(myViewHolder.getAdapterPosition()).getWednesdayAvailability());
                thursdayAvailTimes.setText(coursesList.get(myViewHolder.getAdapterPosition()).getThursdayAvailability());
                fridayAvailTimes.setText(coursesList.get(myViewHolder.getAdapterPosition()).getFridayAvailability());

                Button edit = (Button)mydialog.findViewById(R.id.edit);

                Button delete = (Button)mydialog.findViewById(R.id.delete);

                mydialog.show();

                editDialog = new Dialog(context);

                editDialog.setContentView(R.layout.dialog_edit_course_tutor);

                TextView courseName1 = (TextView)editDialog.findViewById(R.id.courseName1);

                final TextView tutorexpList1 = (TextView)editDialog.findViewById(R.id.tutorexpList1);

                final TextView mondayAvailTimes1 = (TextView)editDialog.findViewById(R.id.mondayAvailTimes1);
                final TextView tuesdayAvailTimes1 = (TextView)editDialog.findViewById(R.id.tuesdayAvailTimes);
                final TextView wednesdayAvailTimes1 = (TextView)editDialog.findViewById(R.id.wednesdayAvailTimes);
                final TextView thursdayAvailTimes1 = (TextView)editDialog.findViewById(R.id.thursdayAvailTimes);
                final TextView fridayAvailTimes1 = (TextView)editDialog.findViewById(R.id.fridayAvailTimes);


                Button addMonTimes = (Button)editDialog.findViewById(R.id.addMonTimes);
                Button addTuesTimes = (Button)editDialog.findViewById(R.id.addTuesTimes);
                Button addWedTimes = (Button)editDialog.findViewById(R.id.addWedTimes);
                Button addThursTimes = (Button)editDialog.findViewById(R.id.addThursTimes);
                Button addFriTimes = (Button)editDialog.findViewById(R.id.addFriTimes);


                courseName1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getCourseName());
                tutorexpList1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getExperience());
                mondayAvailTimes1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getMondayAvailability());
                tuesdayAvailTimes1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getTuesdayAvailability());
                wednesdayAvailTimes1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getWednesdayAvailability());
                thursdayAvailTimes1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getThursdayAvailability());
                fridayAvailTimes1.setText(coursesList.get(myViewHolder.getAdapterPosition()).getFridayAvailability());

                Button save = (Button)editDialog.findViewById(R.id.savebtn);



                Button cancel = (Button)mydialog.findViewById(R.id.cancel);
                Button cancelbtn = (Button)editDialog.findViewById(R.id.cancelbtn);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String courseId = coursesList.get(myViewHolder.getAdapterPosition()).getId();

                        Log.d("CourseId is: ", courseId);

                        DatabaseReference refDel = FirebaseDatabase.getInstance().getReference("Courses").child(courseId);

                        refDel.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    mydialog.dismiss();
                                    Toast.makeText(context,"Course Deleted",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.show();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tutorexp = tutorexpList1.getText().toString();
                        String mondayTimes = mondayAvailTimes1.getText().toString();
                        String tuesdayTimes = tuesdayAvailTimes1.getText().toString();
                        String wednesdayTimes = wednesdayAvailTimes1.getText().toString();
                        String thursdayTimes = thursdayAvailTimes1.getText().toString();
                        String fridayTimes = fridayAvailTimes1.getText().toString();

                        String courseId = coursesList.get(myViewHolder.getAdapterPosition()).getId();

                        Log.d("CourseId is: ", courseId);

                        DatabaseReference refNew = FirebaseDatabase.getInstance().getReference("Courses").child(courseId);

                        refNew.child("experience").setValue(tutorexp);
                        refNew.child("mondayAvailability").setValue(mondayTimes);
                        refNew.child("tuesdayAvailability").setValue(tuesdayTimes);
                        refNew.child("wednesdayAvailability").setValue(wednesdayTimes);
                        refNew.child("thursdayAvailability").setValue(thursdayTimes);
                        refNew.child("fridayAvailability").setValue(fridayTimes);

                        editDialog.dismiss();
                        mydialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });
            }
        });

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.courseName.setText(coursesList.get(i).getCourseName());
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView courseName;
        RelativeLayout deleteCourseItem;
        public MyViewHolder(View itemView)
        {
            super(itemView);

            deleteCourseItem = (RelativeLayout)itemView.findViewById(R.id.deleteLayout);
            courseName = (TextView)itemView.findViewById(R.id.courseName);


        }
    }

}

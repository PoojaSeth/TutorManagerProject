package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tutormanagerapp.R;
import com.example.tutormanagerapp.StudentMessageActivity;
import com.example.tutormanagerapp.StudentUser;

import org.w3c.dom.Text;

import java.util.List;

public class StudentUserAdapter extends RecyclerView.Adapter<StudentUserAdapter.ViewHolder> {

    private Context mcontext;
    private List<StudentUser> users;

    public StudentUserAdapter(Context mcontext,List<StudentUser> users)
    {
        this.mcontext = mcontext;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.student_info,viewGroup,false);
        return new StudentUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final StudentUser user = users.get(i);
        viewHolder.name.setText(user.getName());
        if (user.getImageURL().equals("default"))
        {
            viewHolder.profilepic.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Glide.with(mcontext).load(user.getImageURL()).into(viewHolder.profilepic);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, StudentMessageActivity.class);
                intent.putExtra("UserId",user.getId());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public ImageView profilepic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTV);
            profilepic = itemView.findViewById(R.id.profilepic);

        }
    }
}

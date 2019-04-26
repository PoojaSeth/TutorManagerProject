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

import com.example.tutormanagerapp.TutorMessageActivity;
import com.example.tutormanagerapp.TutorUser;

import java.util.List;

public class TutorUserAdapter extends RecyclerView.Adapter<TutorUserAdapter.ViewHolder>{

    private Context mcontext;
    private List<TutorUser> users;
    //private boolean isChat;


    public TutorUserAdapter(Context mcontext, List<TutorUser> users) {
        this.mcontext = mcontext;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.tutor_user_item,viewGroup,false);
        return new TutorUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TutorUser user = users.get(i);
        viewHolder.username.setText(user.getName());
        if (user.getImageURL().equals("default"))
        {
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Glide.with(mcontext).load(user.getImageURL()).into(viewHolder.profile_image);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, TutorMessageActivity.class);
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
        public TextView username;
        public ImageView profile_image;
        //private ImageView img_on;
        //private ImageView img_off;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            //img_on = itemView.findViewById(R.id.img_on);
            //img_off = itemView.findViewById(R.id.img_off);


        }
    }


}

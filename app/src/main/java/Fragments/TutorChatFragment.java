/* Written by: Pooja Seth */

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

import Fragments.TutorListChatsFragment;
import Fragments.TutorListUsersFragment;


public class TutorChatFragment extends Fragment {

    Button tutorChatbtn, tutorUserBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutor_chat, container, false);

        //tutorChatbtn = (Button)view.findViewById(R.id.tutorchatsbtn);
        tutorUserBtn = (Button)view.findViewById(R.id.tutorUsersbtn);

//        tutorChatbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container,new TutorListChatsFragment());
//                ft.commit();
//            }
//        });

        tutorUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new TutorListUsersFragment());
                ft.commit();
            }
        });

        return view;

    }

}

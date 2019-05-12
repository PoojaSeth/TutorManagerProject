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

import Fragments.StudentChatFragment;
import Fragments.StudentUserFragment;

public class ChatFragment extends Fragment {

  Button studentChatbtn, studentUserBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_chat,container,false);

    //studentChatbtn = (Button)view.findViewById(R.id.studentchatsbtn);
    studentUserBtn = (Button)view.findViewById(R.id.studentUsersbtn);


//    studentChatbtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_container,new StudentChatFragment());
//            ft.commit();
//        }
//    });

        studentUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,new StudentUserFragment());
                ft.commit();
            }
        });


        return view;

    }
}

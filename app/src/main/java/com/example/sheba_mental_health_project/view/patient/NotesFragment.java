package com.example.sheba_mental_health_project.view.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.view.ChatFragment;

public class NotesFragment extends Fragment {

    private final String TAG = "NotesFragment";


    public NotesFragment() {}

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_container, ChatFragment.newInstance())
                .commit();

        return rootView;
    }
}
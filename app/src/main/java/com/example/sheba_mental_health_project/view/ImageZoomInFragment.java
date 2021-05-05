package com.example.sheba_mental_health_project.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.view.therapist.HistoryFragment;


public class ImageZoomInFragment extends Fragment {



    public static ImageZoomInFragment newInstance(String imageUri) {
        ImageZoomInFragment fragment = new ImageZoomInFragment();
        Bundle args = new Bundle();
        args.putSerializable("imageUri", imageUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_image_zoom_in, container, false);
        ImageView imageView = rootView.findViewById(R.id.document_iv);
        ImageButton backImageBtn = rootView.findViewById(R.id.back_btn);
        final String imageUri;

        if (getArguments() != null) {
            imageUri = (String) getArguments().get("imageUri");
            Glide.with(requireActivity().getApplicationContext()).load(imageUri).into(imageView);
        }

        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        return rootView;
    }
}
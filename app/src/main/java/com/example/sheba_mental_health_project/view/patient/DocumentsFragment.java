package com.example.sheba_mental_health_project.view.patient;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.DocumentsAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.DocumentsViewModel;
import com.google.android.material.button.MaterialButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class DocumentsFragment extends Fragment {

    private DocumentsViewModel mViewModel;

    private RecyclerView mRecyclerView;

    private DocumentsAdapter mAdapter;

    private File mFile;
    private Uri mSelectedImage;

    private final int WRITE_PERMISSION_REQUEST = 7;

    private final String TAG = "DocumentsFragment";


    public static DocumentsFragment newInstance() {
        return new DocumentsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Documents)).get(DocumentsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.documents_fragment, container, false);

        final ImageButton backBtn = rootView.findViewById(R.id.back_btn);
        mRecyclerView = rootView.findViewById(R.id.documents_recycler);
        final MaterialButton addDocumentBtn = rootView.findViewById(R.id.add_document_btn);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        addDocumentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "sheba_mental_health_project" + System.nanoTime() + "_pic.jpg");
                mSelectedImage = FileProvider.getUriForFile(requireContext(),
                        "com.example.sheba_mental_health_project.provider", mFile);

                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setOutputUri(mSelectedImage)
                        .start(requireContext(), DocumentsFragment.this);

                preventDoubleClickOnView(addDocumentBtn);
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_PERMISSION_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "sheba_mental_health_project_" + System.nanoTime() + "_pic.jpg");
                mSelectedImage = FileProvider.getUriForFile(requireContext(),
                        "com.example.sheba_mental_health_project.provider", mFile);
                CropImage.activity()
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setOutputUri(mSelectedImage)
                        .start(requireContext(), DocumentsFragment.this);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                //TODO: Add the new image to the recycler
            }
        }
    }

    private void preventDoubleClickOnView(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 500);
    }
}

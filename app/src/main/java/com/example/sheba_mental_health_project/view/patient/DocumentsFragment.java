package com.example.sheba_mental_health_project.view.patient;

import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.DocumentsAdapter;
import com.example.sheba_mental_health_project.model.RotateBitmap;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.view.ConfirmationDialog;
import com.example.sheba_mental_health_project.view.WarningDialog;
import com.example.sheba_mental_health_project.viewmodel.DocumentsViewModel;
import com.google.android.material.button.MaterialButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

public class DocumentsFragment extends Fragment {

    private DocumentsViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private TextView mNoDocumentsTv;

    private DocumentsAdapter mAdapter;

    private File mFile;
    private Uri mSelectedImage;
    private RotateBitmap mRotateBitmap;

    final int CAMERA_REQUEST = 2;
    private final int WRITE_PERMISSION_REQUEST = 7;

    private final String TAG = "DocumentsFragment";

    public interface DocumentsFragmentInterface {
        void onContinueFromDocuments(final String imageUri);
    }

    private DocumentsFragment.DocumentsFragmentInterface listener;


    public static DocumentsFragment newInstance(boolean isTherapist, Appointment appointment) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        args.putSerializable("isTherapist", isTherapist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DocumentsFragment.DocumentsFragmentInterface) context;
        } catch (Exception ex) {
            throw new ClassCastException("The Activity Must Implements DocumentsFragmentInterface listener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.Documents)).get(DocumentsViewModel.class);

        if(getArguments() != null) {
            mViewModel.setIsTherapist( (boolean) getArguments().get("isTherapist"));
            mViewModel.setSelectedAppointment( (Appointment) getArguments().get("appointment"));
        }

        final Observer<Uri> uploadDocumentObserverSuccess = new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                mViewModel.updateDocumentOfAppointment(uri.toString(),false);

            }
        };

        final Observer<String> uploadDocumentObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s);
            }
        };

        final Observer<String> updateDocumentsObserverSuccess = new Observer<String>() {
            @Override
            public void onChanged(String s) {
               // mAdapter.notifyDataSetChanged();
            }
        };

        final Observer<String> updateDocumentsObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s);
            }
        };

        final Observer<List<String>> getDocumentsObserverSuccess = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> documents) {

                if(documents.isEmpty()){
                    mNoDocumentsTv.setVisibility(View.VISIBLE);
                }
                else {
                    mNoDocumentsTv.setVisibility(View.GONE);
                }

                mAdapter = new DocumentsAdapter(requireContext(),documents,mViewModel.isTherapist());
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setDocumentListener(new DocumentsAdapter.DocumentListener() {
                    @Override
                    public void onDocumentClicked(int position, View view) {
                        final String clickedImageUri = mAdapter.getItemAtPosition(position);
                        listener.onContinueFromDocuments(clickedImageUri);
                    }

                    @Override
                    public void onRemoveDocumentClicked(int position, View view) {
                        final WarningDialog warningDialog = new WarningDialog(requireContext());
                        warningDialog.setPromptText(getString(R.string.document_deletion_prompt));
                        warningDialog.setOnActionListener(new WarningDialog.WarningDialogActionInterface() {
                            @Override
                            public void onYesBtnClicked() {
                                mViewModel.updateDocumentOfAppointment(mAdapter.getItemAtPosition(position),true);
                                mViewModel.deleteDocumentFromStorage(mAdapter.getItemAtPosition(position));
                            }

                            @Override
                            public void onNoBtnClicked() {}
                        });
                        warningDialog.show();

                    }
                });
            }
        };

        final Observer<String> getDocumentsObserverFailed = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: "+s);
            }
        };




        mViewModel.getUploadDocumentSucceed().observe(this, uploadDocumentObserverSuccess);
        mViewModel.getUploadDocumentFailed().observe(this,uploadDocumentObserverFailed);

        mViewModel.getUpdateDocumentSucceed().observe(this,updateDocumentsObserverSuccess);
        mViewModel.getUpdateDocumentFailed().observe(this,updateDocumentsObserverFailed);

        mViewModel.getGetDocumentSucceed().observe(this,getDocumentsObserverSuccess);
        mViewModel.getGetDocumentFailed().observe(this,getDocumentsObserverFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.documents_fragment, container, false);

        final ImageButton backBtn = rootView.findViewById(R.id.back_btn);
        mNoDocumentsTv = rootView.findViewById(R.id.no_documents_tv);
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

        if ( !mViewModel.isTherapist() ) {
            addDocumentBtn.setVisibility(View.VISIBLE);
            addDocumentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "sheba_mental_health_project" + System.nanoTime() + "_pic.jpg");
                    mSelectedImage = FileProvider.getUriForFile(requireContext(),
                            "com.example.sheba_mental_health_project.provider", mFile);

//                CropImage.activity()
//                        .setCropShape(CropImageView.CropShape.RECTANGLE)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setOutputUri(mSelectedImage)
//                        .start(requireContext(), DocumentsFragment.this);
//
//                preventDoubleClickOnView(addDocumentBtn);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mSelectedImage);
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            });
        }
        else{
            addDocumentBtn.setVisibility(View.GONE);
        }

        mViewModel.readDocuments();
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

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (result != null) {
//                //TODO: Add the new image to the recycler
//                Log.d(TAG, "onActivityResult: "+ mSelectedImage);
//                mViewModel.uploadPicture(mSelectedImage);
//            }
//        }
        if (resultCode == getActivity().RESULT_OK) {

            if (requestCode == CAMERA_REQUEST) {
               mViewModel.uploadPicture(mSelectedImage);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mViewModel != null){
            mViewModel.removeDocumentsListener();
        }
    }
}

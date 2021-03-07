package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.AddPatientViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddPatientFragment extends Fragment {

    private AddPatientViewModel mViewModel;

    private final String mEmailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private TextInputEditText mEmailEt;
    private TextInputEditText mPasswordEt;
    private TextInputEditText mFirstNameEt;
    private TextInputEditText mLastNameEt;

    private final String TAG = "AddPatientFragment";

    public static AddPatientFragment newInstance() {
        return new AddPatientFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.AddPatient)).get(AddPatientViewModel.class);

        final Observer<Void> onAddNewPatientSucceed = new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                mEmailEt.setText("");
                mPasswordEt.setText("");
                mFirstNameEt.setText("");
                mLastNameEt.setText("");
            }
        };

        final Observer<String> onAddNewPatientFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getAddNewPatientSucceed().observe(this, onAddNewPatientSucceed);
        mViewModel.getAddNewPatientFailed().observe(this, onAddNewPatientFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.add_patient_fragment, container, false);

        mEmailEt = rootView.findViewById(R.id.email_et);
        mPasswordEt = rootView.findViewById(R.id.password_et);
        mFirstNameEt = rootView.findViewById(R.id.first_name_et);
        mLastNameEt = rootView.findViewById(R.id.last_name_et);
        final MaterialButton addBtn = rootView.findViewById(R.id.add_new_patient_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailEt.getText().toString().trim();
                final String password = mPasswordEt.getText().toString().trim();
                final String firstName = mFirstNameEt.getText().toString().trim();
                final String lastName = mLastNameEt.getText().toString().trim();

                if (email.length() > 0 && password.length() > 0 &&
                        firstName.length() > 0 && lastName.length() > 0) {
                    if (!email.matches(mEmailRegex) || password.length() < 8) {
                        if (!email.matches(mEmailRegex)) {
                            mEmailEt.setError(getString(R.string.invalid_email));
                        }
                        if (password.length() < 8) {
                            mPasswordEt.setError(getString(R.string.invalid_password));
                        }
                        return;
                    }
                    mEmailEt.setError(null);
                    mPasswordEt.setError(null);

                    if (mViewModel != null) {
                        mViewModel.addNewPatient(email, password, firstName, lastName);
                    }
                } else {
                    if (email.length() < 1) {
                        mEmailEt.setError(getString(R.string.empty_email));
                    } else {
                        mEmailEt.setError(null);
                    }
                    if (password.length() < 1) {
                        mPasswordEt.setError(getString(R.string.empty_password));
                    } else {
                        mPasswordEt.setError(null);
                    }
                    if (firstName.length() < 1) {
                        mFirstNameEt.setError(getString(R.string.empty_first_name));
                    } else {
                        mFirstNameEt.setError(null);
                    }
                    if (lastName.length() < 1) {
                        mLastNameEt.setError(getString(R.string.empty_last_name));
                    } else {
                        mLastNameEt.setError(null);
                    }
                }
            }
        });

        return rootView;
    }
}

package com.example.sheba_mental_health_project.view.therapist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.model.Appointment;
import com.example.sheba_mental_health_project.model.PainPoint;
import com.example.sheba_mental_health_project.model.TherapistPhysicalStateAdapter;
import com.example.sheba_mental_health_project.model.ViewModelFactory;
import com.example.sheba_mental_health_project.model.enums.PainLocationEnum;
import com.example.sheba_mental_health_project.model.enums.ViewModelEnum;
import com.example.sheba_mental_health_project.viewmodel.TherapistPhysicalStateGenericViewModel;

import java.util.List;

public class TherapistPhysicalStateGenericFragment extends Fragment {

    private TherapistPhysicalStateGenericViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private TherapistPhysicalStateAdapter mTherapistPhysicalStateAdapter;

    private TextView mEmptyListTv;

    private final String TAG = "TherapistPhyStatGenFrag";


    public static TherapistPhysicalStateGenericFragment newInstance(final Appointment appointment) {
        TherapistPhysicalStateGenericFragment fragment = new TherapistPhysicalStateGenericFragment();
        Bundle args = new Bundle();
        args.putSerializable("appointment", appointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext(),
                ViewModelEnum.TherapistPhysicalStateGeneric)).get(TherapistPhysicalStateGenericViewModel.class);

        if (getArguments() != null) {
            mViewModel.setAppointment((Appointment) getArguments().getSerializable("appointment"));
        }

        final Observer<List <PainPoint>> onGetTherapistPhysicalStateSucceed = new Observer<List<PainPoint>>() {
            @Override
            public void onChanged(List<PainPoint> painPoints) {
                for (int i = 0; i < painPoints.size(); i++) {
                    if (painPoints.get(i).getPainLocation() == PainLocationEnum.Mouth) {
                        painPoints.remove(painPoints.get(i));
                    }
                }

                if (mEmptyListTv != null) {
                    if (painPoints != null) {
                        mEmptyListTv.setVisibility(painPoints.isEmpty() ? View.VISIBLE : View.GONE);
                    } else {
                        mEmptyListTv.setVisibility(View.VISIBLE);
                    }
                }

                mTherapistPhysicalStateAdapter = new TherapistPhysicalStateAdapter(requireContext(), painPoints);
                mRecyclerView.setAdapter(mTherapistPhysicalStateAdapter);
            }
        };

        final Observer<String> onGetTherapistPhysicalFailed = new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Log.d(TAG, "onChanged: " + error);
            }
        };

        mViewModel.getTherapistPhysicalStatePainPointsSucceed().observe(this, onGetTherapistPhysicalStateSucceed);
        mViewModel.getTherapistPhysicalStatePainPointsFailed().observe(this, onGetTherapistPhysicalFailed);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.therapist_physical_state_generic_fragment,
                container, false);

        mViewModel.attachGetAllPainPointsPhysicalListener();

        mEmptyListTv = rootView.findViewById(R.id.empty_list_tv);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        mViewModel.getPainPointsPhysical(mViewModel.getAppointment());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.removeGetAllPainPointsPhysicalListener();
    }
}

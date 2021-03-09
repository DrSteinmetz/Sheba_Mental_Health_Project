package com.example.sheba_mental_health_project.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.sheba_mental_health_project.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private final String TAG = "SettingsFragment";


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        final SwitchPreferenceCompat notificationSp = findPreference("notifications_sp");
        if (notificationSp != null) {
            notificationSp.setChecked(sp.getBoolean("notifications_sp", true));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = super.onCreateView(inflater, container, savedInstanceState);

        if (rootView != null) {
            rootView.setBackgroundColor(Color.WHITE);
        }

        return rootView;
    }
}

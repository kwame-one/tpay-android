package com.kwame.tpay.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kwame.tpay.R;
import com.takisoft.preferencex.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        // additional setup
    }
}

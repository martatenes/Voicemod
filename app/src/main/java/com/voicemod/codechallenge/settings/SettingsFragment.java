package com.voicemod.codechallenge.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.constants.Constants;
import com.voicemod.codechallenge.utils.SharedPrefUtils;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat{
    SwitchPreference swQuality, swDuration, swSize;
    SeekBarPreference sbDuration, sbSize;

    private void setUpPreferences(){
        boolean isQualityEnabled = SharedPrefUtils.getBooleanData(getActivity(), SharedPrefUtils.PREF_QUALITY);
        boolean isDurationEnabled = SharedPrefUtils.getBooleanData(getActivity(), SharedPrefUtils.PREF_DURATION);
        int durationValue = SharedPrefUtils.getIntData(getActivity(), SharedPrefUtils.PREF_DURATION_VALUE);
        boolean isSizeEnabled = SharedPrefUtils.getBooleanData(getActivity(), SharedPrefUtils.PREF_SIZE);
        int sizeValue = SharedPrefUtils.getIntData(getActivity(), SharedPrefUtils.PREF_SIZE_VALUE);

        swQuality.setEnabled(isQualityEnabled);
        swDuration.setChecked(isDurationEnabled);
        swSize.setChecked(isSizeEnabled);

        sbDuration.setEnabled(isDurationEnabled);
        sbDuration.setValue(durationValue);
        sbSize.setValue(sizeValue);
        sbSize.setEnabled(isSizeEnabled);

    }



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        // Calidad alta
        swQuality = findPreference("swQuality");

        // Duración en minutos
        swDuration = findPreference("swDuration");
        sbDuration = findPreference("sbDuration");

        // Tamaño en megabytes
        swSize = findPreference("swSize");
        sbSize = findPreference("sbSize");

        setUpPreferences();

        swQuality.setOnPreferenceChangeListener((preference, newValue) -> {
            SharedPrefUtils.saveData(getActivity(), SharedPrefUtils.PREF_QUALITY, (Boolean) newValue);
            return true;
        });

        swDuration.setOnPreferenceChangeListener((preference, newValue) -> {
            sbDuration.setEnabled((Boolean) newValue);
            SharedPrefUtils.saveData(getActivity(), SharedPrefUtils.PREF_DURATION, (Boolean) newValue);
            return true;
        });

        sbDuration.setOnPreferenceChangeListener((preference, newValue) -> {
            if ((int) newValue >= 1) {

                SharedPrefUtils.saveData(getActivity(), SharedPrefUtils.PREF_DURATION_VALUE, (int) newValue);
                final int progress = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(String.format(Locale.getDefault(), getString(R.string.TR_PLACEHOLDER_MINUTOS), progress));
                return true;
            }
            return false;
        });

        swSize.setOnPreferenceChangeListener((preference, newValue) -> {
            sbSize.setEnabled((Boolean) newValue);
            SharedPrefUtils.saveData(getActivity(), SharedPrefUtils.PREF_SIZE, (Boolean) newValue);
            return true;
        });

        sbSize.setOnPreferenceChangeListener((preference, newValue) -> {
            if ((int) newValue > 1) {
                SharedPrefUtils.saveData(getActivity(), SharedPrefUtils.PREF_SIZE_VALUE, (int) newValue);
                final int progress = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(String.format(Locale.getDefault(), getString(R.string.TR_PLACEHOLDER_MEGABYTES), progress));

                return true;
            }
            return false;
        });

    }


}

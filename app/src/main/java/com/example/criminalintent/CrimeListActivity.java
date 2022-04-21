package com.example.criminalintent;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    //Вызывается из метода onCreate в классе SingleFragmentActivity
    protected Fragment createFragment() {
        Log.i(TAG,"TAGcreateFragment");
        return new CrimeListFragment();
    }
}
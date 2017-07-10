package com.example.android.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        StepDetailFragment detailFragment = new StepDetailFragment();

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.step_detail_container, detailFragment)
                .commit();
    }
}

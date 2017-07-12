package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    private Step mStepSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mStepSelected = getIntent().getParcelableExtra("step_selected");

        StepDetailFragment detailFragment = new StepDetailFragment();

        detailFragment.setmStepSelected(mStepSelected);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.step_detail_container, detailFragment)
                .commit();
    }
}

package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    private Step mStepSelected;
    private int mCurrentPos;
    private ArrayList<Step> steps;
    private FragmentManager fm;

//    public static final String CURRENT_POS = "current_pos";
//    public static final String STEPS = "steps";
//    public static final String STEP_SELECTED = "step_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        fm = getSupportFragmentManager();

        if (savedInstanceState == null){
            mStepSelected = getIntent().getParcelableExtra("step_selected");
            String recipeName = getIntent().getStringExtra("recipeName");
            setTitle(recipeName);
            mCurrentPos = getIntent().getIntExtra("position", 0);
            steps = getIntent().getParcelableArrayListExtra("steps");

            StepDetailFragment detailFragment = new StepDetailFragment();

            detailFragment.setmStepSelected(mStepSelected);
            detailFragment.setmSteps(steps);
            detailFragment.setmCurrentPos(mCurrentPos);

            fm.beginTransaction()
                    .add(R.id.step_detail_container, detailFragment)
                    .commit();
        }
    }
}

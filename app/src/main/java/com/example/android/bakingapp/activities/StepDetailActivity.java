package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    private Step mStepSelected;
    private int mCurrentPos;
    private ArrayList<Step> steps;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mStepSelected = getIntent().getParcelableExtra("step_selected");
        String recipeName = getIntent().getStringExtra("recipeName");
        setTitle(recipeName);
        mCurrentPos = getIntent().getIntExtra("position", 0);
        steps = getIntent().getParcelableArrayListExtra("steps");

        StepDetailFragment detailFragment = new StepDetailFragment();

        detailFragment.setmStepSelected(mStepSelected);

        fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.step_detail_container, detailFragment)
                .commit();
    }


    public void navigateNext(View v){
        findViewById(R.id.previous_btn).setVisibility(View.VISIBLE);
        move(1);
    }

    public void navigatePrevious(View v){
        findViewById(R.id.next_btn).setVisibility(View.VISIBLE);
        move(-1);
    }

    private void move(int mov){
        int newIdx = mCurrentPos + mov;
        Step newStep;
        if ( newIdx <= steps.size()-1 && newIdx >= 0){
            mCurrentPos = mCurrentPos + mov;
            newStep = steps.get(mCurrentPos);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setmStepSelected(newStep);
            fm.beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        } else if (mov == 1){
           findViewById(R.id.next_btn).setVisibility(View.GONE);
        } else {
            findViewById(R.id.previous_btn).setVisibility(View.GONE);
        }
    }

}

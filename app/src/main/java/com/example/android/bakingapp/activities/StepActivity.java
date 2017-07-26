package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.fragments.StepMasterListFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity implements StepMasterListFragment.OnStepClickListener{

    public static final String STEPS = "steps";

    private ArrayList<Step> steps;
    private boolean isTablet;
    FragmentManager fm;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);


        recipeName = getIntent().getStringExtra("recipeName");

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(recipeName);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        fm = getSupportFragmentManager();
        //since we added fragment via layout xml
        if (savedInstanceState == null){
            steps = getIntent().getParcelableArrayListExtra("RecipeSelected");
            StepMasterListFragment fragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
            fragment.setSteps(steps);

            if (isTablet){
                initDetailFragment(steps);
            }
        } else {
            steps = savedInstanceState.getParcelableArrayList(STEPS);
            StepMasterListFragment fragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
            fragment.setSteps(steps);

            if (isTablet){
                initDetailFragment(steps);
            }
        }

    }

    public void initDetailFragment(ArrayList<Step> steps){
        StepDetailFragment detailFragment = new StepDetailFragment();
        //always display the firs step data
        detailFragment.setmStepSelected(steps.get(0));
        detailFragment.setmSteps(steps);
        detailFragment.setmCurrentPos(0);
        fm.beginTransaction()
                .add(R.id.step_detail_container, detailFragment)
                .commit();
    }

    @Override
    public void onStepSelected(int position) {
        if (!isTablet){//phone
            Intent step_detail_intent = new Intent(this, StepDetailActivity.class);
            step_detail_intent.putExtra("step_selected", steps.get(position));
            step_detail_intent.putExtra("position", position);
            step_detail_intent.putParcelableArrayListExtra("steps", steps);
            step_detail_intent.putExtra("recipeName", recipeName);
            startActivity(step_detail_intent);
        }else {//tablet
            StepDetailFragment detailFragment = new StepDetailFragment();
            //always display the firs step data
            detailFragment.setmStepSelected(steps.get(position));
            detailFragment.setmSteps(steps);
            detailFragment.setmCurrentPos(position);
            fm.beginTransaction()
                    .replace(R.id.step_detail_container, detailFragment)
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEPS, steps);
    }
}

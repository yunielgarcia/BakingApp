package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.fragments.StepMasterListFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity implements StepMasterListFragment.OnStepClickListener{

    private ArrayList<Step> steps;
    private boolean isTablet;
    FragmentManager fm;
    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        steps = getIntent().getParcelableArrayListExtra("RecipeSelected");
        recipeName = getIntent().getStringExtra("recipeName");

        setTitle(recipeName);

        isTablet = getResources().getBoolean(R.bool.isTablet);



        fm = getSupportFragmentManager();
        //since we added fragment via layout xml
        StepMasterListFragment fragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
        fragment.setSteps(steps);

        if (isTablet){
            StepDetailFragment detailFragment = new StepDetailFragment();
            //always display the firs step data
            detailFragment.setmStepSelected(steps.get(0));
            fm.beginTransaction()
                    .add(R.id.step_detail_container, detailFragment)
                    .commit();
        }
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
            fm.beginTransaction()
                    .replace(R.id.step_detail_container, detailFragment)
                    .commit();
        }

    }
}

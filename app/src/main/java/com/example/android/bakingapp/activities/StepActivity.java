package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepMasterListFragment;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    private ArrayList<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        steps = getIntent().getParcelableArrayListExtra("RecipeSelected");

        FragmentManager fm = getSupportFragmentManager();
        //since we added fragment via layout xml
        StepMasterListFragment fragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
        fragment.setSteps(steps);
    }
}

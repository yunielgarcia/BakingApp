package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsFragment;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.fragments.StepMasterListFragment;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity implements StepMasterListFragment.OnStepClickListener{

    public static final String STEPS = "steps";
    private static final String LIST_STATE_KEY = "list_state";
    public static final String STEP_SELECTED = "step_selected";
    public static final String POSITION = "position";
    public static final String RECIPE_NAME = "recipeName";

    private ArrayList<Step> mSteps;
    private ArrayList<Ingredient> mIngredients;
    private boolean isTablet;
    FragmentManager fm;
    String recipeName;
    private Parcelable mListState;
    private RecyclerView.LayoutManager stepListLayoutManager;
    private StepMasterListFragment mStepMasterListFragment;

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
            mSteps = getIntent().getParcelableArrayListExtra("RecipeSelected");
            mIngredients = getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
            mStepMasterListFragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
            mStepMasterListFragment.setSteps(mSteps);

            if (isTablet){
                initDetailFragment(mSteps);
            }
        } else {
            mStepMasterListFragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
            mSteps = savedInstanceState.getParcelableArrayList(STEPS);
            mIngredients = savedInstanceState.getParcelableArrayList(MainActivity.INGREDIENTS_LIST);
            StepMasterListFragment fragment = (StepMasterListFragment)fm.findFragmentById(R.id.master_list_steps);
            fragment.setSteps(mSteps);

            if (isTablet){
                initDetailFragment(mSteps);
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
            step_detail_intent.putExtra(STEP_SELECTED, mSteps.get(position));
            step_detail_intent.putExtra(POSITION, position);
            step_detail_intent.putParcelableArrayListExtra(STEPS, mSteps);
            step_detail_intent.putExtra(RECIPE_NAME, recipeName);
            startActivity(step_detail_intent);
        }else {//tablet
            StepDetailFragment detailFragment = new StepDetailFragment();
            //always display the firs step data
            detailFragment.setmStepSelected(mSteps.get(position));
            detailFragment.setmSteps(mSteps);
            detailFragment.setmCurrentPos(position);
            fm.beginTransaction()
                    .replace(R.id.step_detail_container, detailFragment)
                    .commit();
        }

    }

    @Override
    public void onIngredientsSelected() {
        if (!isTablet){//phone
            Intent ingredient_detail_intent = new Intent(this, IngredientsActivity.class);
            ingredient_detail_intent.putParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST, mIngredients);
            startActivity(ingredient_detail_intent);
        }else {//tablet
            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(mIngredients);
            fm.beginTransaction()
                    .replace(R.id.step_detail_container, ingredientsFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEPS, mSteps);
        outState.putParcelableArrayList(MainActivity.INGREDIENTS_LIST, mIngredients);
        // Save list state i.e position
        stepListLayoutManager = mStepMasterListFragment.getmLayoutManager();
        mListState = stepListLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }


    @Override
    public void onRestoreInstanceState(@Nullable Bundle state) {
        super.onRestoreInstanceState(state);
        // Retrieve list state and list/item positions
        if(state != null)
            mListState = state.getParcelable(LIST_STATE_KEY);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mListState != null) {
            mStepMasterListFragment.getmLayoutManager().onRestoreInstanceState(mListState);
        }
    }
}

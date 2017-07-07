package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepAdapter;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by ygarcia on 7/5/2017.
 */

public class StepMasterListFragment extends Fragment {

    private ArrayList<Step> mSteps;

    private ListView mStepsListView;
    private StepAdapter mStepAdapter;

    // Mandatory empty constructor
    public StepMasterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_master_list, container, false);

//        mStepsListView = (ListView) rootView.findViewById(R.id.steps_list_view);
//        mStepAdapter = new StepAdapter(getContext(), mSteps);
//        mStepsListView.setAdapter(mStepAdapter);

        return rootView;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
    }

}

package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private boolean isTablet;
    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    // Mandatory empty constructor
    public StepMasterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_master_list, container, false);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        mStepsListView = (ListView) rootView.findViewById(R.id.steps_list_view);

        //go to activity with data
        mStepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onStepSelected(position);
            }
        });


        return rootView;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
        mStepAdapter = new StepAdapter(getContext(), mSteps);
        mStepsListView.setAdapter(mStepAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //this makes sure thath the host activity has implemented the callback interface
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }
}

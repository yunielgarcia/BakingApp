package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepAdapter;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by ygarcia on 7/5/2017.
 */

public class StepMasterListFragment extends Fragment implements StepAdapter.StepListItemClickListener, View.OnClickListener {

    private ArrayList<Step> mSteps;

    private RecyclerView mStepsListView;
    private StepAdapter mStepAdapter;
    private boolean isTablet;
    OnStepClickListener mCallback;
    private LinearLayoutManager mLayoutManager;

    //this implementation comes from adapter. It gives us position to eventually pass to the activity. We do so by initializing our interface mCallback with the same position
    @Override
    public void onListItemClick(int stepSelectedPos) {
        mCallback.onStepSelected(stepSelectedPos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ing_step_block:
                mCallback.onIngredientsSelected();
        }
    }

    //this is to communicate to activity. Declaration here, initialization in onListItemClick()
    public interface OnStepClickListener {
        void onStepSelected(int position);
        void onIngredientsSelected();
    }

    // Mandatory empty constructor
    public StepMasterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_steps_master_list, container, false);

        TextView ing_block = (TextView) rootView.findViewById(R.id.ing_step_block);
        ing_block.setOnClickListener(this);


        isTablet = getResources().getBoolean(R.bool.isTablet);

        mStepsListView = (RecyclerView) rootView.findViewById(R.id.steps_list_view);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mStepsListView.setLayoutManager(mLayoutManager);
        mStepsListView.setHasFixedSize(true);
        mStepAdapter = new StepAdapter(this);
        mStepsListView.setAdapter(mStepAdapter);

        return rootView;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.mSteps = steps;
        mStepAdapter.setStepsData(this.mSteps);
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

    public LinearLayoutManager getmLayoutManager() {
        return mLayoutManager;
    }
}

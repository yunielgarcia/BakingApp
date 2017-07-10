package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

/**
 * Created by ygarcia on 7/10/2017.
 */

public class StepDetailFragment extends Fragment {


    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.step_detail_tv);
        tv.setText("Video plus steps here");


        return rootView;
    }
}

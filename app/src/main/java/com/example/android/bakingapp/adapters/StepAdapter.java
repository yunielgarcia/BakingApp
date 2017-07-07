package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by ygarcia on 7/7/2017.
 */

public class StepAdapter extends ArrayAdapter<Step>{

    public StepAdapter(@NonNull Context context, @NonNull ArrayList<Step> steps) {
        super(context, 0, steps);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.step_list_item, parent, false);
        }

        // Get the step object located at this position in the list
        Step currentStep = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView stepNameTv = (TextView) listItemView.findViewById(R.id.single_step);
        if (currentStep != null) {
            stepNameTv.setText(currentStep.getDescription());
        }

        return listItemView;
    }
}

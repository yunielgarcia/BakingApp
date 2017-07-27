package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ygarcia on 7/7/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{
    private List<Step> mSteps;
    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private StepListItemClickListener mOnClickListener;

    public StepAdapter(StepListItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface StepListItemClickListener {
        void onListItemClick(int stepSelectedPos);
    }

    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new StepAdapter.StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        Step singleStepObj = mSteps.get(position);
        Context context = holder.mStepItem_tv.getContext();
        String stepTitle = singleStepObj.getShortDescription();

        holder.mStepItem_tv.setText(stepTitle);
        if (!singleStepObj.getThumbnailURL().isEmpty() && singleStepObj.getThumbnailURL() != null ){
            Picasso.with(context).load(singleStepObj.getThumbnailURL()).into(holder.mStepImgView);
        } else {
            holder.mStepImgView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView mStepItem_tv;
        ImageView mStepImgView;

        StepAdapterViewHolder(View itemView) {
            super(itemView);
            this.mStepItem_tv = (TextView) itemView.findViewById(R.id.single_step);
            this.mStepImgView = (ImageView)itemView.findViewById(R.id.step_img_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public void setStepsData(List<Step> stepsData) {
        mSteps = stepsData;
        notifyDataSetChanged();
    }
}

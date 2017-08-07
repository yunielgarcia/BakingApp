package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Created by ygarcia on 8/1/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientAdapterViewHolder>{

    private List<Ingredient> mIngredients;

    public IngredientsAdapter() {
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new IngredientsAdapter.IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        Ingredient singleIngredient = mIngredients.get(position);
        String ingName = singleIngredient.getIngredient();
        float ingQuantity = singleIngredient.getQuantity();
        String ingMeasure = singleIngredient.getMeasure();

        holder.mIngredientName.setText(ingName);
        holder.mIngredientQuantity.setText(String.valueOf(ingQuantity));
        holder.mIngredientMeasure.setText(ingMeasure);
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView mIngredientName;
        TextView mIngredientQuantity;
        TextView mIngredientMeasure;

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            mIngredientName = (TextView) itemView.findViewById(R.id.ingredient_name_tv);
            mIngredientQuantity = (TextView) itemView.findViewById(R.id.ingredient_quantity_tv);
            mIngredientMeasure = (TextView) itemView.findViewById(R.id.ingredient_measure_tv);
        }
    }

    public void setIngredientsData(List<Ingredient> ingredientsData){
        mIngredients = ingredientsData;
        notifyDataSetChanged();
    }
}

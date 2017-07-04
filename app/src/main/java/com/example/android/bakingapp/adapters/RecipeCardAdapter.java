package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by Yggarcia on 7/4/2017.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardAdapterViewHolder>{

    private List<Recipe> mRecipes;

    @Override
    public RecipeCardAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeCardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapterViewHolder holder, int position) {
        Recipe singleIdeaObj = mRecipes.get(position);
        Context context = holder.mRecipeItem_tv.getContext();
        String recipeTitle = singleIdeaObj.getName();

        holder.mRecipeItem_tv.setText(recipeTitle);


        Typeface font = Typeface.createFromAsset(context.getAssets(), "GreatVibes-Regular.otf");
        holder.mRecipeItem_tv.setTypeface(font);
    }

    @Override
    public int getItemCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    public void setRecipesData(List<Recipe> recipesData) {
        mRecipes = recipesData;
        notifyDataSetChanged();
    }

    public class RecipeCardAdapterViewHolder extends RecyclerView.ViewHolder{

        public final TextView mRecipeItem_tv;

        public RecipeCardAdapterViewHolder(View itemView) {
            super(itemView);
            this.mRecipeItem_tv = (TextView) itemView.findViewById(R.id.title);
        }
    }
}

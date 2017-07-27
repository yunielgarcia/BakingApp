package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.widget.BakingRecipeService;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Yggarcia on 7/4/2017.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeCardAdapterViewHolder>{

    private List<Recipe> mRecipes;
    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    final private ListItemClickListener mOnClickListener;

    public RecipeCardAdapter(ListItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(Recipe recipeSelected);
    }


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
        Recipe singleRecipeObj= mRecipes.get(position);
        Context context = holder.mRecipeItem_tv.getContext();
        String recipeTitle = singleRecipeObj.getName();

        holder.mRecipeItem_tv.setText(recipeTitle);
        if (singleRecipeObj.getImage() != null && !singleRecipeObj.getImage().isEmpty()){
            Picasso.with(context).load(singleRecipeObj.getImage()).into(holder.mRecipeImgView);
        } else {
            holder.mRecipeImgView.setImageResource(R.drawable.ic_room_service_black_24dp);
        }

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

    public class RecipeCardAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView mRecipeItem_tv;
        ImageView mRecipeImgView;
        Context mContext;

        RecipeCardAdapterViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            this.mRecipeItem_tv = (TextView) itemView.findViewById(R.id.title);
            this.mRecipeImgView = (ImageView) itemView.findViewById(R.id.recipe_img_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Recipe recipeSelected = mRecipes.get(clickedPosition);
            mOnClickListener.onListItemClick(recipeSelected);

            //here we have the position of the recipe so let's update our widget here calling the service
            BakingRecipeService.startActionUpdateIngredients(mContext, clickedPosition);
        }
    }
}

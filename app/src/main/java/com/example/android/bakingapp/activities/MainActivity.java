package com.example.android.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeCardAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.rest.ServiceGenerator;
import com.example.android.bakingapp.rest.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.ListItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ServiceInterface mServiceInterface;
    private List<Recipe> recipes;
    private RecyclerView mRecyclerView;
    private RecipeCardAdapter mRecipeAdapter;
    private boolean isTablet;
    private LinearLayoutManager mLyoutManager;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(myToolbar);


        isTablet = getResources().getBoolean(R.bool.isTablet);

        mServiceInterface = new ServiceGenerator().createService(ServiceInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        if (isTablet){
            mLyoutManager = new GridLayoutManager(this, 3);
        }else {//is phone
            mLyoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }


        mRecyclerView.setLayoutManager(mLyoutManager);
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeCardAdapter(this);

         /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mRecipeAdapter);

        // Get the IdlingResource instance
        getIdlingResource();
    }

    /**
     * We call ImageDownloader.downloadImage from onStart or onResume instead of in onCreate
     * to ensure there is enougth time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        Call<List<Recipe>> call = mServiceInterface.getRecipies();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    if (mIdlingResource != null) {
                        mIdlingResource.setIdleState(true);
                    }
                    recipes = response.body();
                    mRecipeAdapter.setRecipesData(recipes);
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
                Log.d(TAG, "Number of recipes received: " + recipes.size());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onListItemClick(Recipe recipeSelected) {
        Intent startRecipeDetailIntent = new Intent(this, StepActivity.class);
        startRecipeDetailIntent.putParcelableArrayListExtra("RecipeSelected", (ArrayList<Step>) recipeSelected.getSteps());
        startRecipeDetailIntent.putExtra("recipeName", recipeSelected.getName());
        startActivity(startRecipeDetailIntent);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @VisibleForTesting
    @NonNull
    public List<Recipe> getRecipesLoaded() {
        return recipes;
    }
}

























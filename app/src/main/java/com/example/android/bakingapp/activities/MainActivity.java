package com.example.android.bakingapp.activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeCardAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.ServiceGenerator;
import com.example.android.bakingapp.rest.ServiceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ServiceInterface mServiceInterface;
    private List<Recipe> recipes;
    private RecyclerView mRecyclerView;
    private RecipeCardAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mServiceInterface = new ServiceGenerator().createService(ServiceInterface.class);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeCardAdapter();

         /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mRecipeAdapter);
        
        loadData();
    }

    private void loadData() {
//        mLoadingIndicator.setVisibility(View.VISIBLE);

        Call<List<Recipe>> call = mServiceInterface.getRecipies();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
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
                // Log error here since request failed
//                mLoadingIndicator.setVisibility(View.INVISIBLE);
                Log.e(TAG, t.toString());
//                showErrorMessage();
            }
        });
    }

}

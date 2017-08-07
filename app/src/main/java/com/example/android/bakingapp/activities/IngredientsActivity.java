package com.example.android.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsFragment;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<Ingredient> mIngredients;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        fm = getSupportFragmentManager();

        if (savedInstanceState == null){
            mIngredients = getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_LIST);
            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(mIngredients);

            fm.beginTransaction()
                    .add(R.id.ingredient_detail_container, ingredientsFragment)
                    .commit();
        }
    }
}

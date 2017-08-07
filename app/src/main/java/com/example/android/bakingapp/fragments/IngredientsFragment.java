package com.example.android.bakingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    ArrayList<Ingredient> ingredients;
    public static final String INGREDIENTS = "ingredients";


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ///get argument bundle here to initialize ingredients
        readBundle(getArguments());

        RecyclerView ingrediens_rv = (RecyclerView) rootView.findViewById(R.id.ingredients_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingrediens_rv.setLayoutManager(layoutManager);
        ingrediens_rv.setHasFixedSize(true);
        IngredientsAdapter adapter = new IngredientsAdapter();
        adapter.setIngredientsData(ingredients);
        ingrediens_rv.setAdapter(adapter);


        return rootView;
    }

    /**
     * Static factory method that takes an int parameter,
     * initializes the fragment's arguments, and returns the
     * new fragment to the client.
     */
    public static IngredientsFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsFragment f = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS, ingredients);
        f.setArguments(args);
        return f;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList(INGREDIENTS);
        }
    }

}

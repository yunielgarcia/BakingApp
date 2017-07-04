package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yggarcia on 7/4/2017.
 */

public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    private List<Step> steps;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;


//    Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}

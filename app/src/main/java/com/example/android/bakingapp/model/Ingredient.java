package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yggarcia on 7/4/2017.
 */

class Ingredient {

    @SerializedName("quantity")
    private float quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

//    Getters
    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}


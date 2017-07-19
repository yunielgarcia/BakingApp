package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.ServiceInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.rest.ServiceGenerator.createService;

/**
 * Created by ygarcia on 7/19/2017.
 */

public class BakingRecipeService extends IntentService {

    public static final String ACTION_FETCH_RECIPES = "com.example.android.bakingapp.action.fetch_recipes";
    public static final String UPD_INGREDIENTS = "com.example.android.bakingapp.action.upd_ingredients";
    public static final String INGR_POS = "ingreadient_pos";

    private List<Recipe> recipes;


    public BakingRecipeService() {
        super("BakingRecipeService");
    }

    public static void startActionFetchRecipes(Context context) {
        Intent intent = new Intent(context, BakingRecipeService.class);
        intent.setAction(ACTION_FETCH_RECIPES);
        context.startService(intent);
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, BakingRecipeService.class);
        intent.setAction(UPD_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_RECIPES == action) {
                handleFetchRecipes(0);
            } else if (UPD_INGREDIENTS == action) {
                int pos = intent.getIntExtra(INGR_POS, 0);
                handleFetchRecipes(pos);
            }
        }
    }

    private void handleFetchRecipes(final int pos) {
        ServiceInterface serviceInterface = createService(ServiceInterface.class);

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

        Call<List<Recipe>> call = serviceInterface.getRecipies();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipes = response.body();
                    List<String> recipesNames = createRecipesList(recipes);
                    String ingredients = ingredientsForRecipe(recipes.get(pos));


                    BakingWidgetProvider.updateRecipeWidget(getApplicationContext(), appWidgetManager, appWidgetIds, recipesNames, ingredients);


                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
            }
        });
    }

    private List<String> createRecipesList(List<Recipe> recipes){
        List<String> recipesName = new ArrayList<>();
        recipesName.clear();
        for (int i = 0; i < recipes.size(); i++){
            recipesName.add(i, recipes.get(i).getName());
        }
        return recipesName;
    }

    private String ingredientsForRecipe(Recipe recipe){
        String ingredients = "";

        for (int i = 0; i < recipe.getIngredients().size(); i++){
            ingredients = ingredients + recipe.getIngredients().get(i).getIngredient() + ", ";
        }
        return ingredients;
    }

}

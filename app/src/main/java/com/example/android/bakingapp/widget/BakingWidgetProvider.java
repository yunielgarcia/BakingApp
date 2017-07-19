package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {


    static RemoteViews views;

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, List<String> recipes, String ingredients) {

        //set the service intent to act as the adapter for the listView
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.putStringArrayListExtra("recipes", (ArrayList<String>) recipes);

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        views.setTextViewText(R.id.widget_ingredients_tv, ingredients);

        //create intent to lauch app
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_img_launcher, pendingIntent);

        views.setRemoteAdapter(R.id.widget_list, serviceIntent);

        //create intent to update ingredients
        Intent ingredients_intent = new Intent(context, BakingRecipeService.class);
        ingredients_intent.setAction(BakingRecipeService.UPD_INGREDIENTS);
        PendingIntent pendingIntentForIngredients = PendingIntent.getService(context, 0, ingredients_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, pendingIntentForIngredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingRecipeService.startActionFetchRecipes(context);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, List<String> recipes, String ingredients) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipes, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}


package com.example.android.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.MainActivity;
import com.example.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {


    static RemoteViews views;

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        //create intent to lauch app
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_img_launcher, pendingIntent);

        //set the service intent to act as the adapter for the listView
        Intent serviceIntent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_list, serviceIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

//        final List<Recipe> recipes = new ArrayList<>();

//        ServiceInterface serviceInterface = createService(ServiceInterface.class);
//
////        Call<List<Recipe>> call = serviceInterface.getRecipies();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
////                mLoadingIndicator.setVisibility(View.INVISIBLE);
//                if (response.isSuccessful()) {
//                    List<Recipe> recipes = response.body();
//                    List<String> recipesNames = new ArrayList<String>();
//                    recipes.clear();
//                    for (Recipe recipe: recipes) {
//                        recipesNames.add(recipe.getName());
//                    }
//
//                    // Set up the collection
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                        setRemoteAdapter(context, views, recipes);
//                    } else {
//                    setRemoteAdapterV11(context, views, recipes);
//                    }
//
//                    // Instruct the widget manager to update the widget
//                    appWidgetManager.updateAppWidget(appWidgetId, views);
//
//                } else {
//                    int statusCode = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//            }
//        });





    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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


    private static void loadData(final Context context) {

//        ServiceInterface serviceInterface = createService(ServiceInterface.class);
//
//        Call<List<Recipe>> call = serviceInterface.getRecipies();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
////                mLoadingIndicator.setVisibility(View.INVISIBLE);
//                if (response.isSuccessful()) {
//                    recipes = response.body();
//                    // Set up the collection
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                        setRemoteAdapter(context, views, recipes);
//                    } else {
//                        setRemoteAdapterV11(context, views, recipes);
//                    }
//                } else {
//                    int statusCode = response.code();
//                    // handle request errors depending on status code
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//            }
//        });
    }


    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views, List<Recipe> recipes) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putParcelableArrayListExtra("recipes", (ArrayList<Recipe>) recipes);

        views.setRemoteAdapter(R.id.widget_list, intent);
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views, List<Recipe> recipes) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putParcelableArrayListExtra("recipes", (ArrayList<Recipe>) recipes);

        views.setRemoteAdapter(0, R.id.widget_list, intent);
    }
}


package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

/**
 * Created by ygarcia on 7/17/2017.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory{

    List<String> mRecipes;
    Context context;
    Intent intent;

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        mRecipes = intent.getStringArrayListExtra("recipes");
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteView.setTextViewText(android.R.id.text1, mRecipes.get(position));

        Intent fillInIntent = new Intent();
        Bundle extras=new Bundle();

        extras.putInt(BakingRecipeService.INGR_POS, position);
        fillInIntent.putExtras(extras);
        remoteView.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}

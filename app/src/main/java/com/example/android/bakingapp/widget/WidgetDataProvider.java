package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.ServiceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.bakingapp.rest.ServiceGenerator.createService;

/**
 * Created by ygarcia on 7/17/2017.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory{

    List<Recipe> mRecipes;
    Context context;
    Intent intent;

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        ServiceInterface serviceInterface = createService(ServiceInterface.class);

        Call<List<Recipe>> call = serviceInterface.getRecipies();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipes = response.body();
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
        remoteView.setTextViewText(android.R.id.text1, mRecipes.get(position).getName());
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

    private void loadData() {



        //ServiceInterface serviceInterface = createService(ServiceInterface.class);

//        Call<List<Recipe>> call = serviceInterface.getRecipies();
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
////                mLoadingIndicator.setVisibility(View.INVISIBLE);
//                if (response.isSuccessful()) {
//                    mRecipes = response.body();
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

//    private void initData(){
//        mRecipes.clear();
//        for (int i = 1; i <= 10; i++) {
//            mRecipes.add("ListView item " + i);
//        }
//    }
}

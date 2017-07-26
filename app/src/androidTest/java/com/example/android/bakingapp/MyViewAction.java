package com.example.android.bakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

/**
 * Created by ygarcia on 7/24/2017.
 */

public class MyViewAction {

    public static String title;

    public static ViewAction clickChildViewWithId() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView v = (TextView) view.findViewById(R.id.title);
                title = v.getText().toString();
                v.performClick();
            }
        };
    }
}

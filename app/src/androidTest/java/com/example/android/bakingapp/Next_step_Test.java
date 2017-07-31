package com.example.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.activities.MainActivity;
import com.example.android.bakingapp.model.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by ygarcia on 7/31/2017.
 */
@RunWith(AndroidJUnit4.class)
public class Next_step_Test {

    private List<Recipe> recipes;
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickNext_changeStepDescription() throws Exception {
        //select recipe at 0
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        recipes = mActivityTestRule.getActivity().getRecipesLoaded();
        String textToMatch = recipes.get(0).getSteps().get(0).getDescription();
        //select step at 0
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.master_list_steps), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.step_detail_tv)).check(matches(withText(textToMatch)));
        //next
        onView(withId(R.id.next_btn)).perform(click());
        //confirm it changed
        onView(withId(R.id.step_detail_tv)).check(matches(not( withText(textToMatch))));
    }
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}

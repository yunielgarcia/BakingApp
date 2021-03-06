package com.example.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    private List<Recipe> recipes;
    private IdlingResource mIdlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeItemCard_OpensStepActivity_withName() throws Exception {

        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        recipes = mActivityTestRule.getActivity().getRecipesLoaded();
        // Checks that the OrderActivity opens with the correct tea name displayed
        onView(withId(R.id.toolbar_title)).check(matches(withText(recipes.get(0).getName())));
    }

//    @Test
//    public void clickStep_open_stepDetailActivity() throws Exception {
//
//        onView(withId(R.id.steps_list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        // Checks that the OrderActivity opens with the correct tea name displayed
//        onView(withId(R.id.step_detail_container_ll)).check(matches(isDisplayed()));
//    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}

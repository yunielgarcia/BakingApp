package com.example.android.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.activities.MainActivity;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.rest.ServiceGenerator;
import com.example.android.bakingapp.rest.ServiceInterface;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ServiceInterface mServiceInterface = new ServiceGenerator().createService(ServiceInterface.class);
    private List<Recipe> recipes;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickGridViewRecipeItem_OpensStepActivity() throws Exception {

        Call<List<Recipe>> call = mServiceInterface.getRecipies();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipes = response.body();
                    performTest();
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

    private void performTest(){
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the OrderActivity opens with the correct tea name displayed
        onView(withId(R.id.toolbar_title)).check(matches(withText(recipes.get(0).getName())));
    }
}

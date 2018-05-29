package com.dsktp.sora.bakeme.UI;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.dsktp.sora.bakeme.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainScreenActivityTest {

    @Rule
    public ActivityTestRule<MainScreenActivity> mActivityTestRule = new ActivityTestRule<>(MainScreenActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource()
    {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void mainScreenActivityTest() {
        ViewInteraction textView = onView(
                allOf(withText("List of available recipes"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("List of available recipes")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_recipe_name_value), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipes),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Nutella Pie")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tv_recipe_servings_value), withText("8"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipes),
                                        0),
                                3),
                        isDisplayed()));
        textView3.check(matches(withText("8")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.iv_recipe_image),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipes),
                                        0),
                                4),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tv_recipe_servings_tag), withText("Servings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_recipes),
                                        0),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("Servings")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @After
    public void unregisterIdlingResource()
    {
        if(mIdlingResource !=null)
        {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}

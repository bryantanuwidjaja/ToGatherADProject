package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


public class SpinnerActivities_UITest {

    public static ViewAction idleFor(final long millisec) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Idle for " + millisec + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millisec);
            }
        };
    }

    @Rule
    public ActivityTestRule<CreateLobbyActivity> spinnerActivityTestRule =
            new ActivityTestRule<>(CreateLobbyActivity.class);

    @Test
    public void spinnerDisplays() throws Exception {
        onView(withId(R.id.spinnerActivities))
                .perform(click());
        onView(isRoot()).perform(idleFor(500));
        onData(allOf(is(instanceOf(String.class)), is("Eat out")))
                .perform(click());
        onView(withId(R.id.spinnerActivities))
                .check(matches(withSpinnerText(containsString("Eat out"))));
    }

    @Test
    public void spinnerDisplays2() throws Exception {
        onView(withId(R.id.spinnerActivities))
                .perform(click());
        onView(isRoot()).perform(idleFor(500));
        onData(allOf(is(instanceOf(String.class)), is("games")))
                .perform(click());
        onView(withId(R.id.spinnerActivities))
                .check(matches(withSpinnerText(containsString("games"))));
    }
}



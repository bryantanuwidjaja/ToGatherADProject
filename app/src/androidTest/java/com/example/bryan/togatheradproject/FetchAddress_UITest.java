package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class FetchAddress_UITest {

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
    public ActivityTestRule<HomeActivity> fetchAddressActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test // Test fetch address button displays address when tapped
    public void passFetchAddressUI() throws Exception {
        onView(withId(R.id.button_HomeActivity_createLobby))
                .perform(click());
//        onView(withId(R.id.button_CreateLobbyActivity_fetch))
//                .perform(click());
        onView(withText(R.string.toast_successFetchAddress)).inRoot(withDecorView(not(
                fetchAddressActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        onView(isRoot()).perform(idleFor(500));
        onView(withId(R.id.textView_CreateLobbyActivity_address))
                .check(matches(not(withText(""))));
    }

    @Test // Create lobby with no address fetched
    public void emptyFetchAddressUI() throws Exception {
        onView(withId(R.id.button_HomeActivity_createLobby))
                .perform(click());
        onView(withId(R.id.spinnerActivities))
                .perform(click());
        onView(isRoot()).perform(idleFor(100));
        onData(allOf(is(instanceOf(String.class)), is("Coffee")))
                .perform(click());
        onView(withId(R.id.editText_CreateLobbyActivity_capacity))
                .perform(typeText("20"));
        onView(withId(R.id.editText_CreateLobbyActivity_description))
                .perform(typeText("Local coffee shop at level 1"));
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .perform(click());
        onView(withText(R.string.toast_emptyFetchAddress)).inRoot(withDecorView(not(
                fetchAddressActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .check(matches(isDisplayed()));
    }
}

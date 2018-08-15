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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class CreateLobby_UITest {

    public void createLobbyUI(String activity, String capacity, String description) {
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .perform(typeText("sukiliong@yahoo.com")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_LoginActivity_insertPassword))
                .perform(typeText("123123")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_LoginActivity_signIn))
                .perform(click());
        onView(isRoot()).perform(idleFor(5000));
        onView(withId(R.id.button_HomeActivity_createLobby))
                .perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.spinnerActivities))
                .perform(click());
        onView(isRoot()).perform(idleFor(200));
        onData(allOf(is(instanceOf(String.class)), is(activity)))
                .perform(click());
        onView(withId(R.id.editText_CreateLobbyActivity_capacity))
                .perform(typeText(capacity)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_CreateLobbyActivity_description))
                .perform(typeText(description)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_CreateLobbyActivity_fetch))
                .perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .perform(click());
        onView(isRoot()).perform(idleFor(1000));
    }

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
    public ActivityTestRule<LoginActivity> createLobbyTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void succesCreateLobby() throws Exception {
        createLobbyUI("Movies", "15", "Lobby Description");
        onView(withId(R.id.textView_LobbyActivity_lobbyID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyCapacityCreateLobby() throws Exception {
        createLobbyUI("Movies", "", "Lobby Description");
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyDescriptionCreateLobby() throws Exception {
        createLobbyUI("Movies", "15", "");
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalidCapacityCreateLobby() throws Exception {
        createLobbyUI("Movies", "p!", "Lobby Description");
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .check(matches(isDisplayed()));
    }


}

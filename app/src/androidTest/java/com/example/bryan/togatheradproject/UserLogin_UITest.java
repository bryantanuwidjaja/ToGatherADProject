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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class UserLogin_UITest {

    public void loginActivityUI(String email, String password) {
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .perform(typeText(email)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_LoginActivity_insertPassword))
                .perform(typeText(password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_LoginActivity_signIn))
                .perform(click());
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
    public ActivityTestRule<LoginActivity> loginActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void successLogin() throws Exception {
        loginActivityUI("sukiliong@yahoo.com", "123123");
        onView(isRoot()).perform(idleFor(6000));
        onView(withId(R.id.button_HomeActivity_createLobby))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalidEmailLogin() throws Exception {
        loginActivityUI("suki@yahoo.com", "123123");
        onView(isRoot()).perform(idleFor(100));
        onView(withText(R.string.toast_loginFailed)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalidPassLogin() throws Exception {
        loginActivityUI("sukiliong@yahoo.com", "password123");
        onView(isRoot()).perform(idleFor(1000));
        onView(withText(R.string.toast_loginFailed)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void emptyEmailLogin() throws Exception {
        loginActivityUI("", "123123");
        onView(isRoot()).perform(idleFor(500));
        onView(withText(R.string.toast_emptyFieldLogin)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

    @Test
    public void emptyPassLogin() throws Exception {
        loginActivityUI("sukiliong@yahoo.com", "");
        onView(isRoot()).perform(idleFor(100));
        onView(withText(R.string.toast_emptyFieldLogin)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalidAllLogin() throws Exception {
        loginActivityUI("sukong@yaho.com", "123");
        onView(isRoot()).perform(idleFor(200));
        onView(withId(R.id.button_LoginActivity_signIn)).check(matches(isDisplayed()));
    }

    @Test
    public void emptyAllLogin() throws Exception {
        loginActivityUI("", "");
        onView(isRoot()).perform(idleFor(100));
        onView(withText(R.string.toast_emptyFieldLogin)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}

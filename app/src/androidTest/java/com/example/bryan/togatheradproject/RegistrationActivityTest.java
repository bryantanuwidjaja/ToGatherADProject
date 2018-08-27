package com.example.bryan.togatheradproject;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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


@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    @Rule
    public ActivityTestRule<LoginActivity> mNotesActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void userRegistrationUI() throws Exception {
        onView(withId(R.id.button_LoginActivity_signUp))
                .perform(click());
        onView(withId(R.id.editText_RegistrationActivity_email))
                .perform(typeText("testing123@gmail.com"));
        onView(withId(R.id.editText_RegistrationActivity_username))
                .perform(typeText("testUser"));
        onView(withId(R.id.editText_RegistrationActivity_password))
                .perform(typeText("Password123"));
        onView(withId(R.id.editText_RegistrationActivity_rePassword))
                .perform(typeText("Password123"))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_RegistrationActivity_create))
                .perform(click());
        onView(isRoot()).perform(waitFor(5000));
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .check(matches(isDisplayed()));
    }
}

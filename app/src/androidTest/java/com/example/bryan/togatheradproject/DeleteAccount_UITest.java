package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

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

public class DeleteAccount_UITest {

    public void registerDummy(String email, String username, String password, String rePassword) {
        onView(withId(R.id.button_LoginActivity_signUp))
                .perform(click());
        onView(withId(R.id.editText_RegistrationActivity_email))
                .perform(typeText(email));
        onView(withId(R.id.editText_RegistrationActivity_username))
                .perform(typeText(username));
        onView(withId(R.id.editText_RegistrationActivity_password))
                .perform(typeText(password));
        onView(withId(R.id.editText_RegistrationActivity_rePassword))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(typeText(rePassword));
        onView(withId(R.id.button_RegistrationActivity_create))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(click());
        onView(isRoot()).perform(idleFor(5000));
        onView(withId(R.id.button_InterestActivity_saveButton)).perform(click());
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
    public void deleteAccount() throws Exception {
        registerDummy("naufaladi10@gmail.com", "naufaladi", "123123", "123123");
        onView(isRoot()).perform(idleFor(2000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(withId(R.id.textView_ProfileScreen_deleteAccount)).perform(click());
        onView(withId(R.id.editText_ConfirmDialog_confirmation)).perform(typeText("CONFIRM"));
        onView(withId(R.id.button_ConfirmDialog_delete)).perform(click());
        onView(withId(R.id.button_LoginActivity_signIn)).check(matches(isDisplayed()));

    }

    @Test
    public void deleteAccountFail() throws Exception {
        registerDummy("naufaladi10@gmail.com", "naufaladi", "123123", "123123");
        onView(isRoot()).perform(idleFor(2000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(withId(R.id.textView_ProfileScreen_deleteAccount)).perform(click());
        onView(withId(R.id.editText_ConfirmDialog_confirmation)).perform(typeText("CFONRIM"));
        onView(withId(R.id.button_ConfirmDialog_delete)).perform(click());
        onView(isRoot()).perform(idleFor(200));
        onView(withId(R.id.button_ConfirmDialog_delete)).check(matches(isDisplayed()));
    }
}

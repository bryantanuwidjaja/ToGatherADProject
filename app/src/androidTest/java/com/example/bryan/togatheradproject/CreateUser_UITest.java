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


@RunWith(AndroidJUnit4.class)
public class CreateUser_UITest {

    public void passUserRegistrationUI(String email, String username, String password, String rePassword) {
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

    @Test // User successfuly created
    public void successUserCreate() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "naufalAdi", "kappa123", "kappa123");
        onView(isRoot()).perform(idleFor(7000));
        onView(withId(R.id.button_HomeActivity_viewProfile))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted invalid password
    public void failInvalidPass() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "naufalAdi", "fa", "fa");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted invalid username
    public void failInvalidName() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "p", "kappa123", "kappa123");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted invalid email
    public void failInvalidEmail() throws Exception {
        passUserRegistrationUI("***)email", "naufalAdi", "kappa123", "kappa123");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted invalid RePassword
    public void failInvalidRePass() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "naufalAdi", "kappa123", "kAppa123");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted empty password
    public void failEmptyPassword() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "naufalAdi", "", "");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted empty username
    public void failEmptyName() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "", "kappa123", "kappa123");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted empty email
    public void failEmptyEmail() throws Exception {
        passUserRegistrationUI("", "naufalAdi", "kappa123", "kappa123");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User inputted empty RePassword
    public void failEmptyRePassword() throws Exception {
        passUserRegistrationUI("naufaladi10@gmail.com", "naufalAdi", "kappa123", "");
        onView(withText(R.string.toast_emptyFieldRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test // User cancels registration
    public void cancelRegistration() throws Exception {
        onView(withId(R.id.button_LoginActivity_signUp))
                .perform(click());
        onView(withId(R.id.button_RegistrationActivity_cancel)).perform(click());
        onView(withText(R.string.toast_cancelRegistration)).inRoot(withDecorView(not(
                loginActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }
}

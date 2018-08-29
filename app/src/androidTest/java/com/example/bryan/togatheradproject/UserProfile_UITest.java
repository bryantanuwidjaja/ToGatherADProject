package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import static org.hamcrest.core.StringContains.containsString;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bryan.togatheradproject.UserLogin_UITest.idleFor;

public class UserProfile_UITest {

    public void changeInterestUI(int interestNum, String interestName) {
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .perform(typeText("sukiliong@yahoo.com")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_LoginActivity_insertPassword))
                .perform(typeText("123123")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_LoginActivity_signIn))
                .perform(click());
        onView(isRoot()).perform(idleFor(5000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(isRoot()).perform(idleFor(200));
        onView(withId(interestNum)).perform(click());
        onView(withId(R.id.editText_FragmentEditProfile_interestEditText))
                .perform(typeText(interestName)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_FragmentEditProfile_saveButton)).perform(click());
        onView(withText(interestName)).check(matches(isDisplayed()));
    }

    @Rule
    public ActivityTestRule<LoginActivity> createLobbyTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void viewProfile() throws Exception {
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .perform(typeText("sukiliong@yahoo.com")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_LoginActivity_insertPassword))
                .perform(typeText("123123")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_LoginActivity_signIn))
                .perform(click());
        onView(isRoot()).perform(idleFor(5000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(isRoot()).perform(idleFor(200));
        onView(withText("bewwww")).check(matches(isDisplayed()));
    }

    @Test
    public void changeInterest1() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest1, "Interest 1");
    }

    @Test
    public void changeInterest2() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest2, "Interest 2");
    }

    @Test
    public void changeInterest3() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest3, "Interest 3");
    }

    @Test
    public void changeInterest4() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest4, "Interest 4");
    }

    @Test
    public void changeInterest5() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest5, "Interest 5");
    }

    @Test
    public void changeInterest6() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest6, "Interest 6");
    }

    @Test
    public void saveChangedInterest() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest1, "Test Interest");
        changeInterestUI(R.id.textView_ProfileScreen_interest2, "bla bla");
        onView(withId(R.id.button_ProfileScreen_saveButton)).perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(withId(R.id.textView_ProfileScreen_interest1)).check(matches(withText("Test Interest")));
        onView(withId(R.id.textView_ProfileScreen_interest2)).check(matches(withText("bla bla")));
    }

    @Test
    public void cancelChangedInterest() throws Exception {
        changeInterestUI(R.id.textView_ProfileScreen_interest1, "Interest 1");
        changeInterestUI(R.id.textView_ProfileScreen_interest2, "Interest 2");
        onView(withId(R.id.button_ProfileScreen_saveButton)).perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        changeInterestUI(R.id.textView_ProfileScreen_interest1, "blabla");
        changeInterestUI(R.id.textView_ProfileScreen_interest2, "test");
        onView(withId(R.id.button_ProfileScreen_cancelButton)).perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.button_HomeActivity_viewProfile)).perform(click());
        onView(withId(R.id.textView_ProfileScreen_interest1)).check(matches(withText("Interest 1")));
        onView(withId(R.id.textView_ProfileScreen_interest2)).check(matches(withText("Interest 1")));


    }

}

package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

public class LobbyActivity_UITest {

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

    public void lobbyActivityUI() {
        onView(withId(R.id.editText_LoginActivity_insertEmail))
                .perform(typeText("sukiliong@yahoo.com")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editText_LoginActivity_insertPassword))
                .perform(typeText("123123")).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_LoginActivity_signIn))
                .perform(click());
        onView(isRoot()).perform(idleFor(7000));
        onView(withId(R.id.listView_HomeActivity_lobbyList)).perform(click());
        onView(isRoot()).perform(idleFor(3000));
    }

    public void chatFunctionTest(String text) {
        lobbyActivityUI();
        onView(withId(R.id.editText_LobbyActivity_chatDialog)).perform(typeText(text));
        onView(withId(R.id.button_LobbyActivity_enter)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.listView_LobbyActivity_chatLog)).perform(swipeUp());
        onData(anything())
                .inAdapterView(withId(R.id.listView_LobbyActivity_chatLog))
                .atPosition(-1)
                .onChildView(withId(R.id.textView_ChatlogListLayout_chatmessage))
                .check(matches(withText(text)));
    }


    @Rule
    public ActivityTestRule<LoginActivity> lobbyActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void checkGuestList() throws Exception {
        lobbyActivityUI();
        onView(withId(R.id.button_LobbyActivity_guestList)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onView(withId(R.id.button_GuestListActivity_returnToLobby)).check(matches(isDisplayed()));
    }

    @Test
    public void chatFunction() throws Exception {
        chatFunctionTest("BlaBlaBla");
    }

    @Test
    public void lobbyDetails() throws Exception {
        lobbyActivityUI();
        onView(withId(R.id.button_LobbyActivity_lobbyDetail)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onView(withId(R.id.button_ActivityLobbyDetail_returnToLobby)).check(matches(isDisplayed()));
    }

    @Test
    public void guestProfile() throws Exception {
        lobbyActivityUI();
        onView(withId(R.id.button_LobbyActivity_guestList)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onData(anything()).inAdapterView(withId(R.id.listView_GuestListActivity_guestList)).atPosition(0).perform(click());
        onView(isRoot()).perform(idleFor(20000));
        onView(withId(R.id.button_GuestProfileActivity_returnToLobby)).perform(closeSoftKeyboard())
                .check(matches(isDisplayed()));
    }

}

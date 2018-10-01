package com.example.bryan.togatheradproject;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bryan.togatheradproject.UserLogin_UITest.idleFor;
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

    public void enterLobby() {
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

    public void createAndEnterLobby() {
        CreateLobby_UITest.createLobbyUI("Movies", "15", "Lobby Description");
        onView(withId(R.id.radioButton_CreateLobbyActivity_public)).perform(click());
        onView(withId(R.id.button_CreateLobbyActivity_create))
                .perform(click());
        onView(isRoot()).perform(idleFor(5000));
    }


    @Rule
    public ActivityTestRule<LoginActivity> lobbyActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);


    @Test
    public void lobbyDetails() throws Exception {
        createAndEnterLobby();
        onView(withId(R.id.button_LobbyActivity_lobbyDetail)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(4000));
        onView(withId(R.id.textView_ActivityLobbyDetail_activity)).check(matches(withText("Movies")));
        onView(withId(R.id.textView_ActivityLobbyDetail_maximumCapacity)).check(matches(withText("15")));
        onView(withId(R.id.textView_ActivityLobbyDetail_description)).check(matches(withText("Lobby Description")));
        onView(withId(R.id.textView_ActivityLobbyDetail_host)).check(matches(withText("suki123")));
        onView(withId(R.id.button_ActivityLobbyDetail_returnToLobby)).check(matches(isDisplayed()));
    }

    @Test
    public void checkGuestList() throws Exception {
        createAndEnterLobby();
        onView(withId(R.id.button_LobbyActivity_guestList)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onView(withId(R.id.button_GuestListActivity_returnToLobby)).check(matches(isDisplayed()));
    }

    @Test
    public void chatFunction() throws Exception {
        createAndEnterLobby();
        onView(withId(R.id.editText_LobbyActivity_chatDialog)).perform(typeText("testing"));
        onView(withId(R.id.button_LobbyActivity_enter)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(1000));
        onView(withId(R.id.listView_LobbyActivity_chatLog)).perform(swipeUp());
        onView(isRoot()).perform(idleFor(1000));
        onView(withText("testing"));
    }


    @Test
    public void guestProfile() throws Exception {
        enterLobby();
        onView(withId(R.id.button_LobbyActivity_guestList)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onData(anything()).inAdapterView(withId(R.id.listView_GuestListActivity_guestList)).atPosition(0).perform(click());
        onView(isRoot()).perform(idleFor(3000));
        onView(withId(R.id.button_GuestProfileActivity_returnToLobby)).perform(closeSoftKeyboard())
                .check(matches(isDisplayed()));
    }

    @Test
    public void guestProfileRate() throws Exception {
        guestProfile();
        onView(withId(R.id.button_GuestProfileActivity_rateUp)).perform(click());
        onView(isRoot()).perform(idleFor(200));
        onView(withText(R.string.toast_rateUp)).inRoot(withDecorView(not(
                lobbyActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void lobbyDetailEdit() throws Exception {
        lobbyDetails();
        onView(withId(R.id.button_ActivityLobbyDetail_editLobby)).perform(click());
        onView(isRoot()).perform(idleFor(2000));
        onView(withId(R.id.editText_EditLobbyActivity_description)).perform(replaceText("description2"));
        onView(withId(R.id.editText_EditLobbyActivity_capacity)).perform(replaceText("10"));
        onView(withId(R.id.radioButton_EditLobbyActivity_privateLobby)).perform(closeSoftKeyboard()).perform(click());
        onView(withId(R.id.button_EditLobbyActivity_save)).perform(closeSoftKeyboard()).perform(click());
        onView(isRoot()).perform(idleFor(2000));
        onView(withId(R.id.button_LobbyActivity_lobbyDetail)).perform(click());
        onView(isRoot()).perform(idleFor(2000));
        onView(withText("10")).check(matches(isDisplayed()));
        onView(withText("description2")).check(matches(isDisplayed()));
    }

    @Test
    public void lobbyPromotion() throws Exception{
        createAndEnterLobby();
        onView(withId(R.id.button_LobbyActivity_promotion)).perform(click());
        onView(withId(R.id.imageView_PromotionLayout_promotionImage)).check(matches(isDisplayed()));
        onView(withId(R.id.textView_PromotionActivity_promotionList)).check(matches(isDisplayed()));
        onView(withId(R.id.button_PromotionActivity_Return)).check(matches(isDisplayed()));

    }



}

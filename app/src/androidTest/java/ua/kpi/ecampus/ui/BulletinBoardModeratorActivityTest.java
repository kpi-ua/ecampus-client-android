package ua.kpi.ecampus.ui;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ua.kpi.ecampus.helper.action.OrientationChangeAction.orientationLandscape;
import static ua.kpi.ecampus.helper.action.OrientationChangeAction.orientationPortrait;
import static ua.kpi.ecampus.helper.matcher.EspressoTestsMatchers.withDrawable;

/**
 * Tests to verify that the behavior of {@link BulletinBoardActivity} is correct.
 * Created by Administrator on 03.03.2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BulletinBoardModeratorActivityTest {

    private final String SHORT_CLASS_NAME_NEW_BULLETIN = ".ui.activity.NewBulletinActivity";

    @Rule
    public IntentsTestRule<BulletinBoardModeratorActivity> mActivityRule = new IntentsTestRule<>(
            BulletinBoardModeratorActivity.class);

    /**
     * Tests that a view pager can be swiped in both directions.
     * Tests that trying to swipe beyond the start of a view pager has no effect.
     * Tests that trying to swipe beyond the end of a view pager has no effect.
     * Tests that swiping across tab displays correct views
     */
    //@Ignore
    @Test
    public void testSwipeLeftRight() {
    }

    /**
     * Tests that toolbar menu has correct view - search
     */
    @Test
    public void testToolbarSearchView() {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()));
    }

    /**
     * Tests that correct intent is sent by clicking on a moderator icon action
     */
    @Test
    public void testSendIntent() {
            onView(withId(R.id.fab_add)).perform(click());
            // Asserts that the given component class name matches intent sent by the application under test.
            intended(hasComponent(hasShortClassName(SHORT_CLASS_NAME_NEW_BULLETIN)));
    }


    /**
     * Test that screen is rotated without exceptions on all tabs.
     */
    @Test
    public void testRotateScreen() {
        // first tab
        onView(withDrawable(R.mipmap.ic_action_all)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        // second tab
        onView(withDrawable(R.mipmap.ic_action_actual)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        //third tab
        onView(withDrawable(R.mipmap.ic_action_by_profile)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        //fourth tab
        onView(withDrawable(R.mipmap.ic_action_by_susdivision)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        //fifth tab
        onView(withDrawable(R.mipmap.ic_action_action_delete)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
    }
}

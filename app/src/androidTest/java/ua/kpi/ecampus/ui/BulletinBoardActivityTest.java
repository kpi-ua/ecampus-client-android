package ua.kpi.ecampus.ui;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.helper.Sleep;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
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
import static org.hamcrest.Matchers.allOf;

/**
 * Tests to verify that the behavior of {@link BulletinBoardActivity} is correct.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BulletinBoardActivityTest {

    private final String SHORT_CLASS_NAME_BULLETIN_MODERATOR = ".ui.activity.BulletinBoardModeratorActivity";

    private final String SHORT_CLASS_NAME_BULLETIN_CONTENT = ".ui.activity.BulletinContentActivity";

    @Rule
    public IntentsTestRule<BulletinBoardActivity> mActivityRule = new IntentsTestRule<>(
            BulletinBoardActivity.class);

    /**
     * Tests that a view pager can be swiped in both directions.
     * Tests that trying to swipe beyond the start of a view pager has no effect.
     * Tests that trying to swipe beyond the end of a view pager has no effect.
     * Tests that swiping across tab displays correct views
     */
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
     * Tests that toolbar menu has moderator icon, if user can go to {@link BulletinBoardModeratorActivity}
     * Tests that toolbar menu hasn't moderator icon, if user can't go to aforementioned activity.
     */
    @Test
    public void testToolbarModeratorView() {
        if (userIsModerator()) {
            onView(withId(R.id.action_moderator)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.action_moderator)).check(doesNotExist());
        }
    }

    /**
     * Tests that correct intent is sent by clicking on a moderator icon action
     */
    @Test
    public void testSendIntent() {
        if (userIsModerator()) {
            onView(withId(R.id.action_moderator)).perform(click());

            // Asserts that the given component class name matches intent sent by the application under test.
            intended(hasComponent(hasShortClassName(SHORT_CLASS_NAME_BULLETIN_MODERATOR)));
        }
    }

    private boolean userIsModerator() {
        return mActivityRule.getActivity().userIsModerator();
    }

    /**
     * Test that screen is rotated without exceptions on all tabs.
     */
    @Test
    public void testRotateScreen() {
        // first tab
        onView(withDrawable(R.mipmap.ic_action_actual)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        Sleep.sleepThread();
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        // second tab
        onView(withDrawable(R.mipmap.ic_action_by_profile)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
        Sleep.sleepThread();
        //onView(withId(R.id.view_pager)).perform(swipeLeft());

        //third tab
        onView(withDrawable(R.mipmap.ic_action_by_susdivision)).check(matches(isDisplayed()));
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
    }

    /**
     * Tests that correct intent is sent by clicking on a list item
    */
    @Ignore
    @Test
    public void testSendIntentContent() {
        onData(allOf(withId(android.R.id.list), isDisplayed())).inAdapterView(withId(android.R.id.list)).atPosition(0).perform(click());
        intended(hasComponent(hasShortClassName(SHORT_CLASS_NAME_BULLETIN_CONTENT)));
    }


    /**
     * Tests that clicking on navigate up button shows parent activity
     * @throws PerformException if currently displayed activity is root activity, since pressing back
     *         button would result in application closing.
     */
//    @Test
//    public void testBackButtonClick_ShowParentActivity() {
//        onView(isRoot()).perform(pressBack());
//    }
}

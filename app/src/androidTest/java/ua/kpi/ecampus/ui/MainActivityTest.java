package ua.kpi.ecampus.ui;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.ui.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ua.kpi.ecampus.helper.action.OrientationChangeAction.orientationLandscape;
import static ua.kpi.ecampus.helper.action.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Tests to verify that the behavior of {@link MainActivity} is correct.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private final int FIRST = 0;
    private final String SHORT_CLASS_NAME_BULLETIN = ".ui.activity.BulletinBoardActivity";

    @Rule
    public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(
            MainActivity.class);

    /**
     * Test that items exist in list.
     */
    @Test
    public void firstItem_Displayed() {
        onView(withText(R.string.bulletin)).check(matches(isDisplayed()));
    }

    /**
     * Test that click on first item in gridview
     */
    @Test
    public void firstItem_Click() {
        onData(allOf(is(instanceOf(Subsystem.class)))).inAdapterView(withId(R.id.grid_view_subsystem)).atPosition(FIRST).perform(click());
    }

    /**
     * Tests that correct intent is sent by clicking on a first item in gridview
     */
    @Test
    public void testSendIntent() {
        onData(allOf(is(instanceOf(Subsystem.class)))).inAdapterView(withId(R.id.grid_view_subsystem)).atPosition(FIRST).perform(click());
        // Asserts that the given component class name matches intent sent by the application under test.
        intended(hasComponent(hasShortClassName(SHORT_CLASS_NAME_BULLETIN)));
    }

    /**
     * Test that screen is rotated without exceptions.
     */
    @Test
    public void testRotateScreen() {
        onView(isRoot()).perform(orientationLandscape());
        onView(isRoot()).perform(orientationPortrait());
    }

}

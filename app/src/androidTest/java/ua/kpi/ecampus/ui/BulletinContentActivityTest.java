package ua.kpi.ecampus.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.ui.activity.BulletinContentActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BulletinContentActivityTest {

    @Rule
    public ActivityTestRule<BulletinContentActivity> mActivityRule = new ActivityTestRule<>(
            BulletinContentActivity.class);

    @Test
    public void testShowView() {
        onView(withId(R.id.text_view_bulletin_theme)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_bulletin_text)).check(matches(isDisplayed()));
        onView(withId(R.id.bulletin_period)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_bulletin_start_date)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_bulletin_end_date)).check(matches(isDisplayed()));
        onView(withId(R.id.bulletin_author)).check(matches(isDisplayed()));
        onView(withId(R.id.text_view_bulletin_author)).check(matches(isDisplayed()));
    }


}

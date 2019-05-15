package ua.kpi.ecampus.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import ua.kpi.ecampus.ui.activity.VoteRateTeacherActivity;
import ua.kpi.ecampus.ui.activity.VoteStudentActivity;
import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.di.ActivityContext;
import ua.kpi.ecampus.model.pojo.Bulletin;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.activity.AddBulletinActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;
import ua.kpi.ecampus.ui.activity.BulletinContentActivity;
import ua.kpi.ecampus.ui.activity.EditBulletinActivity;
import ua.kpi.ecampus.ui.activity.LoginActivity;
import ua.kpi.ecampus.ui.activity.MainActivity;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 */
public class Navigator {

    private Activity mActivityContext;

    @Inject
    public Navigator(@ActivityContext Context context) {
        mActivityContext = (Activity) context;
    }

    public void startLoginActivity() {
        Intent intent = getLaunchIntent(LoginActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = getLaunchIntent(MainActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startBulletinBoardActivity() {
        Intent intent = getLaunchIntent(BulletinBoardActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startBulletinContentActivity(Bulletin item) {
        Intent intent = getLaunchIntent(BulletinContentActivity.class);
        intent.putExtra(Config.KEY_BULLETIN, item);
        mActivityContext.startActivity(intent);
    }

    public void startNewBulletinActivity() {
        Intent intent = getLaunchIntent(AddBulletinActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startEditBulletinActivity(Bulletin item) {
        Intent intent = getLaunchIntent(EditBulletinActivity.class);
        intent.putExtra(Config.KEY_BULLETIN, item);
        mActivityContext.startActivity(intent);
    }

    public void startVoteStudentActivity() {
        Intent intent = getLaunchIntent(VoteStudentActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startVoteRatingActivity(VoteTeacher item) {
        Intent intent = getLaunchIntent(VoteRateTeacherActivity.class);
        intent.putExtra(Config.KEY_TEACHER, item);
        mActivityContext.startActivityForResult(intent, Config.REQUEST_CODE);
    }

    /**
     * Generates the intent needed by the client code to launch this activity.
     */
    private Intent getLaunchIntent(Class activityClass) {
        return new Intent(mActivityContext, activityClass);
    }

    public void startBulletinBoardModeratorActivity() {
        Intent intent = getLaunchIntent(BulletinBoardModeratorActivity.class);
        mActivityContext.startActivity(intent);
    }
}

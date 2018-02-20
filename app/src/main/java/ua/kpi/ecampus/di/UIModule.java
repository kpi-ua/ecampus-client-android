package ua.kpi.ecampus.di;

import ua.kpi.ecampus.ui.activity.AddBulletinActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardActivity;
import ua.kpi.ecampus.ui.activity.BulletinBoardModeratorActivity;
import ua.kpi.ecampus.ui.activity.BulletinContentActivity;
import ua.kpi.ecampus.ui.activity.EditBulletinActivity;
import ua.kpi.ecampus.ui.activity.LoginActivity;
import ua.kpi.ecampus.ui.activity.MainActivity;
import ua.kpi.ecampus.ui.activity.MainNotAuthActivity;

import dagger.Module;
import ua.kpi.ecampus.ui.activity.VoteRateTeacherActivity;
import ua.kpi.ecampus.ui.activity.VoteStudentActivity;
import ua.kpi.ecampus.ui.fragment.VoteCurrentFragment;

/**
 * Dagger module created to provide UI dependencies like presenters.
 */
@Module(complete = false,
        injects = {
                MainNotAuthActivity.class,
                LoginActivity.class,
                MainActivity.class,
                BulletinBoardActivity.class,
                BulletinContentActivity.class,
                AddBulletinActivity.class,
                BulletinBoardModeratorActivity.class,
                EditBulletinActivity.class,
                VoteStudentActivity.class,
                VoteRateTeacherActivity.class,
                VoteCurrentFragment.class
        })

public final class UIModule {
}

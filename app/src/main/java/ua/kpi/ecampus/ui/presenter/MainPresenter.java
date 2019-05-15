package ua.kpi.ecampus.ui.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.Preference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ua.kpi.ecampus.model.Subsystem;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.Preference;
import ua.kpi.ecampus.util.BulletinPredicates;

/**
 * MainPresenter created to manage MainActivity.
 */
public class MainPresenter extends BasePresenter {

    private IView mView;
    private Context mContext;
    private Navigator mNavigator;
    private Preference mPreference;
    private boolean mIsUserLogged = false;

    @Inject
    public MainPresenter(Context context, Navigator navigator, Preference
            preference) {
        mContext = context;
        mNavigator = navigator;
        mPreference = preference;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public void startActivityBasedOn(int position) {
        switch (position) {
            case 0:
                mNavigator.startBulletinBoardActivity();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                if(userIsStudent())
                    mNavigator.startVoteStudentActivity();
                break;
        }
    }

    /**
     * True if user's position is student
     * False means user is teacher
     * @return
     */
    private boolean userIsStudent() {
        final Integer studentId = 5;
        List<Integer> ids = BulletinPredicates.getIdsCollection
                (User.getInstance().position);
        return ids.contains(studentId);
    }

    /**
     * User logout
     */
    public void logout() {
        mPreference.saveLogoutInfo();

        User.getInstance().token = "";
        mNavigator.startLoginActivity();
    }

    /**
     * Get list of ua.kpi.ecampus subsystems.
     * @return subsystems
     */
    public List<Subsystem> getData() {
        List<Subsystem> subsystems = new ArrayList<>();
        Resources res = getResources();
        String[] names = getSubsystemNames(res);
        TypedArray icons = getSubsystemIcon(res);
        for (int i = 0; i < names.length; i++) {
            Subsystem s = new Subsystem(names[i], icons.getResourceId(i, -1));
            subsystems.add(s);
        }
        return subsystems;
    }

    /**
     * Check if user logged or not.
     * If user if logged and auth token is not expired, start MainActivity.
     * If not, redirect user to LoginActivity.
     */
    public void checkUserIsLogged() {
        Date expDate = mPreference.getTokenExpirationDate();
        Date currentDate = new Date();
        mIsUserLogged = mPreference.getIsLogged();
        if (mIsUserLogged && !currentDate.after(expDate))
            getUserSessionValues();
        else
            mNavigator.startLoginActivity();
    }

    private String[] getSubsystemNames(Resources res) {
        return res.getStringArray(R.array.full_subsystem);
    }

    private TypedArray getSubsystemIcon(Resources res) {
        return res.obtainTypedArray(R.array.full_subsystem_image);
    }

    private Resources getResources() {
        return mContext.getResources();
    }

    private void getUserSessionValues() {
        User user = User.getInstance();
        user.id = mPreference.getUserId();
        user.name = mPreference.getUserName();
        user.position = mPreference.getUserPositions();
        user.subdivision = mPreference.getUserSubdivisions();
        user.isBulletinBoardModerator = mPreference.getIsUserBbModerator();
        user.token = mPreference.getToken();
    }

    public interface IView {
        void setViewComponent();
    }
}

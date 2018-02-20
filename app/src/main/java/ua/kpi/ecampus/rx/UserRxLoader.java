package ua.kpi.ecampus.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ua.kpi.ecampus.api.service.ServiceCreator;
import ua.kpi.ecampus.api.service.UserService;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.ui.Preference;

/**
 * Perform requests of the UserService class.
 */
public class UserRxLoader {

    private Preference mPreference;

    public  UserRxLoader(Preference preference) {
        mPreference = preference;
    }

    /**
     * Implements the request to the api function.
     */
    public void apiCall() {
        UserService service = ServiceCreator.createService(UserService.class);
        Observable<User> observable = service.getUser("bearer " + User.getInstance().token);

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNextAction, onErrorAction);
    }

    Action1<User> onNextAction = new Action1<User>() {

        @Override
        public void call(User userInfo) {
            User user = User.getInstance();
            user.id = userInfo.id;
            user.name = userInfo.name;
            user.position = userInfo.position;
            user.subdivision = userInfo.subdivision;
            user.descendantSubdivisions = userInfo.descendantSubdivisions;
            user.isBulletinBoardModerator = userInfo.isBulletinBoardModerator;

            mPreference.saveUserInfo(user);

            //Log.d(Config.LOG, "Successful download of information about the User ".concat(user.name));
        }
    };

    Action1<Throwable> onErrorAction = e -> {
        //Log.e(Config.LOG, e.getMessage());
    };
}

package ua.kpi.campus.ui.presenter;

import android.os.Bundle;

import ua.kpi.campus.Config;
import ua.kpi.campus.api.response.BaseResponse;
import ua.kpi.campus.api.response.RequestResult;
import ua.kpi.campus.model.pojo.Token;
import ua.kpi.campus.model.pojo.User;
import ua.kpi.campus.rx.UserRxLoader;
import ua.kpi.campus.ui.Navigator;
import ua.kpi.campus.ui.Preference;

import javax.inject.Inject;

/**
 * LoginPresenter created to manage LoginActivity.
 * <p>
 * Created by Administrator on 29.01.2016.
 */
public class LoginPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;
    private Preference mPreference;


    @Inject
    public LoginPresenter(Navigator navigator, Preference preference) {
        mNavigator = navigator;
        mPreference = preference;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
    }

    /**
     * Set necessary View components to "login state"
     *
     * @param login    login which is entered by the user
     * @param password password which is entered by the user
     */
    public void login(String login, String password) {
        mView.showLoginProgressDialog();
        mView.activateLoginButton(false);
        validateUser(login, password);
    }

    /**
     * Set views to "initial (after login) state".
     * If server returns success, start MainActivity.
     * If not, launch login failed logic.
     *
     * @param baseResponse response from loader
     */
    public void setLoaderResult(BaseResponse baseResponse) {
        mView.dismissProgressDialog();
        mView.activateLoginButton(true);

        Token answer = baseResponse.getTypedAnswer();
        if (answer != null) {
            onLoginSuccess(answer);
        } else {
            mView.onLoginFailed(baseResponse.getRequestResult());
        }
    }

    /**
     * Save token to User instance for short storing and to the preferences
     * for long storing
     *
     * @param token
     */
    private void saveToken(Token token) {
        User.getInstance().token = token.getAccessToken();
        saveStateToPref(token);
    }

    /**
     * Init loader to load data for the user authentication.
     *
     * @param login
     * @param password
     */
    private void validateUser(String login, String password) {
        Bundle args = new Bundle();
        args.putString(Config.KEY_LOGIN, login);
        args.putString(Config.KEY_PASSWORD, password);
        mView.initLoader(args);
    }


    private void onLoginSuccess(Token token) {
        saveToken(token);
        loadInfoAboutUser();
        mNavigator.startMainActivity();
    }

    /**
     * Save login values to SharedPreferences.
     */
    private void saveStateToPref(Token token) {
        mPreference.saveLoginInfo(token);
    }

    private void loadInfoAboutUser() {
        UserRxLoader loader = new UserRxLoader(mPreference);
        loader.apiCall();
    }

    public interface IView {
        void showLoginProgressDialog();

        void dismissProgressDialog();

        void activateLoginButton(boolean shouldShow);

        void onLoginFailed(RequestResult result);

        void initLoader(Bundle args);
    }
}
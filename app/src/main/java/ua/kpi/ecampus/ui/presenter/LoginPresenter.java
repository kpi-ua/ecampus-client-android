package ua.kpi.ecampus.ui.presenter;

import android.os.Bundle;

import javax.inject.Inject;

import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.api.response.BaseResponse;
import ua.kpi.ecampus.model.pojo.Token;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.rx.UserRxLoader;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.Preference;

import javax.inject.Inject;

/**
 * LoginPresenter created to manage LoginActivity.
 */
public class LoginPresenter extends BasePresenter {

    private IView mView;
    private Navigator mNavigator;
    private Preference mPreference;
    private UserRxLoader mLoader;

    @Inject
    public LoginPresenter(Navigator navigator, Preference preference) {
        mNavigator = navigator;
        mPreference = preference;
        mLoader = new UserRxLoader(preference);
    }

    public void setView(IView view) {
        mView = view;
    }

    public void setLoader(UserRxLoader loader) {
        mLoader = loader;
    }

    @Override
    public void initializeViewComponent() {
    }

    /**
     * Set necessary View components to "login state"
     */
    public void onStartLogin() {
        mView.showLoginProgressDialog();
        mView.activateLoginButton(false);
    }

    /**
     * Set views to "initial (after login) state".
     */
    public void onFinishLogin() {
        mView.dismissProgressDialog();
        mView.activateLoginButton(true);
    }

    /**
     * If server returns success, start MainActivity.
     * If not, launch login failed logic.
     *
     * @param baseResponse response from loader
     */
    public void setLoaderResult(BaseResponse baseResponse) {
        Token answer = baseResponse.getTypedAnswer();
        if (answer != null) {
            onLoginSuccess(answer);
        } else {
            mView.onLoginFailed(baseResponse);
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
     * @param login login which is entered by the user
     * @param password password which is entered by the user
     */
    public void initRequest(String login, String password) {
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
        mLoader.apiCall();
    }

    public interface IView {
        void showLoginProgressDialog();

        void dismissProgressDialog();

        void activateLoginButton(boolean shouldShow);

        void onLoginFailed(BaseResponse response);

        void initLoader(Bundle args);
    }
}

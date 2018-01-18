package ua.kpi.ecampus.ui.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.api.response.BaseResponse;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.loader.TokenLoader;
import ua.kpi.ecampus.ui.presenter.LoginPresenter;
import ua.kpi.ecampus.util.ToastUtil;

/**
 * Login activity.
 */
public class LoginActivity extends BaseActivity implements LoginPresenter
        .IView, LoaderManager.LoaderCallbacks<BaseResponse> {

    @Bind(R.id.edit_text_login)
    EditText mLogin;
    @Bind(R.id.edit_text_password)
    EditText mPassword;
    @Bind(R.id.button_login)
    Button mBtnLogin;
    @Inject
    LoginPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        mPresenter.setView(this);
        mPresenter.initializeViewComponent();
    }

    @Override
    protected List<Object> getModules() {
        LinkedList<Object> modules = new LinkedList<>();
        modules.add(new UIModule());
        return modules;
    }

    @Override
    public void showLoginProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style
                .AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.progress_authenticate));
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void activateLoginButton(boolean shouldShow) {
        mBtnLogin.setActivated(shouldShow);
    }

    @Override
    public void onLoginFailed(BaseResponse response) {
        switch (response.getRequestResult()) {
            case OK:
                int code = response.getStatusCode();
                if (code == 200)
                    ToastUtil.showShortMessage(getString(R.string.invalid_credentials),
                            getApplicationContext());
                else if (code == 500)
                    ToastUtil.showShortMessage(getString(R.string.server_error),
                            getApplicationContext());
                break;
            case ERROR:
                ToastUtil.showShortMessage(getString(R.string.no_internet),                                             getApplicationContext());
                break;
        }

    }

    @Override
    public void initLoader(Bundle args) {
        getLoaderManager().initLoader(R.id.api_loader, args, this);
    }

    @OnClick(R.id.button_login)
    public void login() {
        String userLogin = mLogin.getText().toString();
        String userPassword = mPassword.getText().toString();
        boolean isValid = validateInput(userLogin, userPassword);
        if (isValid) {
            mPresenter.onStartLogin();
            mPresenter.initRequest(userLogin, userPassword);
        }
    }

    @Override
    public Loader<BaseResponse> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case R.id.api_loader:
                String login = bundle.getString(Config.KEY_LOGIN);
                String pass = bundle.getString(Config.KEY_PASSWORD);
                return new TokenLoader(getApplicationContext(), login, pass);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<BaseResponse> loader, BaseResponse
            baseResponse) {
        int id = loader.getId();
        if (id == R.id.api_loader) {
            mPresenter.onFinishLogin();
            mPresenter.setLoaderResult(baseResponse);
        }
        getLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(Loader<BaseResponse> loader) {

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    /**
     * Validate user input credentials.
     *
     * @param inputLogin
     * @param inputPassword
     * @return true if credentials are not empty, false otherwise.
     */
    private boolean validateInput(String inputLogin, String inputPassword) {
        boolean isValid = true;
        TextInputLayout inputLayout = findViewById(R.id.input_login);
        if (inputLogin.isEmpty()) {
            setErrorInput(inputLayout, getString(R.string.login_is_required));
            isValid = false;
        } else {
            setErrorInput(inputLayout, null);
        }
        inputLayout = findViewById(R.id.input_password);
        if (inputLogin.isEmpty()) {
            setErrorInput(inputLayout, getString(R.string.password_is_required));
            isValid = false;
        } else {
            setErrorInput(inputLayout, null);
        }
        return isValid;
    }

    /**
     * Handle user input error.
     *
     * @param inputLayout TextInputLayout view
     * @param errorMsg    error message to show
     */
    private void setErrorInput(TextInputLayout inputLayout, String errorMsg) {
        inputLayout.setError(errorMsg);
    }
}

package ua.kpi.ecampus.ui.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kpi.ecampus.api.response.BaseResponse;
import ua.kpi.ecampus.api.response.RequestResult;
import ua.kpi.ecampus.model.pojo.Token;
import ua.kpi.ecampus.rx.UserRxLoader;
import ua.kpi.ecampus.ui.Navigator;
import ua.kpi.ecampus.ui.Preference;
import ua.kpi.ecampus.ui.activity.LoginActivity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    private LoginPresenter mPresenter;
    @Mock
    private LoginPresenter.IView mView;
    @Mock
    private Navigator mNavigator;
    @Mock
    private Preference mPreference;
    @Mock
    private UserRxLoader mLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenter(mNavigator, mPreference);
        mView = mock(LoginActivity.class);
        mPresenter.setView(mView);
        mPresenter.setLoader(mLoader);
    }

    @Test
    public void onStartLogin() {
        mPresenter.onStartLogin();
        verify(mView).showLoginProgressDialog();
        verify(mView).activateLoginButton(false);
    }

    @Test
    public void onFinishLogin() {
        mPresenter.onFinishLogin();
        verify(mView).dismissProgressDialog();
        verify(mView).activateLoginButton(true);
    }

    @Test
    public void setLoaderResult_Error() {
        BaseResponse response = getErrorResponse();
        mPresenter.setLoaderResult(response);
        verify(mView).onLoginFailed(response);
    }

    @Test
    public void setLoaderResult_Ok() {
        BaseResponse response = getOkResponse();
        mPresenter.setLoaderResult(response);
        verify(mPreference).saveLoginInfo(response.getTypedAnswer());
        verify(mLoader).apiCall();
        verify(mNavigator).startMainActivity();
    }

    private BaseResponse getErrorResponse() {
        return new BaseResponse();
    }

    private BaseResponse getOkResponse() {
        BaseResponse response = new BaseResponse();
        response.setRequestResult(RequestResult.OK);
        response.setAnswer(new Token("valid_token"));
        return response;
    }
}

package ua.kpi.ecampus.loader;

import android.content.Context;

import ua.kpi.ecampus.api.response.BaseResponse;
import ua.kpi.ecampus.api.response.RequestResult;
import ua.kpi.ecampus.api.response.TokenResponse;
import ua.kpi.ecampus.api.service.AuthService;
import ua.kpi.ecampus.api.service.ServiceCreator;
import ua.kpi.ecampus.model.pojo.Token;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Loads Token data.
 * <p>
 * Created by Administrator on 09.02.2016.
 */
public class TokenLoader extends BaseLoader {

    private final String mUsername;
    private final String mPassword;
    private final String mGrantType = "password";

    public TokenLoader(Context context, String username, String pass) {
        super(context);
        mUsername = username;
        mPassword = pass;
    }

    @Override
    protected BaseResponse apiCall() throws IOException {
        AuthService service = ServiceCreator.createService(AuthService.class);

        Call<Token> call = service.auth(mUsername, mPassword, mGrantType);
        Response<Token> resp = call.execute();
        Token token = resp.body();

        return new TokenResponse()
                .setRequestResult(RequestResult.OK)
                .setStatusCode(resp.code())
                .setAnswer(token);
    }
}

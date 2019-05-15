package ua.kpi.ecampus.api.response;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Represent a single data type for using it as response in loaders.
 * This type can persists a query result. If its necessary to do something
 * for a particular query, the new class must be inherited from this class
 * and override / add  methods.
 */
public class BaseResponse {

    @Nullable
    private Object mAnswer;

    private RequestResult mRequestResult;

    private int mStatusCode;

    public BaseResponse() {
        mRequestResult = RequestResult.ERROR;
    }

    @NonNull
    public RequestResult getRequestResult() {
        return mRequestResult;
    }

    public BaseResponse setRequestResult(RequestResult requestResult) {
        mRequestResult = requestResult;
        return this;
    }

    @Nullable
    public <T> T getTypedAnswer() {
        if (mAnswer == null) {
            return null;
        }
        //noinspection unchecked
        return (T) mAnswer;
    }

    public BaseResponse setAnswer(@Nullable Object answer) {
        mAnswer = answer;
        return this;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public BaseResponse setStatusCode(int code) {
        mStatusCode = code;
        return this;
    }
    
    public void save(Context context) {
    }
}

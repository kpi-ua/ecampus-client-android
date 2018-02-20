package ua.kpi.ecampus.model.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Token entity.
 * POJO class for convenient GSON serialization.
 */
public class Token {

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("expires_in")
    private int expiresIn;

    public Token() {
    }

    public Token(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }
}

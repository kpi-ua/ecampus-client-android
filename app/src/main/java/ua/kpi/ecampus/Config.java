package ua.kpi.ecampus;

public class Config {

    public static final String LOG = "campus.kpi.ua";
    /**
     * Keys for intent
     */
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_BULLETIN = "key_bulletin";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_TEACHER = "key_teacher";
    /**
     * Keys for SharedPreferences
     * Name of our shared preferences
     */
    public static final String SHARED_PREF_NAME = "campusapp";
    /**
     * Used to store the token of current logged in user
     */
    public static final String TOKEN_SHARED_PREF = "token";
    /**
     * Used to store the token expiration time
     */
    public static final String EXPIRES_IN_SHARED_PREF = "token_expires_in";
    /**
     * Stores the boolean in SharedPreferences to track user is logged in or not
     */
    public static final String IS_LOGGED_SHARED_PREF = "islogged";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String USER_POSITION = "user_position";

    public static final String USER_SUBDIVISION = "user_subdivision";

    public static final String USER_IS_BB_MODERATOR = "user_is_moderator";
    /**
     * Request code for startActivityForResult method
     */
    public static final int REQUEST_CODE  = 1;

}
package com.braguia;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private String USER_TOKEN = null;
    private String SESSION_ID = null;

    public SessionManager(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAuthToken(String token, String sessionId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.putString(SESSION_ID, sessionId);
        editor.apply();
    }


    public String fetchAuthToken() {
        return prefs.getString(USER_TOKEN, null);
    }

    public String fetchSessionId() {
        return prefs.getString(SESSION_ID, null);
    }


}

package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;


/**
 * Created by Peter Staranchuk.
 */

public class DataStoreHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public DataStoreHelper(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_prefs_userinfo), Context.MODE_PRIVATE);
    }

    public void storeUserName(String userName) {
        sharedPreferences.edit()
                .putString(context.getString(R.string.shared_prefs_key_username), userName)
                .apply();
    }

    public void storeUserId(String userId) {
        sharedPreferences.edit()
                .putString(context.getString(R.string.shared_prefs_key_userid), userId)
                .apply();
    }

    public void clearUserData() {
        storeUserId("");
        storeUserName("");
    }

    @NonNull
    public String getUserName() {
        return sharedPreferences.getString(context.getString(R.string.shared_prefs_key_username), "");
    }

    @NonNull
    public String getUserId() {
        return sharedPreferences.getString(context.getString(R.string.shared_prefs_key_userid), "");
    }
}

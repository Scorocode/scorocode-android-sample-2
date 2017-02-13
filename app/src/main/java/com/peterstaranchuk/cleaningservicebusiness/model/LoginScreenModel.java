package com.peterstaranchuk.cleaningservicebusiness.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.helpers.DataStoreHelper;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.Responses.user.ResponseLogin;
import ru.profit_group.scorocode_sdk.scorocode_objects.User;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenModel {

    private Context context;

    public LoginScreenModel(Context context) {
        this.context = context;
    }

    public boolean isDataValid(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            return true;
        }

        return false;
    }

    public void loginUser(String email, String password, CallbackLoginUser callbackLoginUser) {
        User user = new User();
        user.login(email, password, callbackLoginUser);
    }

    public void clearUserData() {
        new DataStoreHelper(context).clearUserData();
    }

    public void storeUserData(ResponseLogin responseLogin) {
        DataStoreHelper dataStoreHelper = new DataStoreHelper(context);
        dataStoreHelper.storeUserInfo(responseLogin.getResult().getUserInfo());
    }

    public boolean isEmployee(ResponseLogin responseLogin) {
        return (Boolean) responseLogin.getResult().getUserInfo().get(context.getString(R.string.isEmployeeField));
    }

    @NonNull
    private String getUserId(ResponseLogin responseLogin) {
        String userId = String.valueOf(responseLogin.getResult().getUserInfo().getId());
        return userId != null? userId : "";
    }

    @NonNull
    private String getUserName(ResponseLogin responseLogin) {
        String userName = String.valueOf(responseLogin.getResult().getUserInfo().get(context.getString(R.string.fieldUsername)));
        return userName != null? userName : "";
    }

}

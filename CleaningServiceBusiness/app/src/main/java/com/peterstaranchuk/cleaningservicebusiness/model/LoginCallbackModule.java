package com.peterstaranchuk.cleaningservicebusiness.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.activities.MainActivity;
import com.peterstaranchuk.cleaningservicebusiness.helpers.DataStoreHelper;

import dagger.Module;
import dagger.Provides;
import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.Responses.user.ResponseLogin;

/**
 * Created by Peter Staranchuk.
 */

@Module
public class LoginCallbackModule {

    private Context context;

    public LoginCallbackModule(Context context) {
        this.context = context;
    }

    @Provides
    CallbackLoginUser callbackLoginUser() {
        return new CallbackLoginUser() {
            @Override
            public void onLoginSucceed(ResponseLogin responseLogin) {
                //if user account exist in server (inside users collection)
                //when login will be successful

                DataStoreHelper dataStoreHelper = new DataStoreHelper(context);
                dataStoreHelper.storeUserName(getUserName(responseLogin));
                dataStoreHelper.storeUserId(getUserId(responseLogin));

                MainActivity.display(context);
            }

            @Override
            public void onLoginFailed(String errorCode, String errorMessage) {
                //if login failed you can handle this situation. You can also see the reason
                //why login operation was failed
                Toast.makeText(context, context.getString(R.string.cant_login) + "\n" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        };
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

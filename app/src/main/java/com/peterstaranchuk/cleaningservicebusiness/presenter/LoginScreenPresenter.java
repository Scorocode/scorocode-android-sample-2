package com.peterstaranchuk.cleaningservicebusiness.presenter;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.view.LoginActivityView;

import ru.profit_group.scorocode_sdk.Callbacks.CallbackLoginUser;
import ru.profit_group.scorocode_sdk.Responses.user.ResponseLogin;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenPresenter {
    private LoginActivityView view;
    private LoginScreenModel model;

    public LoginScreenPresenter(LoginActivityView view, LoginScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onLoginButtonClicked() {
        String email = view.getEmail();
        String password = view.getPassword();

        CallbackLoginUser callbackLoginUser = new CallbackLoginUser() {
            @Override
            public void onLoginSucceed(ResponseLogin responseLogin) {
                //if user account exist in server (inside users collection)
                //when login will be successful

                if(model.isEmployee(responseLogin)) {
                    model.storeUserData(responseLogin);
                    view.displayMainActivity();
                } else {
                    view.showError(R.string.only_employees);
                }
            }

            @Override
            public void onLoginFailed(String errorCode, String errorMessage) {
                //if login failed you can handle this situation. You can also see the reason
                //why login operation was failed
                view.showError(R.string.cant_login);
            }
        };

        if(model.isDataValid(email, password)) {
            model.loginUser(email, password, callbackLoginUser);
        } else {
            view.showError(R.string.wrong_data_error);
        }
    }

    public void onCreateScreen() {
        model.clearUserData();
        view.disableLoginButton();
        view.setDataListeners();
        view.setItemsVisibility();
        view.setActionBar();
    }

    public Action1<CharSequence> getDataCheckCallback() {
        return new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if(!view.getEmail().isEmpty() && !view.getPassword().isEmpty()) {
                    view.enableLoginButton();
                } else {
                    view.disableLoginButton();
                }
            }
        };
    }
}

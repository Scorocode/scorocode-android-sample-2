package com.peterstaranchuk.cleaningservicebusiness.presenter;

import com.peterstaranchuk.cleaningservicebusiness.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.view.LoginScreenView;

import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class LoginScreenPresenter {
    private LoginScreenView view;
    private LoginScreenModel model;

    public LoginScreenPresenter(LoginScreenView view, LoginScreenModel model) {
        this.view = view;
        this.model = model;
    }

    public void onLoginButtonClicked() {
        String email = view.getEmail();
        String password = view.getPassword();

        if(model.isDataValid(email, password)) {
            model.loginUser(email, password);
        } else {
            model.handleError();
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

package com.peterstaranchuk.cleaningservicebusiness.dagger2modules;


import com.peterstaranchuk.cleaningservicebusiness.activities.LoginActivity;
import com.peterstaranchuk.cleaningservicebusiness.model.LoginScreenModel;
import com.peterstaranchuk.cleaningservicebusiness.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.LoginScreenView;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

@Module
public class LoginScreenModule {
    private LoginScreenView view;

    public LoginScreenModule(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    @Provides
    Action1<CharSequence> dataCheckCallback(LoginScreenPresenter presenter) {
        return presenter.getDataCheckCallback();
    }

    @Provides
    LoginScreenPresenter presenter() {
        return new LoginScreenPresenter(view, new LoginScreenModel(view.getContext()));
    }

}

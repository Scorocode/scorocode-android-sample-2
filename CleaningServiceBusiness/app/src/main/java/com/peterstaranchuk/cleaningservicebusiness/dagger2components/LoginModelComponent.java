package com.peterstaranchuk.cleaningservicebusiness.dagger2components;

import com.peterstaranchuk.cleaningservicebusiness.activities.LoginActivity;
import com.peterstaranchuk.cleaningservicebusiness.dagger2modules.LoginScreenModule;
import com.peterstaranchuk.cleaningservicebusiness.model.LoginCallbackModule;
import com.peterstaranchuk.cleaningservicebusiness.model.LoginScreenModel;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@Component(modules = LoginCallbackModule.class)
public interface LoginModelComponent {
    void inject(LoginScreenModel model);

    class Injector {
        public static void inject(LoginActivity activity) {
            DaggerLoginScreenComponent.builder()
                    .loginScreenModule(new LoginScreenModule(activity))
                    .build()
                    .inject(activity);
        }
    }
}

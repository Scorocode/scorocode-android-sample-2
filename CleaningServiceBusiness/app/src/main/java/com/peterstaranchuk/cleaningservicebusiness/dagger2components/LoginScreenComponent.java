package com.peterstaranchuk.cleaningservicebusiness.dagger2components;

import com.peterstaranchuk.cleaningservicebusiness.activities.LoginActivity;
import com.peterstaranchuk.cleaningservicebusiness.dagger2modules.LoginScreenModule;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@Component(modules = LoginScreenModule.class)
public interface LoginScreenComponent {

    void inject(LoginActivity activity);

    class Injector {
        public static void inject(LoginActivity activity) {
            DaggerLoginScreenComponent.builder()
                    .loginScreenModule(new LoginScreenModule(activity))
                    .build()
                    .inject(activity);
        }
    }

}

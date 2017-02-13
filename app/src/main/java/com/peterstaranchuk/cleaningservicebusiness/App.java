package com.peterstaranchuk.cleaningservicebusiness;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Peter Staranchuk.
 */

public class App extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

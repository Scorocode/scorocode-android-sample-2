package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.support.v7.app.ActionBar;

/**
 * Created by Peter Staranchuk.
 */

public class ActionBarHelper {


    public static void setHomeButton(ActionBar actionBar) {
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

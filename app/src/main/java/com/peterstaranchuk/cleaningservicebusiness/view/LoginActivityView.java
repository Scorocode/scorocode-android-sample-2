package com.peterstaranchuk.cleaningservicebusiness.view;

import android.content.Context;

/**
 * Created by Peter Staranchuk.
 */

public interface LoginActivityView {

    String getEmail();

    String getPassword();

    Context getContext();

    void enableLoginButton();

    void disableLoginButton();

    void setDataListeners();

    void setItemsVisibility();

    void setActionBar();

    void showError(int errorId);

    void displayMainActivity();
}

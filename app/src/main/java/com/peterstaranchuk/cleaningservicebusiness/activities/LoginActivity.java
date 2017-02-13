package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peterstaranchuk.cleaningservicebusiness.R;
import com.peterstaranchuk.cleaningservicebusiness.dagger2components.LoginScreenComponent;
import com.peterstaranchuk.cleaningservicebusiness.helpers.InputHelper;
import com.peterstaranchuk.cleaningservicebusiness.presenter.LoginScreenPresenter;
import com.peterstaranchuk.cleaningservicebusiness.view.LoginActivityView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.profit_group.scorocode_sdk.ScorocodeSdk;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;

    @Inject Action1<CharSequence> action;
    @Inject LoginScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginScreenComponent.Injector.inject(this);
        ButterKnife.bind(this);
        ScorocodeSdk.initWith(
                getString(R.string.appKey),
                getString(R.string.clientKey),
                null,
                getString(R.string.fileKey), null, null, null
        );

        presenter.onCreateScreen();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginButtonClicked(View loginButton) {
        presenter.onLoginButtonClicked();
    }

    @Override
    public String getEmail() {
        return InputHelper.getStringFrom(etEmail);
    }

    @Override
    public String getPassword() {
        return InputHelper.getStringFrom(etPassword);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void enableLoginButton() {
        InputHelper.enableButton(btnLogin);
    }

    @Override
    public void disableLoginButton() {
        InputHelper.disableButton(btnLogin);
    }

    @Override
    public void setDataListeners() {
        InputHelper.checkForEmptyEnter(etEmail, action);
        InputHelper.checkForEmptyEnter(etPassword, action);
    }

    @Override
    public void setItemsVisibility() {
        ButterKnife.findById(this, R.id.tvTitle).setVisibility(View.VISIBLE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.loginScreenTitle));
        }
    }

    @Override
    public void showError(int errorId) {
        Toast.makeText(this, getString(errorId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMainActivity() {
        OrdersActivity.display(this);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

}

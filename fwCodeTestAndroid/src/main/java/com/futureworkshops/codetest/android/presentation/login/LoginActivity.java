package com.futureworkshops.codetest.android.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.futureworkshops.codetest.android.R;
import com.futureworkshops.codetest.android.presentation.landing.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity extends DaggerAppCompatActivity
        implements LoginPresenter.View {

    @BindView(R.id.login_user)
    EditText user;
    @BindView(R.id.login_pass)
    EditText pass;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        getSupportActionBar().setTitle(R.string.login);
        ButterKnife.bind(this);
        onInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public void onInit() {
        loginPresenter.attachView(this);
    }

    @OnClick(R.id.login_button)
    public void makeLogin(View view) {
        if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            showErrorMessage(getString(R.string.login_empty));
            return;
        }
        loginPresenter.performLogin(user.getText().toString(), pass.getText().toString());
    }

    public void showErrorMessage(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginError(String error) {
        showErrorMessage(error);
    }
}

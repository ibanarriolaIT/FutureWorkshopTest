package com.futureworkshops.codetest.android.presentation.login;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.domain.repositories.LoginRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {

    LoginRepository loginRepository;

    public LoginPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @SuppressLint("CheckResult")
    public void performLogin(String user, String pass) {
        loginRepository.performLogin(user, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(login -> ((View)getView()).onLoginSuccess(),
                throwable -> ((View)getView()).onLoginError(ErrorHandler.getErrorMessage(throwable)));
    }

    public interface View {
        void onLoginSuccess();
        void onLoginError(String error);
    }
}

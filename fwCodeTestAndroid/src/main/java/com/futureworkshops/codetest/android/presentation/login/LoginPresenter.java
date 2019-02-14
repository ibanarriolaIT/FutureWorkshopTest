package com.futureworkshops.codetest.android.presentation.login;

import android.annotation.SuppressLint;

import com.futureworkshops.codetest.android.domain.repositories.LoginRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter {

    private final LoginRepository loginRepository;

    public LoginPresenter(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @SuppressLint("CheckResult")
    public void performLogin(String user, String pass) {
        Disposable disposable = loginRepository.performLogin(user, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(login -> ((View) getView()).onLoginSuccess(),
                        throwable -> ((View) getView()).onLoginError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public interface View extends BaseView {
        void onLoginSuccess();

        void onLoginError(String error);
    }
}

package com.futureworkshops.codetest.android.domain.repositories;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.domain.model.Login;

import io.reactivex.Single;

public class LoginRepository {

    private final RestManager restManager;

    public LoginRepository(RestManager restManager) {
        this.restManager = restManager;
    }

    public Single<Login> performLogin(String user, String pass) {
        return restManager.login(user, pass);
    }
}

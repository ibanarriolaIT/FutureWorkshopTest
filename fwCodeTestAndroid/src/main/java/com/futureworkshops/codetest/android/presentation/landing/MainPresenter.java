package com.futureworkshops.codetest.android.presentation.landing;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.WorkerSchedulerProvider;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenter extends BasePresenter {

    RestManager restManager;

    public MainPresenter(RestManager restManager) {
        this.restManager = restManager;
    }

    public void callImportantOperation() {
        restManager.performImportantOperation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ((View) getView()).onImportantOperationOk(),
                        throwable -> ((View) getView()).onImportantOperationError());
    }

    public interface View {
        void onImportantOperationOk();

        void onImportantOperationError();
    }
}

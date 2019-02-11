package com.futureworkshops.codetest.android.presentation.landing;

import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.WorkerSchedulerProvider;
import com.futureworkshops.codetest.android.domain.repositories.ImportantOperationRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenter extends BasePresenter {

    ImportantOperationRepository importantOperationRepository;

    public MainPresenter(ImportantOperationRepository importantOperationRepository) {
        this.importantOperationRepository = importantOperationRepository;
    }

    public void callImportantOperation() {
        importantOperationRepository.performImportantOperation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ((View) getView()).onImportantOperationOk(),
                        throwable -> ((View) getView()).onImportantOperationError(ErrorHandler.getErrorMessage(throwable)));
    }

    public interface View {
        void onImportantOperationOk();

        void onImportantOperationError(String error);
    }
}

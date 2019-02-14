package com.futureworkshops.codetest.android.presentation.landing;

import com.futureworkshops.codetest.android.domain.repositories.ImportantOperationRepository;
import com.futureworkshops.codetest.android.presentation.common.BasePresenter;
import com.futureworkshops.codetest.android.presentation.common.BaseView;
import com.futureworkshops.codetest.android.presentation.utils.ErrorHandler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter {

    private final ImportantOperationRepository importantOperationRepository;

    public MainPresenter(ImportantOperationRepository importantOperationRepository) {
        this.importantOperationRepository = importantOperationRepository;
    }

    public void callImportantOperation() {
        Disposable disposable = importantOperationRepository.performImportantOperation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ((View) getView()).onImportantOperationOk(),
                        throwable -> ((View) getView()).onImportantOperationError(ErrorHandler.getErrorMessage(throwable)));
        addSubscription(disposable);
    }

    public interface View extends BaseView {
        void onImportantOperationOk();

        void onImportantOperationError(String error);
    }
}

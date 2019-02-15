package com.futureworkshops.codetest.android.presentation.common;

import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends BaseView> {
    protected WeakReference<V> view;
    private WeakReference<CompositeDisposable> subscriptions;

    @UiThread
    public void attachView(V view) {
        this.view = new WeakReference(view);
    }

    @UiThread
    public void detachView() {
        clearView();
        clearSubscriptions();
    }

    @UiThread
    public V getView() {
        return view == null ? null : view.get();
    }

    @UiThread
    public boolean isViewAttached() {
        return view != null && view.get() != null;
    }

    @UiThread
    protected void addSubscription(Disposable disposable) {
        if (subscriptions == null || subscriptions.get() == null) {
            subscriptions = new WeakReference<>(new CompositeDisposable());
        }
        if (subscriptions.get() != null) {
            subscriptions.get().add(disposable);
        }
    }

    private void clearView() {
        if (view == null) {
            return;
        }
        view.clear();
        view = null;
    }

    private void clearSubscriptions() {
        if (subscriptions == null) {
            return;
        }
        if (subscriptions.get() != null) {
            subscriptions.get().dispose();
        }
        subscriptions.clear();
        subscriptions = null;
    }
}

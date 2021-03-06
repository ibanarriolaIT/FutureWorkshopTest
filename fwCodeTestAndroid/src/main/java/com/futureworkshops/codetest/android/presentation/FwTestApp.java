/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.presentation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.futureworkshops.codetest.android.BuildConfig;
import com.futureworkshops.codetest.android.data.network.server.MockServer;
import com.futureworkshops.codetest.android.domain.dagger.ApplicationComponent;
import com.futureworkshops.codetest.android.domain.dagger.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;


public class FwTestApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initMockServer();
    }


    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseLogTree());
        }
    }


    private void initMockServer() {
        MockServer mockServer = new MockServer(this);
        mockServer.start();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().application(this).build();
        applicationComponent.inject(this);
        return applicationComponent;
    }

    class ReleaseLogTree extends Timber.Tree {

        private static final int MAX_LOG_LENGTH = 4000;

        @Override
        protected boolean isLoggable(String tag, int priority) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return false;
            }

            return true;
        }

        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable t) {
            if (isLoggable(tag, priority)) {
                if (message.length() < MAX_LOG_LENGTH) {
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, message);
                    } else {
                        Log.println(priority, tag, message);
                    }
                    return;
                }

                int length = message.length();
                for (int i = 0; i < length; i++) {
                    int newLine = message.indexOf('\n', i);
                    newLine = newLine != -1 ? newLine : length;
                    do {
                        int end = Math.min(newLine, i + MAX_LOG_LENGTH);
                        String part = message.substring(i, end);
                        if (priority == Log.ASSERT) {
                            Log.wtf(tag, part);
                        } else {
                            Log.println(priority, tag, part);
                        }
                        i = end;
                    } while (i < newLine);
                }
            }
        }
    }

}

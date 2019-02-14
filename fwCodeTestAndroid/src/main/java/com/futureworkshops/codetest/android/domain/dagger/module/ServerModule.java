/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.domain.dagger.module;

import android.content.Context;

import com.futureworkshops.codetest.android.data.network.server.MockServer;
import com.futureworkshops.codetest.android.presentation.FwTestApp;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServerModule {

    @Provides
    static Context provideContext(FwTestApp application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    MockServer providesMockServer(Context context) {
        return new MockServer(context);
    }

    @Singleton
    @Provides
    @Named("endpoint")
    String providesEndpoint(MockServer mockServer) {
        return mockServer.getUrl();
    }

}

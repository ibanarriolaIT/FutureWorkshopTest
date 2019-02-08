package com.futureworkshops.codetest.android.domain.dagger;

import android.content.Context;

import com.futureworkshops.codetest.android.presentation.FwTestApp;
import com.futureworkshops.codetest.android.presentation.landing.MainActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, InjectorBuilder.class, NetModule.class, ServerModule.class})
public interface ApplicationComponent extends AndroidInjector<FwTestApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(FwTestApp application);

        ApplicationComponent build();
    }

    void inject(FwTestApp app);
}


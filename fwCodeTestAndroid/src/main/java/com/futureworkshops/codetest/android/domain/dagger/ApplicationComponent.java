package com.futureworkshops.codetest.android.domain.dagger;

import com.futureworkshops.codetest.android.domain.dagger.module.NetModule;
import com.futureworkshops.codetest.android.domain.dagger.module.RoomModule;
import com.futureworkshops.codetest.android.domain.dagger.module.ServerModule;
import com.futureworkshops.codetest.android.presentation.FwTestApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, InjectorBuilder.class, RoomModule.class, NetModule.class, ServerModule.class})
public interface ApplicationComponent extends AndroidInjector<FwTestApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(FwTestApp application);

        ApplicationComponent build();
    }

    void inject(FwTestApp app);
}


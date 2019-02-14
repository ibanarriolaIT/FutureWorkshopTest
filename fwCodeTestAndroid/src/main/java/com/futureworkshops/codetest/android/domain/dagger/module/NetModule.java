/*
 * Copyright (c) 2018 FutureWorkshops. All rights reserved.
 */

package com.futureworkshops.codetest.android.domain.dagger.module;

import com.futureworkshops.codetest.android.BuildConfig;
import com.futureworkshops.codetest.android.data.network.RestManager;
import com.futureworkshops.codetest.android.data.network.XMLRestManager;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.SchedulersProvider;
import com.futureworkshops.codetest.android.data.network.rx.scheduler.WorkerSchedulerProvider;
import com.futureworkshops.codetest.android.domain.repositories.BreedRepository;
import com.futureworkshops.codetest.android.domain.repositories.ImportantOperationRepository;
import com.futureworkshops.codetest.android.domain.repositories.LoginRepository;
import com.futureworkshops.codetest.android.domain.repositories.StatsRepository;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedStats;
import com.futureworkshops.codetest.android.domain.usecase.GetBreedsList;
import com.google.gson.Gson;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class NetModule {

    @Provides
    SchedulersProvider provideSchedulersProvider() {
        return new WorkerSchedulerProvider();
    }

    @Singleton
    @Provides
    RestManager providesRestManager(SchedulersProvider schedulersProvider,
                                    @Named("JsonRetrofit") Retrofit retrofit) {
        return new RestManager(schedulersProvider, retrofit);
    }

    @Singleton
    @Provides
    XMLRestManager providesXMLRestManager(SchedulersProvider schedulersProvider,
                                          @Named("XMLRetrofit") Retrofit retrofit) {
        return new XMLRestManager(schedulersProvider, retrofit);
    }

    @Provides
    @Named("JsonRetrofit")
    Retrofit providesJsonRetrofit(@Named("endpoint") String endpointUrl,
                                  Interceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Named("XMLRetrofit")
    Retrofit providesXMLRetrofit(@Named("endpoint") String endpointUrl,
                                 Interceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(endpointUrl)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory
                        .createNonStrict(new Persister(new AnnotationStrategy())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    Interceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(RestManager restManager) {
        return new LoginRepository(restManager);
    }

    @Provides
    @Singleton
    BreedRepository providesBreedRepository(RestManager restManager) {
        return new BreedRepository(restManager);
    }

    @Provides
    @Singleton
    StatsRepository providesStatsRepository(XMLRestManager restManager) {
        return new StatsRepository(restManager);
    }

    @Provides
    @Singleton
    ImportantOperationRepository providesImportantOperationRepository(RestManager restManager) {
        return new ImportantOperationRepository(restManager);
    }

    @Provides
    @Singleton
    GetBreedsList providesGetBreedsList(BreedRepository breedRepository) {
        return new GetBreedsList(breedRepository);
    }

    @Provides
    @Singleton
    GetBreedStats providesGetBreedStats(StatsRepository statsRepository) {
        return new GetBreedStats(statsRepository);
    }


}

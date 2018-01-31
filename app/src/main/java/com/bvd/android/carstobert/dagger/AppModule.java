package com.bvd.android.carstobert.dagger;

import android.content.Context;

import com.bvd.android.carstobert.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bara on 1/31/2018.
 */

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }
}

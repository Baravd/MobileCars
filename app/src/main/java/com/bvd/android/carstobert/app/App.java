package com.bvd.android.carstobert.app;

import android.app.Application;

import com.bvd.android.carstobert.dagger.AppComponent;
import com.bvd.android.carstobert.dagger.AppModule;
import com.bvd.android.carstobert.dagger.DaggerAppComponent;

/**
 * Created by bara on 1/31/2018.
 */

public class App extends Application {
    private static final String TAG = App.class.getName();

    public AppComponent injector;

    public AppComponent getInjector() {
        return injector;
    }

    public static String getTAG() {
        return TAG;
    }

    protected AppComponent initDagger(App application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}

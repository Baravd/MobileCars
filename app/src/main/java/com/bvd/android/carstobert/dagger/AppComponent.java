package com.bvd.android.carstobert.dagger;

import com.bvd.android.carstobert.app.App;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by bara on 1/31/2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App target);

}

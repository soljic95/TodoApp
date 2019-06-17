package com.example.sampletodoapp.base;

import android.app.Application;
import android.content.Context;

import com.example.sampletodoapp.di.component.ApplicationComponent;
import com.example.sampletodoapp.di.component.DaggerApplicationComponent;
import com.example.sampletodoapp.di.module.ApplicationModule;

public class BaseApplication extends Application {

    private ApplicationComponent component;

    public static BaseApplication from(final Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                                              .applicationModule(new ApplicationModule(this))
                                              .build();

    }

    public ApplicationComponent getComponent() {
        return component;
    }
}

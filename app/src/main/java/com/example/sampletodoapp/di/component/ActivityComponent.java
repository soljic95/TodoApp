package com.example.sampletodoapp.di.component;

import com.example.sampletodoapp.di.module.ActivityModule;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.main.MainActivity;
import com.example.sampletodoapp.ui.createUser.CreateUserActivity;
import com.example.sampletodoapp.ui.splash.SplashScreenActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {
                ApplicationComponent.class
        },
        modules = {
                ActivityModule.class
        }
)
public interface ActivityComponent extends ActivityComponentExposes {

    void inject(CreateUserActivity activity);

    void inject(MainActivity activity);

    void inject(SplashScreenActivity activity);

}

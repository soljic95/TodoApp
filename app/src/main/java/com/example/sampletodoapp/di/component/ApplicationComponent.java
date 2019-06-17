package com.example.sampletodoapp.di.component;

import com.example.sampletodoapp.di.module.ApplicationModule;
import com.example.sampletodoapp.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent extends ApplicationComponentExposes {

}

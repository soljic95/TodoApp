package com.example.sampletodoapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.sampletodoapp.R;
import com.example.sampletodoapp.base.BaseActivity;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.ActivityComponent;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.main.MainActivity;
import com.example.sampletodoapp.ui.createUser.CreateUserActivity;

import javax.inject.Inject;

public class SplashScreenActivity extends BaseActivity implements SplashContract.View {

    @ActivityScope
    @Inject
    SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        presenter.setSplashView(this);
        presenter.init();

    }

    @Override
    protected void inject(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

}

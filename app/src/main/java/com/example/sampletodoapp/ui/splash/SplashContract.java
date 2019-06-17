package com.example.sampletodoapp.ui.splash;

public interface SplashContract {

    interface View {

    }

    interface Presenter {

        void init();

        void setSplashView(SplashContract.View splashView);

    }
}

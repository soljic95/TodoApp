package com.example.sampletodoapp.base;

import android.os.Bundle;

import com.example.sampletodoapp.di.component.ActivityComponent;
import com.example.sampletodoapp.di.component.DaggerActivityComponent;
import com.example.sampletodoapp.di.module.ActivityModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppComponent();
        inject(component);

    }

    private void initAppComponent() {
        component = DaggerActivityComponent.builder()
                                           .applicationComponent(BaseApplication.from(this).getComponent())
                                           .activityModule(new ActivityModule(this))
                                           .build();
    }

    protected abstract void inject(final ActivityComponent component);

    protected abstract BasePresenter getPresenter();

    public ActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void onResume() {
        getPresenter().activate();
        super.onResume();
    }

    @Override
    protected void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }
}




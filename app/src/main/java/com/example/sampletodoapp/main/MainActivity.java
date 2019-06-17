package com.example.sampletodoapp.main;

import android.os.Bundle;

import com.example.sampletodoapp.R;
import com.example.sampletodoapp.base.BaseActivity;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.ActivityComponent;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.toolbar2)
    Toolbar toolbar;

    @Inject
    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            presenter.showTodoListScreen();
        }

    }

    @Override
    protected void inject(final ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

}


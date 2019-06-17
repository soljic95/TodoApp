package com.example.sampletodoapp.main;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.router.Router;

public class MainActivityPresenter extends BasePresenter implements MainContract.Presenter {

    private Optional<MainContract.View> view;

    public MainActivityPresenter(final TodoDao todoDao, final Router router) {
        super(todoDao, router);
    }

    @Override
    public void showTodoListScreen() {
        getRouter().showTodoListScreen();
    }

    @Override
    public void setView(final MainContract.View view) {
        this.view = Optional.of(view);
    }
}





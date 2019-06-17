package com.example.sampletodoapp.base;

import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.router.Router;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter {

    private Router router;
    private TodoDao todoDao;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BasePresenter(TodoDao todoDao, Router router) {
        this.router = router;
        this.todoDao = todoDao;
    }

    protected void deactivate() {
        compositeDisposable.clear();
    }

    public Router getRouter() {
        return router;
    }

    public TodoDao getTodoDao() {
        return todoDao;
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected void activate() {
        //do something
    }
}



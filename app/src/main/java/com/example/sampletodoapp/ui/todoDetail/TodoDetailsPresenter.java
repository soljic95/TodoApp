package com.example.sampletodoapp.ui.todoDetail;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.router.Router;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TodoDetailsPresenter extends BasePresenter implements TodoDetailsContract.Presenter {

    private Optional<TodoDetailsContract.View> detailsView = Optional.empty();
    private TodoDao todoDao = getTodoDao();

    public TodoDetailsPresenter(final TodoDao todoDao, final Router router) {
        super(todoDao, router);
    }

    @Override
    public void deleteTodo(long todoId) {
        addDisposable(Completable.fromAction(() -> todoDao.deleteTodo(todoId))
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(() -> detailsView.ifPresent(TodoDetailsContract.View::onTodoDeleted)));
    }

    @Override
    public void init(long todoId) {
        addDisposable(todoDao.getTodoFromDb(todoId)
                             .subscribeOn(Schedulers.io())
                             .observeOn(AndroidSchedulers.mainThread())
                             .subscribe(todo -> detailsView.ifPresent(view -> view.render(todo))));
    }

    @Override
    public void setDetailsView(TodoDetailsContract.View view) {
        this.detailsView = Optional.ofNullable(view);
    }

    @Override
    public void exitScreen() {
        getRouter().goBack();
    }

}

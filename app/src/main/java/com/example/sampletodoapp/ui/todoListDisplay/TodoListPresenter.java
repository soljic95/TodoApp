package com.example.sampletodoapp.ui.todoListDisplay;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.data.TodoDatabase;
import com.example.sampletodoapp.router.Router;
import com.example.sampletodoapp.utils.CollectionsUtils;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TodoListPresenter extends BasePresenter implements TodoListContract.Presenter {

    private TodoDatabase todoDatabase;
    private Optional<TodoListContract.View> todoDisplayView = Optional.empty();
    private CollectionsUtils utils = new CollectionsUtils();
    private Router router = getRouter();
    private TodoDao todoDao = getTodoDao();

    public TodoListPresenter(final TodoDao todoDao, final Router router, TodoDatabase todoDatabase) {
        super(todoDao, router);
        this.todoDatabase = todoDatabase;
    }

    @Override
    public void requestData() {
        addDisposable(todoDao.getAllTodos()
                             .map(todos -> utils.sortList(todos))
                             .subscribeOn(Schedulers.io())
                             .observeOn(AndroidSchedulers.mainThread())
                             .subscribe(todos -> todoDisplayView.ifPresent(view -> view.render(todos))));
    }

    @Override
    public void deleteTodo(long todoId) {
        addDisposable(Completable.fromAction(() -> todoDao.deleteTodo(todoId))
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe());
    }

    @Override
    public void deleteAccount() {
        addDisposable(Completable.fromAction(() -> todoDatabase.clearAllTables())
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(() -> todoDisplayView.ifPresent(view -> todoDisplayView.get().onDatabaseCleared())));
    }

    @Override
    public void showProgressDialog() {
        todoDisplayView.ifPresent(TodoListContract.View::onShowProgressDialog);
    }

    public void setView(TodoListContract.View todoDisplayView) {
        this.todoDisplayView = Optional.ofNullable(todoDisplayView);
    }

    @Override
    public void onFabClicked() {
        router.showCreateTodoFragmentScreen();
    }

    @Override
    public void onListItemClicked(final long todoId) {
        router.showTodoDetailScreen(todoId);
    }

    @Override
    public void exitScreen() {
        router.showCreateUserScreen();
    }

}

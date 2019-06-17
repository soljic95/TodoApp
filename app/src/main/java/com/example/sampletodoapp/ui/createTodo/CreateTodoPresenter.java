package com.example.sampletodoapp.ui.createTodo;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.model.Todo;
import com.example.sampletodoapp.router.Router;

import java.util.Calendar;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateTodoPresenter extends BasePresenter implements CreateTodoContract.Presenter {

    private Optional<CreateTodoContract.View> createTodoView = Optional.empty();

    public CreateTodoPresenter(final TodoDao todoDao, final Router router) {
        super(todoDao, router);
    }

    @Override
    public void setCreateTodoView(CreateTodoContract.View createTodoView) {
        this.createTodoView = Optional.ofNullable(createTodoView);
    }

    @Override
    public void exitButtonClicked() {
        getRouter().goBack();
    }

    @Override
    public void btnDeadlineClicked() {
        createTodoView.ifPresent(CreateTodoContract.View::onDateFragmentDisplay);
    }

    public void createTodo(final Todo todo) {
        addDisposable(Completable.fromAction(() -> getTodoDao().insertTodo(todo))
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(() ->
                                                    createTodoView.ifPresent(view -> createTodoView.get().onTodoCreated())));
    }

    @Override
    public void formatData(final long todoId, final String etHeadline, final String etText, final long deadlineInMili, final String etPriority) {
        Todo todo = new Todo();
        todo.setTodoHead(etHeadline);
        todo.setTodoText(etText);

        if (deadlineInMili > Calendar.getInstance().getTimeInMillis()) {
            todo.setTodoDeadline(deadlineInMili);
        }
        todo.setTodoId(todoId);
        todo.setTodoPriority(Integer.parseInt(etPriority));

        createTodo(todo);
    }

    @Override
    public void validateInput(final String etHeadline, final String etText, final String etPriority) {
        boolean isInputValid = true;
        if (etHeadline.length() < 1) {
            createTodoView.ifPresent(CreateTodoContract.View::onHeadlineEmpty);
            isInputValid = false;
        }

        if (etText.length() < 1) {
            createTodoView.ifPresent(CreateTodoContract.View::onTextEmpty);
            isInputValid = false;
        }

        if (etPriority.length() < 1) {
            createTodoView.ifPresent(CreateTodoContract.View::onPriorityEmpty);
            isInputValid = false;
        }

        if (isInputValid) {
            createTodoView.ifPresent(CreateTodoContract.View::onInputValid);
        }
    }
}

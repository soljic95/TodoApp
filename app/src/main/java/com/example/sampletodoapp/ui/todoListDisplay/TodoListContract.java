package com.example.sampletodoapp.ui.todoListDisplay;

import com.example.sampletodoapp.model.Todo;

import java.util.List;

public interface TodoListContract {

    interface View {

        void onDatabaseCleared();

        void onShowProgressDialog();

        void render(List<Todo> todoList);
    }

    interface Presenter {

        void requestData();

        void deleteTodo(long todoId);

        void deleteAccount();

        void showProgressDialog();

        void setView(TodoListContract.View todoListView);

        void onFabClicked();

        void onListItemClicked(long todoId);

        void exitScreen();

    }

}

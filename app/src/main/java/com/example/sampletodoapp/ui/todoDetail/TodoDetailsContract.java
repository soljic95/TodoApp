package com.example.sampletodoapp.ui.todoDetail;

import com.example.sampletodoapp.model.Todo;

public interface TodoDetailsContract {

    interface View {

        void onTodoDeleted();

        void render(Todo todo);

    }

    interface Presenter {

        void deleteTodo(long todoId);

        void init(long todoId);

        void setDetailsView(TodoDetailsContract.View detailsView);

        void exitScreen();
    }
}

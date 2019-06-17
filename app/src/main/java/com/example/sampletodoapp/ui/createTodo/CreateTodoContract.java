package com.example.sampletodoapp.ui.createTodo;

public interface CreateTodoContract {

    interface View {

        void onTodoCreated();

        void onHeadlineEmpty();

        void onTextEmpty();

        void onPriorityEmpty();

        void onInputValid();

        void onDateFragmentDisplay();

    }

    interface Presenter {

        void formatData(long todoId, String etHeadline, String etText, long deadlineInMili, String etPriority);

        void validateInput(String etHeadline, String etText, String etPriority);

        void setCreateTodoView(CreateTodoContract.View view);

        void exitButtonClicked();

        void btnDeadlineClicked();

    }
}

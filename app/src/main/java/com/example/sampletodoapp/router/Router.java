package com.example.sampletodoapp.router;

public interface Router {

    void goBack();

    void showMainScreen();

    void showCreateTodoFragmentScreen();

    void showTodoDetailScreen(long todoId);

    void showCreateUserScreen();

    void showTodoListScreen();

}

package com.example.sampletodoapp.main;

public class MainContract {

    public interface View {

    }

    public interface Presenter {

        void showTodoListScreen();

        void setView(MainContract.View view);

    }
}

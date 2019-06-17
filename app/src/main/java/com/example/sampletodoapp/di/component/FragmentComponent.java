package com.example.sampletodoapp.di.component;

import com.example.sampletodoapp.di.module.FragmentModule;
import com.example.sampletodoapp.di.scope.FragmentScope;
import com.example.sampletodoapp.ui.createTodo.CreateTodoFragment;
import com.example.sampletodoapp.ui.todoDetail.TodoDetailFragment;
import com.example.sampletodoapp.ui.todoListDisplay.TodoListFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = ActivityComponent.class, modules = {FragmentModule.class})
public interface FragmentComponent extends FragmentComponentExposes {

    void inject(TodoListFragment fragment);

    void inject(TodoDetailFragment fragment);

    void inject(CreateTodoFragment fragment);

}

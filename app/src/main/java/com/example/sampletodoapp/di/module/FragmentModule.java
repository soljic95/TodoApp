package com.example.sampletodoapp.di.module;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.sampletodoapp.adapter.TodoRecyclerAdapter;
import com.example.sampletodoapp.base.BaseFragment;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.data.TodoDatabase;
import com.example.sampletodoapp.di.qualifiers.ForActivity;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.di.scope.FragmentScope;
import com.example.sampletodoapp.router.Router;
import com.example.sampletodoapp.ui.createTodo.CreateTodoContract;
import com.example.sampletodoapp.ui.createTodo.CreateTodoPresenter;
import com.example.sampletodoapp.ui.todoDetail.TodoDetailsContract;
import com.example.sampletodoapp.ui.todoDetail.TodoDetailsPresenter;
import com.example.sampletodoapp.ui.todoListDisplay.TodoListContract;
import com.example.sampletodoapp.ui.todoListDisplay.TodoListPresenter;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManger(BaseFragment baseFragment) {
        return baseFragment.getFragmentManager();
    }

    @Provides
    static public LinearLayoutManager provideLinearManager(@ForActivity Context context) {
        return new LinearLayoutManager(context);
    }

    @FragmentScope
    @Provides
    static public TodoRecyclerAdapter provideRecyclerAdapter(LayoutInflater inflater) {
        return new TodoRecyclerAdapter(inflater);
    }

    @FragmentScope
    @Provides
    static public TodoListContract.Presenter provideTodoListPresenter(TodoDao todoDao, TodoDatabase todoDatabase, Router router) {
        return new TodoListPresenter(todoDao, router, todoDatabase);
    }

    @FragmentScope
    @Provides
    static public TodoDetailsContract.Presenter provideTodoDetailsPresenter(TodoDao todoDao, Router router) {
        return new TodoDetailsPresenter(todoDao, router);
    }

    @FragmentScope
    @Provides
    static public CreateTodoContract.Presenter provideCreteTodoPresenter(TodoDao todoDao, Router router) {
        return new CreateTodoPresenter(todoDao, router);
    }

    public interface Exposes {

    }

}

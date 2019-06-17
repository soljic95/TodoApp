package com.example.sampletodoapp.di.module;

import android.content.Context;

import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.data.TodoDatabase;
import com.example.sampletodoapp.di.qualifiers.ForApplication;
import com.example.sampletodoapp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ForApplication
    @ApplicationScope
    Context provideContext() {
        return context;
    }

    @Provides
    @ApplicationScope
    static TodoDatabase provideTodoDatabase(@ForApplication Context context) {
        return TodoDatabase.getInstance(context);
    }

    @Provides
    @ApplicationScope
    TodoDao provideTodoDao(TodoDatabase todoDatabase) {
        return todoDatabase.todoDao();
    }

    public interface Exposes {

        TodoDatabase getTodoDatabase();

        @ForApplication
        Context getApplicationContext();

        TodoDao getTodoDao();
    }
}

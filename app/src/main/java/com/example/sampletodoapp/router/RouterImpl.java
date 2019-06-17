package com.example.sampletodoapp.router;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.sampletodoapp.R;
import com.example.sampletodoapp.main.MainActivity;
import com.example.sampletodoapp.ui.createTodo.CreateTodoFragment;
import com.example.sampletodoapp.ui.createUser.CreateUserActivity;
import com.example.sampletodoapp.ui.todoDetail.TodoDetailFragment;
import com.example.sampletodoapp.ui.todoListDisplay.TodoListFragment;

import androidx.fragment.app.FragmentManager;

public class RouterImpl implements Router {

    private static final int CONTAINER_ID = R.id.container;
    private static final String CURRENT_TODO_BUNDLE_KEY = "todo";
    private static final int ZERO_VALUE = 0;

    private Activity activity;
    private FragmentManager fragmentManager;

    public RouterImpl(final Activity activity, final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void goBack() {
        if (areThereFragmentsOnTheBackStack()) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }

    @Override
    public void showMainScreen() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    public void showTodoDetailScreen(long todoId) {
        TodoDetailFragment detailFragment = new TodoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(CURRENT_TODO_BUNDLE_KEY, todoId);
        detailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                       .addToBackStack(null)
                       .replace(CONTAINER_ID, detailFragment)
                       .commit();

    }

    @Override
    public void showCreateUserScreen() {
        activity.startActivity(new Intent(activity, CreateUserActivity.class));
        activity.finish();
    }

    @Override
    public void showTodoListScreen() {
        TodoListFragment todoListFragment = new TodoListFragment();
        fragmentManager.beginTransaction()
                       .replace(CONTAINER_ID, todoListFragment)
                       .commit();
    }

    @Override
    public void showCreateTodoFragmentScreen() {
        CreateTodoFragment createTodoFragment = new CreateTodoFragment();
        fragmentManager.beginTransaction()
                       .addToBackStack(null)
                       .replace(CONTAINER_ID, createTodoFragment)
                       .commit();

    }

    private boolean areThereFragmentsOnTheBackStack() {
        return fragmentManager.getBackStackEntryCount() > ZERO_VALUE;

    }
}

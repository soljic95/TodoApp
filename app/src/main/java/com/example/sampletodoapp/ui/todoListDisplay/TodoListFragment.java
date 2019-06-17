package com.example.sampletodoapp.ui.todoListDisplay;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.R;
import com.example.sampletodoapp.adapter.TodoRecyclerAdapter;
import com.example.sampletodoapp.base.BaseFragment;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.FragmentComponent;
import com.example.sampletodoapp.di.qualifiers.ForActivity;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoListFragment extends BaseFragment implements TodoListContract.View {

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    @Inject
    TodoRecyclerAdapter adapter;

    @Inject
    Provider<LinearLayoutManager> manager;

    @Inject
    TodoListContract.Presenter presenter;

    @Inject
    @ForActivity
    @ActivityScope
    Context context;

    private Optional<AlertDialog> progressDialog = Optional.empty();
    private Optional<AppCompatActivity> appCompatActivityOptional = Optional.empty();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appCompatActivityOptional = Optional.ofNullable((AppCompatActivity) getActivity());

        return inflater.inflate(R.layout.fragment_todo_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.setView(this);

        setSwipeToDelete(recyclerView);

        setUpRecyclerView();

        setHasOptionsMenu(true);

        setAdapterOnClickListener();

    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

    @Override
    protected void inject(FragmentComponent component) {
        component.inject(this);
    }

    @OnClick(R.id.fab)
    void openCreateTodoFragment() {
        presenter.onFabClicked();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager.get());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        appCompatActivityOptional.ifPresent(activity -> activity.getMenuInflater().inflate(R.menu.main_menu, menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAcc) {
            presenter.showProgressDialog();
            presenter.deleteAccount();
        }
        return false;
    }

    @Override
    public void onResume() {
        presenter.requestData();
        ActionBar supportActionBar = appCompatActivityOptional.get().getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setTitle(R.string.todo_list_fragment_title);
        }
        super.onResume();
    }

    @Override
    public void onDatabaseCleared() {
        presenter.exitScreen();

    }

    @Override
    public void onShowProgressDialog() {
        progressDialog = Optional.of(new AlertDialog.Builder(context)
                                             .setView(R.layout.progress_dialog)
                                             .create());
        progressDialog.get().show();
    }

    @Override
    public void render(List<Todo> todoList) {
        adapter.addTodoList(todoList);
        adapter.notifyDataSetChanged();
    }

    private void setSwipeToDelete(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                                                               ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                presenter.deleteTodo(adapter.getTodoAt(viewHolder.getAdapterPosition()));

            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDestroy() {
        progressDialog.ifPresent(Dialog::dismiss);
        super.onDestroy();
    }

    private void setAdapterOnClickListener() {
        TodoRecyclerAdapter.TodoRecyclerAdapterListener listener = todoId -> presenter.onListItemClicked(todoId);

        adapter.setAdapterListener(listener);
    }

}

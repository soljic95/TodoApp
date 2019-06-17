package com.example.sampletodoapp.ui.todoDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.R;
import com.example.sampletodoapp.base.BaseFragment;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.FragmentComponent;
import com.example.sampletodoapp.di.scope.FragmentScope;
import com.example.sampletodoapp.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoDetailFragment extends BaseFragment implements TodoDetailsContract.View {

    private final static String CURRENT_TODO_BUNDLE_KEY = "todo";

    @BindView(R.id.tvHead)
    TextView tvHeadline;

    @BindView(R.id.tvPriority)
    TextView tvPriority;

    @BindView(R.id.tvText)
    TextView tvTodoText;

    @BindView(R.id.tvDateCreated)
    TextView tvTodoDateCreated;

    @BindView(R.id.tvDeadline)
    TextView tvDeadline;

    @Inject
    @FragmentScope
    TodoDetailsContract.Presenter presenter;

    private Optional<AppCompatActivity> appCompatActivityOptional;

    private Todo currentTodo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setPresenter();

        appCompatActivityOptional = Optional.ofNullable((AppCompatActivity) getActivity());

        return inflater.inflate(R.layout.fragment_detailed_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setActionBar();
    }

    @Override
    protected void inject(FragmentComponent component) {
        component.inject(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.delete_todo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.exitScreen();
        } else if (item.getItemId() == R.id.deleteTodo) {
            presenter.deleteTodo(currentTodo.getTodoId());
            Log.d("marko", "onOptionsItemSelected: " + item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTodoDeleted() {
        presenter.exitScreen();
    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

    @Override
    public void render(Todo todo) {
        final SimpleDateFormat sdfDate = new SimpleDateFormat(getResources().getString(R.string.simple_date_format_date), Locale.getDefault());
        currentTodo = todo;
        tvHeadline.setText(currentTodo.getTodoHead());
        tvTodoText.setText(currentTodo.getTodoText());
        tvPriority.setText(String.format(getResources().getString(R.string.priority), currentTodo.getTodoPriority()));
        tvDeadline.setText(String.format(getResources().getString(R.string.deadline_set_text), sdfDate.format(currentTodo.getTodoDeadline())));
    }

    private void setActionBar() {
        appCompatActivityOptional.ifPresent(activity -> {
            ActionBar actionBar = appCompatActivityOptional.get().getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(getResources().getString(R.string.empty_string));
            }
        });
    }

    private void setPresenter() {
        presenter.setDetailsView(this);
        presenter.init(getArguments().getLong(CURRENT_TODO_BUNDLE_KEY));

    }

}

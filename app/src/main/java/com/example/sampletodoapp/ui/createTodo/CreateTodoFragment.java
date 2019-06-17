package com.example.sampletodoapp.ui.createTodo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sampletodoapp.R;
import com.example.sampletodoapp.base.BaseActivity;
import com.example.sampletodoapp.base.BaseFragment;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.FragmentComponent;
import com.example.sampletodoapp.di.qualifiers.ForActivity;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.di.scope.FragmentScope;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTodoFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, CreateTodoContract.View {

    @BindView(R.id.etTodoHead)
    public TextInputEditText etHeadline;

    @BindView(R.id.etTodoText)
    public TextInputEditText etText;

    @BindView(R.id.etPriority)
    public TextInputEditText etPriority;

    @BindView(R.id.btnSetDeadline)
    public Button btnSetDeadline;

    @BindView(R.id.btnCreateTodo)
    public Button btnCreateTodo;

    @BindView(R.id.tvDeadlineDisplay)
    public TextView tvDeadlineDisplay;

    @Inject
    @FragmentScope
    CreateTodoContract.Presenter presenter;

    @Inject
    @ForActivity
    @ActivityScope
    Context context;

    private Calendar deadline = Calendar.getInstance();
    private Optional<AppCompatActivity> appCompatActivityOptional = Optional.empty();

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCompatActivityOptional = Optional.ofNullable((BaseActivity) getActivity());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO  Ovo radije u onCreate(), ovdje zna biti null. u on create? ovako ko gore!?

        return inflater.inflate(R.layout.fragment_create_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        setActionBar();

        setView();

    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

    @Override
    protected void inject(FragmentComponent component) {
        component.inject(this);
    }

    @OnClick(R.id.btnSetDeadline)
    void displayDeadlineText() {
        presenter.btnDeadlineClicked();

    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {
        deadline.set(Calendar.YEAR, year);
        deadline.set(Calendar.MONTH, month);
        deadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tvDeadlineDisplay.setText(String.format(getResources().getString(R.string.deadline_set_text), deadline.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.exitButtonClicked();
        }
        return true;
    }

    @OnClick(R.id.btnCreateTodo)
    void createTodo() {
        presenter.validateInput(etHeadline.getText().toString(), etText.getText().toString(), etPriority.getText().toString());
    }

    private DatePickerDialog datePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context, this, year, month, day);

    }

    @Override
    public void onTodoCreated() {
        presenter.exitButtonClicked();
    }

    @Override
    public void onHeadlineEmpty() {
        animateView(etHeadline);
        etHeadline.setError(getResources().getString(R.string.headline_error));
    }

    @Override
    public void onTextEmpty() {
        animateView(etText);
        etText.setError(getResources().getString(R.string.details_error));
    }

    @Override
    public void onPriorityEmpty() {
        animateView(etPriority);
        etPriority.setError(getResources().getString(R.string.priority_error));
    }

    @Override
    public void onInputValid() {
        presenter.formatData(Calendar.getInstance().getTimeInMillis(),
                             etHeadline.getText().toString(),
                             etText.getText().toString(),
                             deadline.getTimeInMillis(),
                             etPriority.getText().toString()
                            );
    }

    @Override
    public void onDateFragmentDisplay() {
        datePickerDialog().show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    private void setActionBar() {
        appCompatActivityOptional.ifPresent(activity -> {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(R.string.create_todo_title);
            }
        });

    }

    private void setView() {
        presenter.setCreateTodoView(this);
    }

    private void animateView(View view) {
        YoYo.with(Techniques.Shake)
            .duration(600)
            .playOn(view);
    }

}
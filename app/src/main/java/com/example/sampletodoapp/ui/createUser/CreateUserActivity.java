package com.example.sampletodoapp.ui.createUser;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Optional;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.sampletodoapp.R;
import com.example.sampletodoapp.base.BaseActivity;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.di.component.ActivityComponent;
import com.example.sampletodoapp.di.qualifiers.ForActivity;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateUserActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, CreateUserContract.View {

    @BindView(R.id.btnDateOfBirth)
    Button btnDateOfBirth;

    @BindView(R.id.tvDateOfBirth)
    TextView tvDateOfBirth;

    @BindView(R.id.btnCreateUser)
    Button btnCreateUser;

    @BindView(R.id.etName)
    TextInputEditText etName;

    @BindView(R.id.etSurname)
    TextInputEditText etSurname;

    @Inject
    @ActivityScope
    CreateUserContract.Presenter presenter;

    @Inject
    @ForActivity
    @ActivityScope
    Context context;

    private Optional<AlertDialog> progressDialog = Optional.empty();
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ButterKnife.bind(this);

        presenter.setCreateUserView(this);
    }

    @Override
    protected void inject(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return (BasePresenter) presenter;
    }

    @OnClick(R.id.btnCreateUser)
    public void btnCreateUserClicked() {
        presenter.onBtnCreateUserClicked(etName.getText().toString(),
                                         etSurname.getText().toString(),
                                         tvDateOfBirth.getText().toString(),
                                         calendar.get(Calendar.YEAR),
                                         calendar.getTimeInMillis());
    }

    @Override
    public void onInputValid() {
        showProgressDialog();
        btnCreateUser.setEnabled(false);
        btnDateOfBirth.setClickable(false);

    }

    @OnClick(R.id.btnDateOfBirth)
    public void setupDateOfBirth() {
        showDateFragment().show();
    }

    private DatePickerDialog showDateFragment() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(this, this, year, month, day);
    }

    @Override
    public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {
        tvDateOfBirth.setText(String.format(getResources().getString(R.string.birth_day), dayOfMonth, month + 1, year));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    private void showProgressDialog() {
        progressDialog = Optional.of(new AlertDialog.Builder(context)
                                             .setView(R.layout.progress_dialog)
                                             .create());
        progressDialog.get().show();
    }

    @Override
    public void onUserReady(User user) {
        presenter.userIsReady();
    }

    @Override
    public void onNameEmpty() {
        animateView(etName);
        etName.setError(getResources().getString(R.string.empty_name_error));
    }

    @Override
    public void onSurnameEmpty() {
        animateView(etSurname);
        etSurname.setError(getResources().getString(R.string.surname_empty_error));
    }

    @Override
    public void onDateOfBirthEmpty() {
        animateView(tvDateOfBirth);
        tvDateOfBirth.setText(getResources().getString(R.string.empty_date_error));
    }

    @Override
    public void onDateOfBirthNotValid() {
        animateView(tvDateOfBirth);
        Toast.makeText(this, getResources().getString(R.string.underage_toast_warrning), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        progressDialog.ifPresent(alertDialog -> alertDialog.dismiss());
        super.onDestroy();
    }

    private void animateView(View view) {
        YoYo.with(Techniques.Shake)
            .duration(600)
            .playOn(view);
    }
}

package com.example.sampletodoapp.ui.createUser;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.model.Todo;
import com.example.sampletodoapp.model.User;
import com.example.sampletodoapp.router.Router;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateUserPresenter extends BasePresenter implements CreateUserContract.Presenter {

    private static final int LEGAL_AGE = 2001;
    private static final int MINIMUM_TEXT = 1;

    private Optional<CreateUserContract.View> createUserView = Optional.empty();

    public CreateUserPresenter(final TodoDao todoDao, final Router router) {
        super(todoDao, router);
    }

    @Override
    public void setCreateUserView(CreateUserContract.View createUserView) {
        this.createUserView = Optional.ofNullable(createUserView);
    }

    @Override
    public void onBtnCreateUserClicked(final String etName, final String etSurname, final String tvDateOfBirth, final int birthYear, long dateOfBirth) {
        boolean isInputValid = true;

        if (etName.length() < MINIMUM_TEXT) {
            isInputValid = false;
            createUserView.ifPresent(CreateUserContract.View::onNameEmpty);
        }

        if (etSurname.length() < MINIMUM_TEXT) {
            isInputValid = false;
            createUserView.ifPresent(CreateUserContract.View::onSurnameEmpty);
        }

        if (tvDateOfBirth.length() < MINIMUM_TEXT) {
            isInputValid = false;
            createUserView.ifPresent(CreateUserContract.View::onDateOfBirthEmpty);
        }

        if (birthYear > LEGAL_AGE) {
            isInputValid = false;
            createUserView.ifPresent(CreateUserContract.View::onDateOfBirthNotValid);
        }

        if (isInputValid) {
            createUserView.ifPresent(view -> {
                view.onInputValid();
                createUserInDb(new User(etName, etSurname, dateOfBirth));
            });
        }
    }

    @Override
    public void userIsReady() {
        getRouter().showMainScreen();
    }

    @Override
    public void createUserInDb(User user) {
        addDisposable(Completable.fromAction(() -> getTodoDao().insertUserInDb(user))
                                 .subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(() -> createUserView.ifPresent(view -> view.onUserReady(user))));
    }
}

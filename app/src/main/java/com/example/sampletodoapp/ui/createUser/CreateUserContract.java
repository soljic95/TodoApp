package com.example.sampletodoapp.ui.createUser;

import com.example.sampletodoapp.model.User;

public interface CreateUserContract {

    interface View {

        void onUserReady(User user);

        void onNameEmpty();

        void onSurnameEmpty();

        void onDateOfBirthEmpty();

        void onDateOfBirthNotValid();

        void onInputValid();

    }

    interface Presenter {

        void createUserInDb(User user);

        void setCreateUserView(CreateUserContract.View createUserView);

        void onBtnCreateUserClicked(String etName, String etSurname, String tvDateOfBirth, int birthYear, long dateOfBirth);

        void userIsReady();

    }

}

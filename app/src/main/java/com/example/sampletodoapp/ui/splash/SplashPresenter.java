package com.example.sampletodoapp.ui.splash;

import android.os.Handler;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.base.BasePresenter;
import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.router.Router;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashPresenter extends BasePresenter implements SplashContract.Presenter {

    private static final long ONE_SECOND_DELAY = 1000;
    private Optional<SplashContract.View> splashScreenView = Optional.empty();
    private Handler handler = new Handler();
    private Router router = getRouter();

    public SplashPresenter(final TodoDao todoDao, final Router router) {
        super(todoDao, router);
    }

    public void setSplashView(SplashContract.View splashScreenView) {
        this.splashScreenView = Optional.ofNullable(splashScreenView);
    }

    @Override
    public void init() {
        addDisposable(getTodoDao().getUserFromDatabase()
                                  .subscribeOn(Schedulers.io())
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe(user -> processGetUserSucces()
                                          , throwable -> processGetUserFailure()
                                            ));
    }

    private void processGetUserSucces() {
        handler.postDelayed(() -> splashScreenView.ifPresent(view -> router.showMainScreen()), ONE_SECOND_DELAY);
    }

    private void processGetUserFailure() {
        handler.postDelayed(() -> splashScreenView.ifPresent(view -> router.showCreateUserScreen()), ONE_SECOND_DELAY);
    }

}

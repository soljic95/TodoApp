package com.example.sampletodoapp.di.module;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.sampletodoapp.data.TodoDao;
import com.example.sampletodoapp.di.qualifiers.ForActivity;
import com.example.sampletodoapp.di.scope.ActivityScope;
import com.example.sampletodoapp.main.MainActivityPresenter;
import com.example.sampletodoapp.main.MainContract;
import com.example.sampletodoapp.router.Router;
import com.example.sampletodoapp.router.RouterImpl;
import com.example.sampletodoapp.ui.createUser.CreateUserContract;
import com.example.sampletodoapp.ui.createUser.CreateUserPresenter;
import com.example.sampletodoapp.ui.splash.SplashContract;
import com.example.sampletodoapp.ui.splash.SplashPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Context context;

    public ActivityModule(final Context context) {
        this.context = context;
    }

    @Provides
    @ForActivity
    @ActivityScope
    Context provideContext() {
        return context;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return (Activity) context;
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager(Activity activity) {
        return ((AppCompatActivity) activity).getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    Router provideRouter(Activity activity, FragmentManager fragmentManager) {
        return new RouterImpl(activity, fragmentManager);
    }

    @Provides
    static public LayoutInflater provideLayoutInflater(@ForActivity Context context) {
        return LayoutInflater.from(context);
    }

    @Provides
    @ActivityScope
    static CreateUserContract.Presenter provideCreateUserPresenter(TodoDao todoDao, Router router) {
        return new CreateUserPresenter(todoDao, router);
    }

    @Provides
    @ActivityScope
    static SplashContract.Presenter provideSplash(TodoDao todoDao, Router router) {
        return new SplashPresenter(todoDao, router);
    }

    @Provides
    @ActivityScope
    static MainContract.Presenter provideMainActivityPresenter(TodoDao todoDao, Router router) {
        return new MainActivityPresenter(todoDao, router);
    }

    public interface Exposes {

        MainContract.Presenter getMainActivityPresenter();

        LayoutInflater getLayoutInflater();

        CreateUserContract.Presenter getCreateUserPresenter();

        SplashContract.Presenter getSplashPresenter();

        Router getRouter();

        @ForActivity
        Context getActivityContext();

        Activity getActivity();

    }
}

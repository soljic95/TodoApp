package com.example.sampletodoapp.base;

import android.os.Bundle;

import com.example.sampletodoapp.di.component.DaggerFragmentComponent;
import com.example.sampletodoapp.di.component.FragmentComponent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        inject(fragmentComponent);

    }

    private void initFragmentComponent() {
        fragmentComponent = DaggerFragmentComponent.builder()
                                                   .activityComponent(((BaseActivity) getActivity()).getComponent())
                                                   .build();
    }

    protected abstract BasePresenter getPresenter();

    protected abstract void inject(FragmentComponent component);

    @Override
    public void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }
}

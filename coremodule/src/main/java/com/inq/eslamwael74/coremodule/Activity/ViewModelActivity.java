package com.inq.eslamwael74.coremodule.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.inq.eslamwael74.coremodule.ViewModel.ViewModel;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public abstract class ViewModelActivity extends AppCompatActivity {

    private static final String VIEW_MODEL_STATE = "viewModelState";

    private ViewModel viewModel;

    @Nullable
    protected abstract ViewModel createViewModel(@Nullable ViewModel.State saveViewModelState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel.State state = null;

        if (savedInstanceState != null){
            state = savedInstanceState.getParcelable(VIEW_MODEL_STATE);
        }
        viewModel = createViewModel(state);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (viewModel != null){
            viewModel.onStart();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null){
            outState.putParcelable(VIEW_MODEL_STATE,viewModel.getInstanceState());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewModel != null){
            viewModel.onStop();
        }
    }
}

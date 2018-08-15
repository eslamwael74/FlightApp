package com.inq.eslamwael74.coremodule.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.inq.eslamwael74.coremodule.ViewModel.ViewModel;

/**
 * Created by EslamWael74 on 8/10/2018.
 * Email: eslamwael74@outlook.com
 */
public abstract class ViewModelFragment extends Fragment {

    private static final String VIEW_MODEL_STATE = "viewModelState";

    private ViewModel viewModel;

    @Nullable
    protected abstract ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModel.State state = null;

        if (savedInstanceState != null){
            state = savedInstanceState.getParcelable(VIEW_MODEL_STATE);
        }
        viewModel = createViewModel(state);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null){
            viewModel.onStart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null){
            outState.putParcelable(VIEW_MODEL_STATE,viewModel.getInstanceState());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModel != null){
            viewModel.onStop();
        }
    }
}

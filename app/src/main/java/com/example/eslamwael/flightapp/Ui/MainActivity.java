package com.example.eslamwael.flightapp.Ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.eslamwael.flightapp.R;
import com.example.eslamwael.flightapp.TicketViewModel;
import com.example.eslamwael.flightapp.databinding.ActivityMainBinding;
import com.inq.eslamwael74.coremodule.Activity.ViewModelActivity;
import com.inq.eslamwael74.coremodule.ViewModel.ViewModel;


public class MainActivity extends ViewModelActivity {

    TicketViewModel viewModel;
    ActivityMainBinding binding;

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State saveViewModelState) {
        viewModel = new TicketViewModel(saveViewModelState,this);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setViewModel(viewModel);

    }
}

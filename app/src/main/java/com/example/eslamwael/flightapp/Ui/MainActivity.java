package com.example.eslamwael.flightapp.Ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.eslamwael.flightapp.R;
import com.example.eslamwael.flightapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainViewModel viewModel = new MainViewModel(this);

        binding.setViewmodel(viewModel);

    }
}

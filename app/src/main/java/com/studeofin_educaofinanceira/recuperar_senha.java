package com.studeofin_educaofinanceira;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.studeofin_educaofinanceira.databinding.ActivityRecuperarSenhaBinding;

public class recuperar_senha extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecuperarSenhaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecuperarSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}
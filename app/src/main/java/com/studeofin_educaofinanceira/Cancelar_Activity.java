package com.studeofin_educaofinanceira;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.studeofin_educaofinanceira.data.model.YourPreference;
import com.studeofin_educaofinanceira.databinding.ActivityCancelarBinding;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

public class Cancelar_Activity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCancelarBinding binding;
    public YourPreference yourPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCancelarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button buttoncancelar = findViewById(R.id.cancelar);
        Button buttonOk = findViewById(R.id.ok);
        yourPrefrence = YourPreference.getInstance(Cancelar_Activity.this);
        buttoncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarButton();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirmar();
            }
        });

    }

    public void CancelarButton(){
        Intent myIntent = new Intent(Cancelar_Activity.this, DashBoard.class);
        Cancelar_Activity.this.startActivity(myIntent);
        Cancelar_Activity.this.finish();
    }

    public void Confirmar(){
        yourPrefrence.saveData("Logado",false);
        yourPrefrence.saveData("EmailUser","");
        yourPrefrence.saveData("Nome","");
        yourPrefrence.saveData("Sobrenome","");
        Intent myIntent = new Intent(Cancelar_Activity.this, LoginActivity.class);
        Cancelar_Activity.this.startActivity(myIntent);
        Toast.makeText(this, "Conta Cancelada com Sucesso", Toast.LENGTH_LONG).show();
        Cancelar_Activity.this.finish();
    }


}
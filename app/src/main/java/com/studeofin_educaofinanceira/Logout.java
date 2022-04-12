package com.studeofin_educaofinanceira;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.studeofin_educaofinanceira.data.model.YourPreference;
import com.studeofin_educaofinanceira.databinding.ActivityContaBinding;
import com.studeofin_educaofinanceira.databinding.ActivityLogoutBinding;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

public class Logout extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityLogoutBinding binding;
    public YourPreference yourPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button buttoncancelar = findViewById(R.id.cancelar);
        Button buttonOk = findViewById(R.id.ok);
        yourPrefrence = YourPreference.getInstance(Logout.this);
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
        Intent myIntent = new Intent(Logout.this, DashBoard.class);
        Logout.this.startActivity(myIntent);
        Logout.this.finish();
    }

    public void Confirmar(){
        yourPrefrence.saveData("Logado",false);
        yourPrefrence.saveData("EmailUser","");
        yourPrefrence.saveData("Nome","");
        yourPrefrence.saveData("Sobrenome","");
        Intent myIntent = new Intent(Logout.this, LoginActivity.class);
        Logout.this.startActivity(myIntent);
        Toast.makeText(this, "Logoff Efetuado Com Sucesso", Toast.LENGTH_LONG).show();
        Logout.this.finish();
    }

}

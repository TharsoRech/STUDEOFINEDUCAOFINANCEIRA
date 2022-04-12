package com.studeofin_educaofinanceira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.studeofin_educaofinanceira.databinding.ActivityRecuperarSenhaBinding;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

public class recuperar_senha extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecuperarSenhaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecuperarSenhaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Button SalvarButton = binding.Salvar;
        final EditText Email = binding.Email;
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
             if(isEmailValid(Email.getText().toString())){
                 SalvarButton.setEnabled(true);
             }
             else{
                 SalvarButton.setEnabled(false);
             }
            }
        };
        Email.addTextChangedListener(afterTextChangedListener);
        SalvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean PodeContinuar = true;
                if(Email.getText().toString().isEmpty() || !isEmailValid(Email.getText().toString())){
                    PodeContinuar = false;
                    msgbox("Erro na Recuperação de Senha de usuário","E-mail não é um e-mail válido");
                }
                if(PodeContinuar){
                    msgboxSucesso("Recuperação de Senha","E-mail de recuperação Enviado com Sucesso");
                }
            }
        });


    }

    public void msgboxSucesso(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent myIntent = new Intent(recuperar_senha.this, LoginActivity.class);
                recuperar_senha.this.startActivity(myIntent);
                recuperar_senha.this.finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return false;
        }
    }
    public void msgbox(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}
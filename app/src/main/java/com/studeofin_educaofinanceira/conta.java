package com.studeofin_educaofinanceira;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.studeofin_educaofinanceira.databinding.ActivityContaBinding;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

public class conta extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityContaBinding binding;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Button SalvarButton = binding.Salvar;
        final TextView CancelarButton = binding.CancelarConta;
        final EditText Email = binding.Email;
        final EditText Senha = binding.password;
        final EditText nome = binding.Nome;
        final EditText Sobrenome = binding.Sobrenome;
        ImageView photoButton = this.findViewById(R.id.imageView6);
        this.imageView = photoButton;
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        SalvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean PodeContinuar = true;
                if(nome.getText().toString().isEmpty() || nome.getText().toString().length() > 20){
                    PodeContinuar = false;
                    msgbox("Erro no Cadastro de usuário","Nome Contém mais que 20 caracteres ou está em Branco");
                }
                if(Sobrenome.getText().toString().isEmpty() || Sobrenome.getText().toString().length() > 30){
                    PodeContinuar = false;
                    msgbox("Erro no Cadastro de usuário","Sobrenome Contém mais que 30 caracteres ou está em Branco");
                }
                if(Senha.getText().toString().length() < 8){
                    PodeContinuar = false;
                    msgbox("Erro no Cadastro de usuário","Senha Contém menos que 8 caracteres");
                }
                if(Email.getText().toString().isEmpty() || !isEmailValid(Email.getText().toString())){
                    PodeContinuar = false;
                    msgbox("Erro no Cadastro de usuário","E-mail não é um e-mail válido");
                }
                if(PodeContinuar){
                    msgboxSucesso("Sucesso","Usuário Cadastrado com sucesso");
                }
            }
        });
        CancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(conta.this, LoginActivity.class);
                conta.this.startActivity(myIntent);
                conta.this.finish();
            }
        });
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

    public void msgboxSucesso(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent myIntent = new Intent(conta.this, LoginActivity.class);
                conta.this.startActivity(myIntent);
                conta.this.finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "Permissão de Câmera Negada", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }



}
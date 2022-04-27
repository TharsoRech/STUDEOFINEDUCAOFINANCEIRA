package com.studeofin_educaofinanceira;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.studeofin_educaofinanceira.data.model.YourPreference;
import com.studeofin_educaofinanceira.databinding.ActivityDashBoardBinding;
import com.studeofin_educaofinanceira.ui.login.LoginActivity;

public class DashBoard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashBoardBinding binding;
    public YourPreference yourPrefrence;
    private ImageView imageView;
    boolean HasFoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerview = navigationView.getHeaderView(0);
        imageView = (ImageView) headerview.findViewById(R.id.imageViewDash);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Planejamento, R.id.nav_Gerir, R.id.nav_Controle,R.id.nav_Alerta,R.id.nav_Educacional,R.id.nav_Indicadores,R.id.nav_Dicas,R.id.nav_Metas,R.id.nav_Forum,R.id.nav_Perfil,R.id.nav_Sair)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        yourPrefrence = YourPreference.getInstance(DashBoard.this);
        HasFoto = yourPrefrence.getDataBoolean("HasFoto");
        if(HasFoto){
            String image = yourPrefrence.getData("Foto");
            imageView.setImageBitmap(StringToBitMap(image));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dash_board);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Logout(MenuItem item){
        Intent myIntent = new Intent(DashBoard.this, Logout.class);
        DashBoard.this.startActivity(myIntent);
        DashBoard.this.finish();
    }

    public void Perfil(MenuItem item){
        Intent myIntent = new Intent(DashBoard.this, conta.class);
        DashBoard.this.startActivity(myIntent);
        DashBoard.this.finish();
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
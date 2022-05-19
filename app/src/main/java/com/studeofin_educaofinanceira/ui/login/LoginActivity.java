package com.studeofin_educaofinanceira.ui.login;

import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studeofin_educaofinanceira.data.LoginRepository;
import com.studeofin_educaofinanceira.data.model.YourPreference;
import com.studeofin_educaofinanceira.recuperar_senha;
import com.studeofin_educaofinanceira.conta;
import com.studeofin_educaofinanceira.DashBoard;
import com.studeofin_educaofinanceira.R;
import com.studeofin_educaofinanceira.ui.login.LoginViewModel;
import com.studeofin_educaofinanceira.ui.login.LoginViewModelFactory;
import com.studeofin_educaofinanceira.databinding.ActivityLoginBinding;

import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    public  YourPreference yourPrefrence;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final TextView EsqueceuButton = binding.EsqueceuSenha;
        final TextView CriarContaButton = binding.CriarConta;
        final TextView SairButton = binding.SairApp;
        final ProgressBar loadingProgressBar = binding.loading;
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    boolean podeLogar = false;
                    boolean logadoSucesso = false;
                    String usuarioText = usernameEditText.getText().toString();
                    String senhaText = passwordEditText.getText().toString();
                    String email = yourPrefrence.getData("EmailUser");
                    String senha = yourPrefrence.getData("Senha");
                    if((email != null && senha!= null) && (email.length() >0 && senha.length() >0)){
                        podeLogar = true;
                        logadoSucesso= (usuarioText.equals(email) && senhaText.equals(senha));
                    }
                    if(podeLogar && logadoSucesso){
                        loginViewModel.login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }
                    else{
                      msgbox("Login","Usuario ou senha incorretos");
                    }
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                }
                catch (Exception ex){
                    Toast toast=Toast. makeText(getApplicationContext(),ex.getMessage(),Toast. LENGTH_SHORT);
                    toast. setMargin(50,50);
                    toast. show();
                }
            }
        });
        CriarContaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, conta.class);
                LoginActivity.this.startActivity(myIntent);
                LoginActivity.this.finish();
            }
        });
        EsqueceuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // testegetmethod();
               Intent myIntent = new Intent(LoginActivity.this, recuperar_senha.class);
               LoginActivity.this.startActivity(myIntent);
            }
        });
        SairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 21) {
                    // If yes, run the fancy new function to end the app and
                    //  remove it from the task list.
                    finishAffinity();
                    finishAndRemoveTask();
                } else {
                    // If not, then just end the app without removing it from
                    //  the task list.
                    finish();
                    ActivityCompat.finishAffinity(LoginActivity.this);
                }
            }
        });
        yourPrefrence = YourPreference.getInstance(LoginActivity.this);
        yourPrefrence.saveData("Logado",false);

    }

    private void updateUiWithUser(LoggedInUserView model) {
        try{
           yourPrefrence.saveData("Logado",true);
          Intent myIntent = new Intent(LoginActivity.this, DashBoard.class);
          LoginActivity.this.startActivity(myIntent);
          LoginActivity.this.finish();
        }
        catch (Exception ex){
           msgbox("Erro no Login",ex.getMessage());
        }
    }

    private void testegetmethod() {
        try{
            trustEveryone();
            String url = "https://10.0.2.2:44326/api/Login/Logar?email=tharso_rech@hotmail.com&senha=teste";
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            JSONObject obj = new JSONObject();
            obj.put("id", "1");
            obj.put("name", "myname");

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            msgbox("Resppota",response.toString());
                            //yourPrefrence.saveData("Logado",true);
                            //yourPrefrence.saveData("EmailUser",model.getDisplayName());
                            Intent myIntent = new Intent(LoginActivity.this, DashBoard.class);
                            LoginActivity.this.startActivity(myIntent);
                            LoginActivity.this.finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            msgbox("Erro no Login",error.getMessage() + "Houve um erro na comunicação verifique sua internet");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    // below line we are creating a map for
                    // storing our values in key and value pair.
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("email", "tharso_rech@hotmail.com");
                    // params.put("senha", "teste");

                    // at last we are
                    // returning our params.
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

            queue.add(jsObjRequest);
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception ex) { // should never happen
            msgbox("Erro ao ativar ssl para todos",ex.getMessage());
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
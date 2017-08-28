package com.fconde.cashcolombinoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.CSVFileUser;
import com.fconde.cashcolombinoapp.models.Usuarios;

import java.io.InputStream;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText editTextLogin, editTextPassword;
    private Switch switchRecordar;
    private Button buttonLogin;
    private List<Usuarios> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
        cargaUsuarios();

        prefs = getSharedPreferences("Login", Context.MODE_PRIVATE);

        final Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        toolbar.setTitle("Identificación");
        setSupportActionBar(toolbar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString().toUpperCase().trim();
                editTextLogin.setText(login);
                String password = editTextPassword.getText().toString();
                boolean encontrado = false;

                if(isValidLogin(login, password)){
                    for(int i = 0; i < usuarios.size(); i++){
                        if(login.equals(usuarios.get(i).getNifLogin()) && password.equals(usuarios.get(i).getPassword()) && usuarios.get(i).getIsActivo().equals("true")){
                            saveOnPreferences(login, password);
                            i = usuarios.size();
                            encontrado = true;
                            goToPedidos(login, password);
                        }else{
                            if(login.equals(usuarios.get(i).getNifLogin()) && password.equals(usuarios.get(i).getPassword()) && usuarios.get(i).getIsActivo().equals("false")){
                                Toast.makeText(getApplicationContext(), "No está autorizado a usar este apartado de la app. Pongase en contacto con su representante para su activación.", Toast.LENGTH_LONG).show();
                                encontrado = true;
                                i = usuarios.size();
                            }else{
                                if(login.equals(usuarios.get(i).getNifLogin()) && !password.equals(usuarios.get(i).getPassword())){
                                    Toast.makeText(getApplicationContext(), "¡¡ PASSWORD INCORRECTO !!", Toast.LENGTH_SHORT).show();
                                    encontrado = true;
                                    i = usuarios.size();
                                }else{
                                    if(login.equals(usuarios.get(i).getNifLogin())) {
                                        encontrado = false;
                                    }
                                }
                            }
                        }
                    }

                    if(!encontrado){
                        Toast.makeText(getApplicationContext(), "Este usuario no existe. Pongase en contacto con su representante para su activación.", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Usuario y/o password incorrecto.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveOnPreferences (String login, String password){
        if(switchRecordar.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("login", login);
            editor.putString("password", password);
            editor.commit();
            editor.apply();
        }
    }

    private void bindUI(){
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        switchRecordar = (Switch) findViewById(R.id.switchRecordar);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    public void cargaUsuarios(){
        InputStream inputStreamUsuarios = getResources().openRawResource(R.raw.usuarios);
        CSVFileUser csvFileUsuarios = new CSVFileUser(inputStreamUsuarios);
        usuarios = csvFileUsuarios.read();
    }

    private boolean isValidLogin(String login, String password){
        if(!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)){
            return true;
        }else return false;
    }

    private void goToPedidos(String login, String codCliente){
        Intent intent = new Intent(LoginActivity.this, PedidosActivity.class);
        intent.putExtra("NIF", login);
        intent.putExtra("codCliente", codCliente);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

package com.mhmc.feriaiot.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mhmc.feriaiot.R;

public class Login extends AppCompatActivity {

    EditText edtTxtUser,edtTxtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
       /* edtTxtUser = findViewById(R.id.editTextText);
        edtTxtPass = findViewById(R.id.editTextTextPassword);
        if(edtTxtUser.getText().equals("172797644") && edtTxtPass.getText().equals("1234")){*/
            Intent chat = new Intent(this, Principal.class);
            startActivity(chat);
       /* }else{
            Toast.makeText(this, "Inicio de sesión erroneo", Toast.LENGTH_SHORT).show();
        }*/
    }
}
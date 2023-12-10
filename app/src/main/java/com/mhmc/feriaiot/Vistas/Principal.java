package com.mhmc.feriaiot.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mhmc.feriaiot.R;

public class Principal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toast.makeText(this, "Bienvenido!", Toast.LENGTH_SHORT).show();
    }

    public void OnClickSismo(View view){
        Intent sismos = new Intent(this, RegistroEventos.class);
        startActivity(sismos);
    }

    public void OnClickChat(View view){
        Intent chat = new Intent(this, Chat.class);
        startActivity(chat);
    }

}

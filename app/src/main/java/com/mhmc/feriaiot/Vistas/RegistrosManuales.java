package com.mhmc.feriaiot.Vistas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mhmc.feriaiot.DataAccess.DataHandler_Firestore;
import com.mhmc.feriaiot.Modelos.Eventos;
import com.mhmc.feriaiot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrosManuales extends AppCompatActivity {

    private EditText txtCodigo,txtNombre,txtDueño,txtDireccion,txtDireccion2;
    private ListView lista;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_registros_manuales);

        db = FirebaseFirestore.getInstance();
        txtCodigo=findViewById(R.id.txtCodigo);
        txtNombre=findViewById(R.id.txtNombre);
        txtDueño=findViewById(R.id.txtDueño);
        txtDireccion=findViewById(R.id.txtDireccion);
        txtDireccion2=findViewById(R.id.txtDireccion2);


    }

    public void CargarListaFirestore(View view) {
        lista=findViewById(R.id.lista);
        DataHandler_Firestore dhFs = new DataHandler_Firestore();
        ArrayList<Eventos> listaEventos = dhFs.LeerTodosLosRegistros(this);
        List<String> eventosLista = new ArrayList<>();
        for(Eventos eventos: listaEventos){
            String linea = "||"+eventos.getIdEvento()+"||"+eventos.getSensor()+"||"+eventos.getTimestamp()+"||"+eventos.getUbicacionN()+"-"+eventos.getUbicacionN()+"||";
            eventosLista.add(linea);
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(RegistrosManuales.this, android.R.layout.simple_list_item_1,eventosLista);
        lista.setAdapter(adaptador);

    }

    public void guardarDatos(View view){
        Eventos eventos = new Eventos(txtCodigo.getText().toString(),txtNombre.getText().toString(),txtDueño.getText().toString(),txtDireccion.getText().toString(),txtDireccion2.getText().toString());
        DataHandler_Firestore dhFirestore = new DataHandler_Firestore();
        dhFirestore.crearRegistro(eventos);
    }



}

package com.mhmc.feriaiot.Vistas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;
import com.mhmc.feriaiot.Adaptadores.ListaEventoTeluricoAdapter;
import com.mhmc.feriaiot.DataAccess.DataHandler_Firestore;
import com.mhmc.feriaiot.Modelos.Eventos;
import com.mhmc.feriaiot.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistroEventos extends AppCompatActivity {

    private static String mqtthost = "tcp://mqtt-androidvpa.cloud.shiftr.io:1883";
    private static String idUsuario = "mqtt-androidvpa";
    private static String topico = "Mensaje";
    private static String User = "mqtt-androidvpa";
    private static String Pass = "xB5wIhZJpOr9ZMHs";

    private MqttClient mqttClient;
    Button boton;
    EditText editText;
    Context context;
    ListaEventoTeluricoAdapter listaEventoTeluricoAdapter;

    @Override
    public void closeContextMenu() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_eventos);
        try {
            init();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            mqttClient = new MqttClient(mqtthost, idUsuario, null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(User);
            options.setPassword(Pass.toCharArray());
            mqttClient.connect(options);
            Toast.makeText(this, "Cliente conectado a MQTT", Toast.LENGTH_SHORT).show();
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Conexión Perdida");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());

                    DataHandler_Firestore dataHandlerFirestore = new DataHandler_Firestore();
                    Date date = new Date();
                    Eventos eventos = new Eventos(String.valueOf(Math.random()), String.valueOf(date.getTime()), "LAB01", "IPST", "Hall de acceso");
                    dataHandlerFirestore.crearRegistro(eventos);
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT", "Entrega Completa");
                }
            });
            mqttClient.subscribe(topico);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            try{
                mqttClient.disconnect();
            }catch (MqttException e){
                e.printStackTrace();
            }
        }
        
    private void init() throws InterruptedException {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Obteniendo Datos...");
        pd.show();
        DataHandler_Firestore dataHandlerFirestore = new DataHandler_Firestore();
        ArrayList<Eventos> listaEventos = dataHandlerFirestore.LeerTodosLosRegistros(this);

        ListaEventoTeluricoAdapter listaEventoTeluricoAdapter = new ListaEventoTeluricoAdapter(listaEventos, this, new ListaEventoTeluricoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Eventos item) {
                Toast.makeText(RegistroEventos.this, "No hay más datos disponibles por el momento", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerEventos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaEventoTeluricoAdapter);
        pd.dismiss();
    }

    public void irRegistrosManuales(View view){
        Intent registrosManuales = new Intent(this, RegistrosManuales.class);
        startActivity(registrosManuales);

    }
}

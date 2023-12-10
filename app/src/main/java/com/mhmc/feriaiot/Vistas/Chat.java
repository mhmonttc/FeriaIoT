package com.mhmc.feriaiot.Vistas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mhmc.feriaiot.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    private static String mqtthost ="tcp://mqttmhmc.cloud.shiftr.io:1883";
    private static String idUsuario ="AppAndroid";
    private static String topico = "Mensaje";
    private static String User="mqttmhmc";
    private static String Pass="0y9b5hvHgoZNSIOR";

    private ListView listView;
    private EditText editTextMessage;
    private Button boton;
    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes_mqtt);

        listView = (ListView) findViewById(R.id.lstChat);
        editTextMessage = findViewById(R.id.editTextText2);
        boton = findViewById(R.id.btnEnviarMensaje);
        ArrayList<String> listaMacroEventos = new ArrayList<>();
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(Chat.this, android.R.layout.simple_list_item_1,listaMacroEventos);
        listView.setAdapter(adaptador);

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
                        listaMacroEventos.add("UsuarioX:" + " " + payload);
                        Log.e("MENSAJE RECIBIDO:", listaMacroEventos.toString());
                        adaptador.notifyDataSetChanged();
                        listView.setAdapter(adaptador);
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        Log.d("MQTT", "Entrega Completa");
                    }
                });
                mqttClient.subscribe(topico);
                boton.setOnClickListener(v -> {
                    String message = editTextMessage.getText().toString();
                    if (!message.isEmpty()) {
                        sendMessage(message);
                        editTextMessage.getText().clear();
                    } else {
                        Toast.makeText(this, "Ingrese en el mensaje", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }


        private void sendMessage(String message) {
            try{
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttClient.publish(topico,mqttMessage);
            }catch (MqttException e){
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
    }






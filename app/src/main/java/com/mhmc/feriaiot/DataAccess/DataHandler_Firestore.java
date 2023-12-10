package com.mhmc.feriaiot.DataAccess;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mhmc.feriaiot.Modelos.Eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataHandler_Firestore {
    private boolean resultado;
    private FirebaseFirestore db;

    public synchronized ArrayList<Eventos> LeerTodosLosRegistros(Context context){
        ArrayList<Eventos> listaEventos = new ArrayList<>();
        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        db2.collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document: task.getResult()){
                            listaEventos.add(new Eventos(document.getString("id"),document.getString("timestamp"), document.getString("sensor"), document.getString("ubicacionN"), document.getString("ubicacionS")));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        return listaEventos;
    }

    public boolean crearRegistro(Eventos evento){
        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        Map<String,Object> productoMap = new HashMap<>();
        productoMap.put("id",evento.getIdEvento());
        productoMap.put("timestamp",evento.getTimestamp());
        productoMap.put("sensor",evento.getSensor());
        productoMap.put("ubicacionN",evento.getUbicacionN());
        productoMap.put("ubicacionS",evento.getUbicacionS());

        db2.collection("eventos")
                .document()
                .set(productoMap)
                .addOnSuccessListener(e ->{
                    Log.e("Sucess",e.toString());
                    resultado=true;
                })
                .addOnFailureListener(e->{
                    Log.e("Error",e.getMessage());
                    resultado=false;
                });
        return resultado;
    }
    public boolean borrarRegistro(String id){
        FirebaseFirestore db2 = FirebaseFirestore.getInstance();
        db2.collection("eventos")
                .document(id)
                .delete()
                .addOnSuccessListener(e ->{
                    Log.e("Sucess",e.toString());
                    resultado=true;
                })
                .addOnFailureListener(e->{
                    Log.e("Error",e.getMessage());
                    resultado=false;
                });
        return resultado;
    }


}

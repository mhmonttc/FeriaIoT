package com.mhmc.feriaiot.DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mhmc.feriaiot.Modelos.Eventos;

import java.util.ArrayList;
import java.util.Date;

public class EventosCRUD {

    public long CrearEvento(int id,Date timestamp, String sensor, String ubicacionN,String ubicacionS, Context context){
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd =dh.getWritableDatabase();
        ContentValues reg=new ContentValues();
        reg.put("id",id);
        reg.put("timestamp",timestamp.toString());
        reg.put("sensor",sensor);
        reg.put("ubicacionN",ubicacionN);
        reg.put("ubicacionS",ubicacionS);
        long resp =bd.insert("eventos",null,reg);
        CerrarConexión(context);
        return resp;
    }

    public long BorrarEvento(int id,Context context){
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd =dh.getWritableDatabase();
        long resp = bd.delete("eventos","id="+id,null);
        CerrarConexión(context);
        return resp;
    }

    public long EditarEvento(int id,Date timestamp, String sensor, String ubicacionN,String ubicacionS, Context context){
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        ContentValues reg= new ContentValues();
        reg.put("id",id);
        reg.put("timestamp",timestamp.toString());
        reg.put("sensor",sensor);
        reg.put("ubicacionN",ubicacionN);
        reg.put("ubicacionS",ubicacionS);;
        long resp =bd.update("eventos",reg,"id=?", new String[]{String.valueOf(id)});
        CerrarConexión(context);
        return resp;
    }

    public Cursor TodosLosEventos(Context context) {
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd =dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT id, timestamp,sensor,ubicacionN,ubicacionS from eventos",null);
        return c;
    }

    public void CerrarConexión(Context context){
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd = dh.getWritableDatabase();
        bd.close();
    }

    /*public ArrayList<Eventos> LosEventosDeCiertaCategoria(String nombreCategoria, Context context){
        ArrayList<Eventos> prodList = new ArrayList<>();
        DataHandler_Sqlite dh =new DataHandler_Sqlite(context,"feriaiot.db",null,1);
        SQLiteDatabase bd =dh.getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT id,nombre,cantidad,tienda,categoria FROM Eventos WHERE categoria=?",new String[]{nombreCategoria});
        if (c.moveToFirst()) {
            do {
                String id_PX = c.getString(0);
                String nombre_PX = c.getString(1);
                int cantPX = c.getInt(2);
                String tiendaPX = c.getString(3);
                String categoriaPX = c.getString(4);
                Eventos prod = new Eventos(id_PX, nombre_PX, cantPX, tiendaPX, categoriaPX);
                prodList.add(prod);
            } while (c.moveToNext());
        }
        return prodList;
    }*/


}

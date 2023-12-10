package com.mhmc.feriaiot.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmc.feriaiot.Modelos.Eventos;
import com.mhmc.feriaiot.R;

import java.util.ArrayList;
import java.util.List;

public class ListaEventoTeluricoAdapter extends RecyclerView.Adapter<ListaEventoTeluricoAdapter.ViewHolder> {

        private ArrayList<Eventos> datos;
        private LayoutInflater layoutInflater;
        private Context context;
        final ListaEventoTeluricoAdapter.OnItemClickListener listener;

        public interface OnItemClickListener{
            void onItemClick(Eventos item);
        }

        public ListaEventoTeluricoAdapter(ArrayList<Eventos> itemList, Context context, OnItemClickListener listen) {
            this.datos = itemList;
            this.layoutInflater = LayoutInflater.from(context);
            this.context = context;
            this.listener=listen;
        }

        @NonNull
        @Override
        public ListaEventoTeluricoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.cardview_registro_eventos,null);
            return new ListaEventoTeluricoAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListaEventoTeluricoAdapter.ViewHolder holder, int position) {
            holder.bindData(datos.get(position));
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        public void setItems(ArrayList<Eventos> itemList){datos=itemList;}

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txtIdEvento, txtUbicacion, txtSensor, txtTimestamp;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtIdEvento = itemView.findViewById(R.id.txtIdEvento);
                txtUbicacion = itemView.findViewById(R.id.txtUbicacion);
                txtSensor = itemView.findViewById(R.id.txtSensor);
                txtTimestamp = itemView.findViewById(R.id.txtTimestamp);

            }
                void bindData(final Eventos item){

                    txtIdEvento.setText(String.valueOf(item.getIdEvento()));
                    txtUbicacion.setText(item.getUbicacionN().toString() + "," + item.getUbicacionS().toString());
                    txtSensor.setText(item.getSensor());
                    txtTimestamp.setText(item.getTimestamp().toString());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onItemClick(item);
                        }
                    });
                }
            }

}

package com.example.listviewpersonalizado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

    public abstract class Adaptador<T> extends BaseAdapter {

        private ArrayList<T> entradas;
        private int R_Layout_IdView;
        private Context contexto;

        public Adaptador(Context contexto, int R_Layout_IdView, ArrayList<T> entradas){
            super();
            this.contexto = contexto;
            this.entradas = entradas;
            this.R_Layout_IdView = R_Layout_IdView;
        }

        public abstract void onEntrada(Object entrada, View view);

        public int getCount(){
                return entradas.size();
        }
        public Object getItem(int posicion){
                return entradas.get(posicion);
        }
        public long getItemId(int posicion){
                return posicion;
        }
        public View getView(int posicion, View view, ViewGroup pariente) {
            if (view == null) {
                LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R_Layout_IdView, pariente, false);
            }
            onEntrada(getItem(posicion), view);
            return view;
        }

    }

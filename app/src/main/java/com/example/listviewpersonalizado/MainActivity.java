package com.example.listviewpersonalizado;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private TextView texto;
    private RadioButton radioButton_pulsado;
    private AdmBaseDatosSQLite dbHelper;
    private ArrayList<Encapsulador> datosPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lista = findViewById(R.id.listView);
        texto = findViewById(R.id.textViewInfo);

        dbHelper = new AdmBaseDatosSQLite(this);

        inicializarDatosPokemon();
        Button insertarButton = findViewById(R.id.insertarButton);
        Button borrarButton = findViewById(R.id.borrarButton);
        Button listarButton = findViewById(R.id.listarButton);

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos();
            }
        });

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarDatos();
            }
        });

        listarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarDatos();
            }
        });

        actualizarListView(datosPokemon);
    }

    private void inicializarDatosPokemon() {
        datosPokemon = new ArrayList<>();
        datosPokemon.add(new Encapsulador(R.drawable.mewtwo, "Mewtwo", "Psíquico", "Presión", "Descripción Mewtwo", false));
        datosPokemon.add(new Encapsulador(R.drawable.infernape, "Infernape", "Fuego/Lucha", "Mar llamas", "Descripción Infernape", false));
        datosPokemon.add(new Encapsulador(R.drawable.quagsire, "Quagsire", "Agua/Tierra", "Absorbe agua", "Descripción Quagsire", false));
        datosPokemon.add(new Encapsulador(R.drawable.arceus, "Arceus", "Normal", "Multitipo", "Descripción Arceus", false));
        datosPokemon.add(new Encapsulador(R.drawable.umbreon, "Umbreon", "Siniestro", "Sincronía", "Descripción Umbreon", false));
        datosPokemon.add(new Encapsulador(R.drawable.gengar, "Gengar", "Fantasma/Veneno", "Levitación", "Descripción Gengar", false));
        datosPokemon.add(new Encapsulador(R.drawable.rayquaza, "Rayquaza", "Dragón/Volador", "Esclusa de Aire", "Descripción Rayquaza", false));
        datosPokemon.add(new Encapsulador(R.drawable.aurorus, "Aurorus", "Roca/Hielo", "Piel Helada", "Descripción Aurorus", false));
        datosPokemon.add(new Encapsulador(R.drawable.smoliv, "Smoliv", "Planta/Normal", "Madrugar", "Descripción Smoliv", false));
        datosPokemon.add(new Encapsulador(R.drawable.scorbunny, "Scorbunny", "Fuego", "Mar Llamas", "Descripción Scorbunny", false));
    }


    private void actualizarListView(ArrayList<Encapsulador> datos) {
        lista.setAdapter(new Adaptador(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = view.findViewById(R.id.texto_titulo);
                    TextView texto_tipo = view.findViewById(R.id.texto_tipo);
                    TextView texto_habilidad = view.findViewById(R.id.texto_habilidad);
                    TextView texto_inferior_entrada = view.findViewById(R.id.texto_datos);
                    ImageView imagen_entrada = view.findViewById(R.id.imagen);
                    RadioButton miRadio = view.findViewById(R.id.boton);

                    texto_superior_entrada.setText(((Encapsulador) entrada).get_textoTitulo());
                    texto_tipo.setText("Tipo: " + ((Encapsulador) entrada).get_tipo());
                    texto_habilidad.setText("Habilidad: " + ((Encapsulador) entrada).get_habilidad());
                    texto_inferior_entrada.setText(((Encapsulador) entrada).get_textoContenido());
                    imagen_entrada.setImageResource(((Encapsulador) entrada).get_idImagen());

                    miRadio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (radioButton_pulsado != null) radioButton_pulsado.setChecked(false);
                            radioButton_pulsado = (RadioButton) v;
                            texto.setText("Pokémon seleccionado: " + ((Encapsulador) entrada).get_textoTitulo());
                        }
                    });
                }
            }
        });
    }

    private void insertarDatos() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Encapsulador pokemon : datosPokemon) {
            ContentValues values = new ContentValues();
            values.put(AdmBaseDatosSQLite.COLUMN_NAME, pokemon.get_textoTitulo());
            values.put(AdmBaseDatosSQLite.COLUMN_TIPO, pokemon.get_tipo());
            values.put(AdmBaseDatosSQLite.COLUMN_HABILIDAD, pokemon.get_habilidad());
            values.put(AdmBaseDatosSQLite.COLUMN_DESCRIPCION, pokemon.get_textoContenido());
            values.put(AdmBaseDatosSQLite.COLUMN_IMAGEN, pokemon.get_idImagen());
            db.insert(AdmBaseDatosSQLite.TABLE_POKEMON, null, values);
        }

        texto.setText("Pokémon insertados");
    }

    private void borrarDatos() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(AdmBaseDatosSQLite.TABLE_POKEMON, null, null);
        texto.setText("Pokémon borrados");
    }

    private void listarDatos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AdmBaseDatosSQLite.TABLE_POKEMON, null, null, null, null, null, null);

        ArrayList<Encapsulador> datosDB = new ArrayList<>();
        while (cursor.moveToNext()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(AdmBaseDatosSQLite.COLUMN_NAME));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(AdmBaseDatosSQLite.COLUMN_TIPO));
            String habilidad = cursor.getString(cursor.getColumnIndexOrThrow(AdmBaseDatosSQLite.COLUMN_HABILIDAD));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(AdmBaseDatosSQLite.COLUMN_DESCRIPCION));
            int imagen = cursor.getInt(cursor.getColumnIndexOrThrow(AdmBaseDatosSQLite.COLUMN_IMAGEN));

            datosDB.add(new Encapsulador(imagen, nombre, tipo, habilidad, descripcion, false));
        }
        cursor.close();

        actualizarListView(datosDB);
        texto.setText("Pokémon listados");
    }

    public class Encapsulador {
        private int imagen;
        private String titulo;
        private String tipo;
        private String habilidad;
        private String texto;
        private boolean dato1;

        public Encapsulador(int idImagen, String textoTitulo, String tipo, String habilidad, String textoContenido, boolean favorito) {
            this.imagen = idImagen;
            this.titulo = textoTitulo;
            this.tipo = tipo;
            this.habilidad = habilidad;
            this.texto = textoContenido;
            this.dato1 = favorito;
        }

        public int get_idImagen() {
            return imagen;
        }

        public String get_textoTitulo() {
            return titulo;
        }

        public String get_tipo() {
            return tipo;
        }

        public String get_habilidad() {
            return habilidad;
        }

        public String get_textoContenido() {
            return texto;
        }

        public boolean get_checkBox1() {
            return dato1;
        }
    }
}

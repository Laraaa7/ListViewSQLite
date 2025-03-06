package com.example.listviewpersonalizado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdmBaseDatosSQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PokemonDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POKEMON = "pokemon";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "nombre";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_HABILIDAD = "habilidad";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_IMAGEN = "imagen";

    public AdmBaseDatosSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POKEMON_TABLE = "CREATE TABLE " + TABLE_POKEMON + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_TIPO + " TEXT,"
                + COLUMN_HABILIDAD + " TEXT,"
                + COLUMN_DESCRIPCION + " TEXT,"
                + COLUMN_IMAGEN + " INTEGER" + ")";
        db.execSQL(CREATE_POKEMON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKEMON);
        onCreate(db);
    }
}
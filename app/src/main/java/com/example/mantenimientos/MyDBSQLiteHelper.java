package com.example.mantenimientos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBSQLiteHelper extends SQLiteOpenHelper {

    public MyDBSQLiteHelper(Context context, String nombre, SQLiteDatabase.CursorFactory C, int version) {
        super(context, nombre, C, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE formulario(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, codigo TEXT, estado TEXT, tipoEqu TEXT, marca TEXT, serial TEXT, probHardware TEXT, probSoftware TEXT, especificacion TEXT, solucionProb TEXT, imagen BLOB)");
        db.execSQL("CREATE TABLE perfil(_id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT, nombre TEXT, apellido TEXT, genero TEXT, fecna TEXT, cargo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS formulario");
        db.execSQL("DROP TABLE IF EXISTS perfil");
        db.execSQL("CREATE TABLE formulario(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, codigo TEXT, estado TEXT, tipoEqu TEXT, marca TEXT, serial TEXT, probHardware TEXT, probSoftware TEXT, especificacion TEXT, solucionProb TEXT, imagen BLOB)");
        db.execSQL("CREATE TABLE perfil(_id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT, nombre TEXT, apellido TEXT, genero TEXT, fecna TEXT, cargo TEXT)");
    }
}

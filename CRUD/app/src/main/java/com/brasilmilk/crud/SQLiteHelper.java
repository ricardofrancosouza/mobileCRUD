package com.brasilmilk.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String NOME_BD = "APP_VENDA";
    public static  final  int VERSAO_BD = 1;
    private String scriptCreate;
    private String scriptDelete;
    public SQLiteHelper(Context context, String nome_bd, int versao_bd,  String scriptCreate, String scriptDelete) {
        super(context, nome_bd, null, versao_bd);
        this.scriptCreate = scriptCreate;
        this.scriptDelete = scriptDelete;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.scriptCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(scriptDelete);
        onCreate(db);
    }
}

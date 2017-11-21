package com.brasilmilk.crud;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.graphics.ColorSpace;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricar on 19/11/2017.
 */

public class DAOVendedor {
    private  String scriptSQLCreate ="CREATE TABLE VENDEDOR (ID_VENDEDOR INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "NOME VARCHAR(50), ATIVO CHAR(1), TIPO VARCHAR(20));";
    private  String scriptSQLDelete = "DROP TABLE IF EXISTS VENDEDOR";
    private static  final String NOME_TABETA = "VENDEDOR";

    private SQLiteHelper dbHelper;
    private SQLiteDatabase db;
    private List<ModelVendedor> listaVendedores;
    private String[] getColunasTabVendedores(){
        String[] VENDEDOR_COLUNAS_TAB_VENDEDOR = new String[]{"ID_VENDEDOR", "NOME", "ATIVO", "TIPO"};
        return VENDEDOR_COLUNAS_TAB_VENDEDOR;
    }

    public DAOVendedor(Context ctx){
       try {
            dbHelper = new SQLiteHelper(ctx, SQLiteHelper.NOME_BD, SQLiteHelper.VERSAO_BD, this.scriptSQLCreate, this.scriptSQLDelete);
            this.listaVendedores = new ArrayList<ModelVendedor>();
            ///metodo para criacao dos banco e tabelas
            db = dbHelper.getWritableDatabase();
       }catch (Exception e){
           Log.e("Erro: ", e.getMessage());
       }
    }

    public void close(){
        if(db != null){
            if(!db.isOpen()){
                db.close();
            }
        }
    }
    public List<ModelVendedor> listVendedores(){
        Cursor cursor = null;
        listaVendedores.clear();
        try {
            cursor = db.query(NOME_TABETA, getColunasTabVendedores(), null,null,null,null,
                    "ID_VENDEDOR DESC",null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    ModelVendedor vendedorLinha = new ModelVendedor();
                    vendedorLinha.setID_VENDEDOR(cursor.getInt(cursor.getColumnIndex("ID_VENDEDOR")));
                    vendedorLinha.setATIVO(cursor.getString(cursor.getColumnIndex("ATIVO")));
                    vendedorLinha.setNOME(cursor.getString(cursor.getColumnIndex("NOME")));
                    vendedorLinha.setTIPO(cursor.getString(cursor.getColumnIndex("TIPO")));
                    listaVendedores.add(vendedorLinha);

                }
            }
        }catch (Exception e){
            Log.e("Erro: ",e.getMessage());
        }
        finally {
            if(cursor != null){
                if(!cursor.isClosed()){
                    cursor.close();
                }
            }
        }
        return this.listaVendedores;
    }
    public ContentValues contentVendedor (ModelVendedor vendedor){
        ContentValues values = new ContentValues();
        values.put("ID_VENDEDOR", vendedor.getID_VENDEDOR());
        values.put("NOME", vendedor.getNOME());
        values.put("ATIVO", vendedor.getATIVO());
        values.put("TIPO", vendedor.getTIPO());
        return  values;
    }
    // Insert
    public long insertVendedor(ModelVendedor novoVendedor){
        long id = 0;
        try {
            ContentValues values = contentVendedor(novoVendedor);
            id = db.insert(NOME_TABETA,null,values);

        }catch (Exception e){
            Log.e("Erro: ", e.getMessage());
        }
        return id;
    }
    public boolean excluirVendedor(String ID_VENDEDOR){
        boolean resultadoExclusao= false;
        try {
            String where = "ID_VENDEDOR=?";
            String[] args = new String[]{ID_VENDEDOR};
            int num = db.delete(NOME_TABETA, where, args);
            if(num == 1){
                resultadoExclusao = true;
            }
        }catch (Exception e){
            Log.e("Erro: ", e.getMessage());
        }
        return resultadoExclusao;
    }
    public boolean alterarVendedor(ModelVendedor objVendedor){
        boolean resultadoAlteracao = false;
        try {
            String where = "ID_VENDEDOR=?";
            String[]args = new String[]{String.valueOf(objVendedor.getID_VENDEDOR())};
            int num = db.update(NOME_TABETA, contentVendedor(objVendedor), where,args);
        } catch (Exception e){
            Log.e("Erro: ", e.toString());
        }
        return resultadoAlteracao;
    }

    public ModelVendedor buscaIndividualVendedor(String nome){
        Cursor cursor = null;
        ModelVendedor vendedorLinha = new ModelVendedor();
        String where = "NOME = ?";
        String[] args = new String[]{nome};
        try {
            cursor = db.query(NOME_TABETA, getColunasTabVendedores(), where, args, null, null, null);
            if(cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    vendedorLinha.setID_VENDEDOR(cursor.getInt(cursor.getColumnIndex("ID_VENDEDOR")));
                    vendedorLinha.setNOME(cursor.getString(cursor.getColumnIndex("NOME")));
                    vendedorLinha.setTIPO(cursor.getString(cursor.getColumnIndex("TIPO")));
                    vendedorLinha.setATIVO(cursor.getString(cursor.getColumnIndex("ATIVO")));
                }
            }

        }catch (Exception e){
            Log.e("Erro: ", e.toString());
        }finally {
            if(cursor != null){
                if(!cursor.isClosed()){
                    cursor.close();
                }

            }
        }
        return vendedorLinha;
    }

}

package me.br.devproject.repository;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import me.br.devproject.util.Constantes;
import me.br.devproject.util.Util;

/**
 * Created by ricks on 18/05/2016.
 */
public class LoginRepository extends SQLiteOpenHelper {

    public LoginRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE TB_LOGIN( ");
        query.append(" ID_LOGIN INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" USUARIO TEXT(15) NOT NULL,");
        query.append(" SENHA TEXT(15) NOT NULL)");

        db.execSQL(query.toString());

        popularDB(db);


        StringBuilder queryPessoa = new StringBuilder();
        queryPessoa.append("CREATE TABLE IF NOT EXISTS TB_PESSOA( ");
        queryPessoa.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        queryPessoa.append(" NOME TEXT(30) NOT NULL,");
        queryPessoa.append(" ENDERECO TEXT(50),");
        queryPessoa.append(" CPF TEXT(14),");
        queryPessoa.append(" CNPJ TEXT(14),");
        queryPessoa.append(" SEXO INTEGER(1) NOT NULL,");
        queryPessoa.append(" PROFISSAO INTEGER(3) NOT NULL,");
        queryPessoa.append(" DT_NASC INTEGER NOT NULL)");

        db.execSQL(queryPessoa.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void popularDB(SQLiteDatabase db){
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO TB_LOGIN(USUARIO, SENHA) VALUES (?, ?)");

        //String[] params =  {"admin", "admin"}; Abaixo a mesma implementacao
        db.execSQL(query.toString(), new String[] {"admin", "admin"});
    }

    public void listarLogin(Activity activity){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("TB_LOGIN", null, "ID_LOGIN = ? and USUARIO = ?", new String[]{"1", "admin"}, null, null, "USUARIO");
        while(cursor.moveToNext()){
            String txtID = "ID de Usuario: " + String.valueOf(cursor.getInt(cursor.getColumnIndex("ID_LOGIN")));
            Util.showMsgToast(activity, txtID);
        }
    }

    public void addLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.insert("TB_LOGIN", null, contentValues);
    }

    public void updateLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USUARIO", login);
        contentValues.put("SENHA", senha);

        db.update("TB_LOGIN", contentValues, "ID_LOGIN > 1", null);
    }

    public void deleteLogin(String login, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TB_LOGIN", "USUARIO = ? OR SENHA = ?", new String[] {login, senha});
    }

}

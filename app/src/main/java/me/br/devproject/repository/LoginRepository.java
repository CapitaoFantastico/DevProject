package me.br.devproject.repository;

import android.app.Activity;
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

}

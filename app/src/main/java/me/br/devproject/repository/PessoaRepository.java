package me.br.devproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.br.devproject.entidade.Pessoa;
import me.br.devproject.entidade.Profissao;
import me.br.devproject.entidade.Sexo;
import me.br.devproject.entidade.TipoPessoa;
import me.br.devproject.util.Constantes;

/**
 * Created by ricks on 22/06/2016.
 */
public class PessoaRepository extends SQLiteOpenHelper {

    public PessoaRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_PESSOA( ");
        query.append(" ID_PESSOA INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" NOME TEXT(30) NOT NULL,");
        query.append(" ENDERECO TEXT(50),");
        query.append(" CPF TEXT(14),");
        query.append(" CNPJ TEXT(14),");
        query.append(" SEXO INTEGER(1) NOT NULL,");
        query.append(" PROFISSAO INTEGER(3) NOT NULL,");
        query.append(" DT_NASC INTEGER NOT NULL)");

        db.execSQL(query.toString());
    }

    public void salvarPessoa(Pessoa pessoa){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", pessoa.getNome());
        contentValues.put("ENDERECO", pessoa.getEndereco());
    switch(pessoa.getTipoPessoa()){
        case FISICA:
            contentValues.put("CPF", pessoa.getCpfCnpj());
            break;

        case JURIDICA:
            contentValues.put("CNPJ", pessoa.getCpfCnpj());
            break;
    }
        contentValues.put("SEXO", pessoa.getSexo().ordinal());
        contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
        contentValues.put("DT_NASC", pessoa.getDtNasc().getTime());

        db.insert("TB_PESSOA",null,contentValues);
    }

    public List<Pessoa> listarPessoas(){
        List<Pessoa> lista = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA",null, null, null, null, null,"NOME");

        while(cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(cursor.getInt(cursor.getColumnIndex("ID_PESSOA")));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("ID_PESSOA")));
            String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
            String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
            if(cpf != null){
                pessoa.setTipoPessoa(TipoPessoa.FISICA);
                pessoa.setCpfCnpj(cpf);
            } else {
                pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
                pessoa.setCpfCnpj(cnpj);
            }
            int sexo = cursor.getInt(cursor.getColumnIndex("SEXO"));
            pessoa.setSexo(Sexo.getSexo(sexo));
            int profissao = cursor.getInt(cursor.getColumnIndex("PROFISSAO"));
            pessoa.setProfissao(Profissao.getProfissao(profissao));

            int time = cursor.getInt(cursor.getColumnIndex("DT_NASC"));
            Date dtNasc = new Date();
            dtNasc.setTime(time);
            pessoa.setDtNasc(dtNasc);

            lista.add(pessoa);
        }

        return lista;
    }

}

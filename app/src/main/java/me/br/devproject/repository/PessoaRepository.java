package me.br.devproject.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

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
        ContentValues contentValues = getContentValuesPessoa(pessoa);

        db.insert("TB_PESSOA",null,contentValues);
    }

    @NonNull
    private ContentValues getContentValuesPessoa(Pessoa pessoa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", pessoa.getNome());
        contentValues.put("ENDERECO", pessoa.getEndereco());
        switch(pessoa.getTipoPessoa()){
            case FISICA:
                contentValues.put("CPF", pessoa.getCpfCnpj());
                contentValues.put("CNPJ", "");
                break;

            case JURIDICA:
                contentValues.put("CNPJ", pessoa.getCpfCnpj());
                contentValues.put("CPF", "");
                break;
        }
        contentValues.put("SEXO", pessoa.getSexo().ordinal());
        contentValues.put("PROFISSAO", pessoa.getProfissao().ordinal());
        contentValues.put("DT_NASC", pessoa.getDtNasc().getTime());
        return contentValues;
    }

    public List<Pessoa> listarPessoas(){
        List<Pessoa> lista = new ArrayList<Pessoa>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_PESSOA",null, null, null, null, null,"NOME");

        while(cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            setPessoaFromCursor(cursor, pessoa);

            lista.add(pessoa);
        }

        return lista;
    }

    public void atualizarPessoa(Pessoa pessoa){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = getContentValuesPessoa(pessoa);
        db.update("TB_PESSOA", contentValues, "ID_PESSOA = ?", new String[]{String.valueOf(pessoa.getIdPessoa())});
    }

    public Pessoa consultarPessoaPorID(int idPessoa){
        Pessoa pessoa = new Pessoa();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("TB_PESSOA", null, "ID_PESSOA = ?", new String[]{String.valueOf(idPessoa)},null,null,"NOME");

        if(cursor.moveToNext()){
            setPessoaFromCursor(cursor, pessoa);
        }

        return pessoa;
    }

    private void setPessoaFromCursor(Cursor cursor, Pessoa pessoa) {
        pessoa.setIdPessoa(cursor.getInt(cursor.getColumnIndex("ID_PESSOA")));
        pessoa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
        pessoa.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
        String cpf = cursor.getString(cursor.getColumnIndex("CPF"));
        String cnpj = cursor.getString(cursor.getColumnIndex("CNPJ"));
        if(cpf != null && !"".equals(cpf)){
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

        long time = cursor.getLong(cursor.getColumnIndex("DT_NASC"));
        Date dtNasc = new Date();
        dtNasc.setTime(time);
        pessoa.setDtNasc(dtNasc);
    }

    public void removerPessoaPorId(int idPessoa){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("TB_PESSOA", "ID_PESSOA = ?", new String[]{String.valueOf(idPessoa)} );
    }

}

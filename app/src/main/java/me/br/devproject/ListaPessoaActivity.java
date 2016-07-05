package me.br.devproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.br.devproject.entidade.Pessoa;
import me.br.devproject.repository.PessoaRepository;
import me.br.devproject.util.TipoMsg;
import me.br.devproject.util.Util;

public class ListaPessoaActivity extends AppCompatActivity {

    private ListView lstPessoas;

    private List<Pessoa> listaPessoas;

    private int posicaoSelecionada;

    private ArrayAdapter adapter;

    private PessoaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pessoa);
        getSupportActionBar().setTitle("Lista de Pessoas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        repository = new PessoaRepository(this);

        lstPessoas = (ListView) findViewById(R.id.lstPessoas);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        setArrayAdapterPessoas();
        lstPessoas.setOnItemClickListener(clickListenerPessoas);
        lstPessoas.setOnCreateContextMenuListener(contextMenuListener);
        lstPessoas.setOnItemLongClickListener(onItemLongClickListener);

    }

    private void setArrayAdapterPessoas() {
        listaPessoas = repository.listarPessoas();

        List<String> valores = new ArrayList<>();
        for(Pessoa p : listaPessoas){
            valores.add(p.getNome());
        }

        adapter.clear();
        adapter.addAll(valores);
        lstPessoas.setAdapter(adapter);
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            posicaoSelecionada = position;
            return false;
        }
    };

    private View.OnCreateContextMenuListener contextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Opcoes").setHeaderIcon(R.drawable.edit).add(1, 10, 1, "Editar");
            menu.add(1, 20, 2, "Deletar");
        }
    };

    private AdapterView.OnItemClickListener clickListenerPessoas = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pessoa pessoa = repository.consultarPessoaPorID(listaPessoas.get(position).getIdPessoa());
            //Primeiro get(position) é pra pegar a posicao na Lista
            //O Segundo é pra pegar o ID da pessoa que esta naquela posicao da lista

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder info = new StringBuilder();
            info.append("Nome: " + pessoa.getNome());
            info.append("\nEndereco: " + pessoa.getEndereco());
            info.append("\nCPF/CNPJ: " + pessoa.getCpfCnpj());
            info.append("\nSexo: " + pessoa.getSexo().getDescricao());
            info.append("\nProfissao: " + pessoa.getProfissao().getDescricao());
            info.append("\nDt. Nasc: " + dateFormat.format(pessoa.getDtNasc()));

            Util.showMsgAlertOK(ListaPessoaActivity.this, "Info", info.toString(), TipoMsg.INFO);
        }
    };

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 10:
                Pessoa pessoa = repository.consultarPessoaPorID(listaPessoas.get(posicaoSelecionada).getIdPessoa());
                Intent i = new Intent(this, EditarPessoaActivity.class);
                i.putExtra("pessoa", pessoa);
                startActivity(i);
                finish();
                break;
            case 20:
                Util.showMsgConfirm(ListaPessoaActivity.this, "Remover pessoa", "Deseja realmente remover essa pessoa", TipoMsg.ALERTA, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = listaPessoas.get(posicaoSelecionada).getIdPessoa();
                        repository.removerPessoaPorId(id);
                        setArrayAdapterPessoas();
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
        return true;
    }

    public void addNewPessoa(View view){
        Intent i = new Intent(this, PessoaActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}

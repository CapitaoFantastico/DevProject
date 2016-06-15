package me.br.devproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import me.br.devproject.entidade.Profissao;
import me.br.devproject.util.Mask;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    private EditText edtCpfCnpj;

    private RadioGroup rbgCpfCnpj;

    private RadioButton rbtCpf;

    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbtCpf= (RadioButton) findViewById(R.id.rbtCpf);

        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);

        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int opcao = group.getCheckedRadioButtonId();
                if(opcao == rbtCpf.getId()){
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                } else{
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                }
            }
        });

        this.initProfissioes();
    }

    private void initProfissioes(){
        ArrayList<String> profissoes = new ArrayList<>();
        for(Profissao p : Profissao.values()){
            profissoes.add(p.getDescricao());
        }
        ArrayAdapter adapter = new ArrayAdapter(PessoaActivity.this, android.R.layout.simple_spinner_item, profissoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Set Style to Spinner
        spnProfissao.setAdapter(adapter);
    }
}

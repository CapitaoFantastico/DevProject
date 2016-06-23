package me.br.devproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import me.br.devproject.entidade.Profissao;
import me.br.devproject.fragment.DatePickerFragment;
import me.br.devproject.util.Mask;
import me.br.devproject.util.Util;

public class PessoaActivity extends AppCompatActivity {

    private Spinner spnProfissao;

    private EditText edtCpfCnpj;
    private EditText edtNasc;

    private RadioGroup rbgCpfCnpj;

    //Mapeado apenas um, pois usamos para verificar, se foi o CPF, sei que nao foi o CNPJ e vice-versa
    private RadioButton rbtCpf;

    private TextWatcher cpfMask;
    private TextWatcher cnpjMask;

    private int cpfCnpjSelecionado;

    private TextView txtCpfCnpj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        spnProfissao = (Spinner) findViewById(R.id.spnProfissao);
        edtCpfCnpj = (EditText) findViewById(R.id.edtCpfCnpj);
        rbgCpfCnpj = (RadioGroup) findViewById(R.id.rbgCpfCnpj);
        rbtCpf= (RadioButton) findViewById(R.id.rbtCpf);
        txtCpfCnpj = (TextView) findViewById(R.id.txtCpfCnpj);
        edtNasc = (EditText) findViewById(R.id.edtNasc);


        cpfMask = Mask.insert("###.###.###-##", edtCpfCnpj);
        edtCpfCnpj.addTextChangedListener(cpfMask);

        cnpjMask = Mask.insert("##.###.###/####-##", edtCpfCnpj);

        rbgCpfCnpj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                edtCpfCnpj.setText("");
                edtCpfCnpj.requestFocus();
                cpfCnpjSelecionado = group.getCheckedRadioButtonId();
                if(cpfCnpjSelecionado == rbtCpf.getId()){
                    edtCpfCnpj.removeTextChangedListener(cnpjMask);
                    edtCpfCnpj.addTextChangedListener(cpfMask);
                    txtCpfCnpj.setText("CPF");
                } else{
                    edtCpfCnpj.removeTextChangedListener(cpfMask);
                    edtCpfCnpj.addTextChangedListener(cnpjMask);
                    txtCpfCnpj.setText("CNPJ");
                }
            }
        });

        edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(rbgCpfCnpj.getCheckedRadioButtonId() == rbtCpf.getId()){
                    if(edtCpfCnpj.getText().length() < 14){
                        edtCpfCnpj.setText("");
                    }
                }
                else{
                    if(edtCpfCnpj.getText().length() < 18){
                        edtCpfCnpj.setText("");
                    }
                }
            }
        });

        this.initProfissioes();
    }

    public void setDate(View view){

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Calendar ca1 = Calendar.getInstance();

        Bundle bundle = new Bundle();
        bundle.putInt("dia", ca1.get(Calendar.DAY_OF_MONTH));
        bundle.putInt("mes", ca1.get(Calendar.MONTH));
        bundle.putInt("ano", ca1.get(Calendar.YEAR));

        datePickerFragment.setArguments(bundle);
        datePickerFragment.setDateListener(dateListener);
        datePickerFragment.show(getFragmentManager(), "Data Nasc.");
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            edtNasc.setText(i2 + "/" + (i1 + 1) + "/" + i);
            //Util.showMsgToast(PessoaActivity.this,"Year: " + i + " Month: " + (i1 + 1) + " Day: " + i2);
        }
    };

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

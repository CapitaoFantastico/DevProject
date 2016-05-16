package me.br.devproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.br.devproject.util.TipoMsg;
import me.br.devproject.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Controlando ActionBar - Exibir botão de voltar na ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Bem vindo, Henrique!");

    }

    public void clickBtnEnviar(View view){
        Util.showMsgAlertOK(MainActivity.this, "Titulo", "Essa é a mensagem!", TipoMsg.INFO);
    }

}

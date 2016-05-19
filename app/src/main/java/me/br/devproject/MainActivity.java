package me.br.devproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Infla o menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Acima - Metodo que recupera o click
        switch (item.getItemId()){
            case R.id.menu_sair:

                finish();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                SharedPreferences.Editor editor = getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
                editor.remove("login");
                editor.remove("senha");
                editor.commit();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickBtnEnviar(View view){
        Util.showMsgAlertOK(MainActivity.this, "Titulo", "Essa é a mensagem!", TipoMsg.INFO);
    }

}

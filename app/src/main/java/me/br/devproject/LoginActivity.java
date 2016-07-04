package me.br.devproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.br.devproject.bo.LoginBO;
import me.br.devproject.util.Util;
import me.br.devproject.validation.LoginValidation;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;

    private Button btnLogar;

    private SharedPreferences sharedPreferences;

    private LoginBO loginBO;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //loginBO = new LoginBO(this);

        sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        String login = sharedPreferences.getString("login", null);
        String senha = sharedPreferences.getString("senha", null);

        if(login != null && senha != null){
            Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(i);
            finish(); //Mata Activity
        }


        edtLogin = (EditText) findViewById(R.id.edt_login);
        edtSenha = (EditText) findViewById(R.id.edt_senha);

        btnLogar = (Button) findViewById(R.id.btn_logar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                LoginValidation validation = new LoginValidation();
                validation.setActivity(LoginActivity.this);
                validation.setEdtLogin(edtLogin);
                validation.setEdtsenha(edtSenha);
                validation.setLogin(login);
                validation.setSenha(senha);

                boolean isValid = loginBO.validarCamposLogin(validation);

                if(isValid){
                    //Para Intent informo, onde estou e para onde vou
                    //Classe login já está instanciada, passo .this, pra onde vou, não esta instanciado, passo .class
                    Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish(); //Mata Activity
                }

            }
        });

    }

}

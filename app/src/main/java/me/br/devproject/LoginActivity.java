package me.br.devproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.br.devproject.util.Util;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin;
    private EditText edtSenha;

    private Button btnLogar;

    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        String login = sharedPreferences.getString("login", null);
        String senha = sharedPreferences.getString("senha", null);

        if(login != null && senha != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
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

                boolean isValid = validarCamposLogin(login, senha);

                if(isValid){
                    //Para Intent informo, onde estou e para onde vou
                    //Classe login já está instanciada, passo .this, pra onde vou, não esta instanciado, passo .class
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish(); //Mata Activity
                }

            }
        });

    }

    private boolean validarCamposLogin(String login, String senha){

        boolean resultado = true;

        if(login == null || "".equals(login)){
            edtLogin.setError("Campo obrigatório!");
            //Util.showMsgToast(LoginActivity.this, "Campo Login obrigatório!");
            resultado = false;
        } else if(login.length() < 3){
            edtLogin.setError("Campo deve ter no minímo 3 caracteres");
        }

        if(senha == null || "".equals(senha)){
            edtSenha.setError("Campo obrigatório!");
            //Util.showMsgToast(LoginActivity.this, "Campo senha obrigatório!");
            resultado = false;
        }


        if(resultado){
            if (!login.equals("admin") || !senha.equals("admin")){
                Util.showMsgToast(LoginActivity.this, "Login/Senha Inválidos!");
                resultado = false;
            } else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", login);
                editor.putString("senha", senha);
                editor.commit();
            }
        }

        return resultado;
    }

}

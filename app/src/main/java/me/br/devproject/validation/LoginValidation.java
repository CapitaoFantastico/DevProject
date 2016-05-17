package me.br.devproject.validation;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by ricks on 17/05/2016.
 */
public class LoginValidation {

    private String login;
    private String senha;

    private EditText edtLogin;
    private EditText edtsenha;

    private Activity activity;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EditText getEdtLogin() {
        return edtLogin;
    }

    public void setEdtLogin(EditText edtLogin) {
        this.edtLogin = edtLogin;
    }

    public EditText getEdtsenha() {
        return edtsenha;
    }

    public void setEdtsenha(EditText edtsenha) {
        this.edtsenha = edtsenha;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}

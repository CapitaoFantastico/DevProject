package me.br.devproject.bo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import me.br.devproject.repository.LoginRepository;
import me.br.devproject.util.Util;
import me.br.devproject.validation.LoginValidation;

/**
 * Created by ricks on 16/05/2016.
 */
public class LoginBO {

    public LoginRepository loginRepository;

    //Seto uma configuracao inicial para a classe através do construtor abaixo
    public LoginBO(Activity activity){
        loginRepository = new LoginRepository(activity);
        loginRepository.listarLogin(activity);
    }

    public boolean validarCamposLogin(LoginValidation validation){

        boolean resultado = true;

        if(validation.getLogin() == null || "".equals(validation.getLogin())){
            validation.getEdtLogin().setError("Campo obrigatório!");
            //Util.showMsgToast(LoginActivity.this, "Campo Login obrigatório!");
            resultado = false;
        } else if(validation.getLogin().length() < 3){
            validation.getEdtLogin().setError("Campo deve ter no minímo 3 caracteres");
        }

        if(validation.getSenha()== null || "".equals(validation.getSenha())){
            validation.getEdtsenha().setError("Campo obrigatório!");
            //Util.showMsgToast(LoginActivity.this, "Campo senha obrigatório!");
            resultado = false;
        }


        if(resultado){
            if (!validation.getLogin().equals("admin") || !validation.getSenha().equals("admin")){
                Util.showMsgToast(validation.getActivity(), "Login/Senha Inválidos!");
                resultado = false;
            } else{
                SharedPreferences.Editor editor = validation.getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit();
                editor.putString("login", validation.getLogin());
                editor.putString("senha", validation.getSenha());
                editor.commit();
            }
        }

        return resultado;
    }


    public void deslogar()
    {

    }

}

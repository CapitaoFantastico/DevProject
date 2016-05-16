package me.br.devproject.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import me.br.devproject.R;

/**
 * Created by ricks on 06/04/2016.
 */
public class Util {

    public static void showMsgToast(Activity activity, String txt)   {

        //Parametro desse metodo - Onde quero exibir este Toast

        //Quando precisamos inflar - Quando precisamos de um XML de Layout do corrente usado na tela
        //1 - Layout XML / 2 - ID do Layout (converto pra Viewgroup)
        LayoutInflater inflater = activity.getLayoutInflater();
        View layoutToast = inflater.inflate(R.layout.toast_template, (ViewGroup) activity.findViewById(R.id.layoutToast));

        //Crio um TextView na tela referenciando o criado no XML pra poder altera-lo em tempo de execução
        TextView txtToast = (TextView) layoutToast.findViewById(R.id.txtToast);
        txtToast.setText(txt);

        //Exibindo Alerta tipo Toast
        //Toast toast = Toast.makeText(this, "Teste App 1.0", Toast.LENGTH_LONG); //Alerta comum
        Toast toast = new Toast(activity);
        toast.setView(layoutToast);
        toast.setGravity(Gravity.BOTTOM, 0,0);
        toast.show();


    }

    public static void showMsgAlertOK(final Activity activity, String titulo, String txt, TipoMsg tipoMsg){

        int theme = 0;
        int icone = 0;

        switch (tipoMsg){
            case INFO:
                theme = R.style.AppTheme_Dark_Dialog_Info;
                icone = R.drawable.info_notification_32;
                break;
            case ALERTA:
                theme = R.style.AppTheme_Dark_Dialog_Alerta;
                icone = R.drawable.alert_32;
                break;
            case ERRO:
                theme = R.style.AppTheme_Dark_Dialog_Error;
                icone = R.drawable.error_32;
                break;
            case SUCESSO:
                theme = R.style.AppTheme_Dark_Dialog_Sucesso;
                icone = R.drawable.ok_32;
                break;
        }

        final AlertDialog alertDialog  = new AlertDialog.Builder(activity, theme).create();

        alertDialog.setTitle(titulo);
        alertDialog.setMessage(txt);
        alertDialog.setIcon(icone);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Util.showMsgToast(activity, "Teste App 2.0");
                alertDialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(); //Objeto genérico para guardar atributos
        params.copyFrom(alertDialog.getWindow().getAttributes()); //Copia os atributos do Dialog para o params //Objeto que guarda atributos
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        alertDialog.show();

        alertDialog.getWindow().setAttributes(params); //Seto os atributos no dialog exibido

    }

}

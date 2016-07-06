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

        LayoutInflater inflater = activity.getLayoutInflater();
        View layoutToast = inflater.inflate(R.layout.toast_template, (ViewGroup) activity.findViewById(R.id.layoutToast));

        TextView txtToast = (TextView) layoutToast.findViewById(R.id.txtToast);
        txtToast.setText(txt);


        Toast toast = new Toast(activity);
        toast.setView(layoutToast);
        toast.setGravity(Gravity.BOTTOM, 0,0);
        toast.show();


    }

    public static void showMsgConfirm(final Activity activity, String titulo, String txt, TipoMsg tipoMsg, DialogInterface.OnClickListener onClickListener){

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
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK",onClickListener);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Util.showMsgToast(activity, "Teste App 2.0");
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
                //Util.showMsgToast(activity, "Teste App 2.0");
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

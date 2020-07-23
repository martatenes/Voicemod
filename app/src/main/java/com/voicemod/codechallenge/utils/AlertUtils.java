package com.voicemod.codechallenge.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.voicemod.codechallenge.R;

public class AlertUtils {

    public interface AlertCallback{
        void onClickAccept();
        void onClickCancel();
    }

    public static void ShowSimpleAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.TR_ACEPTAR, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void ShowAlertWithCallback(Context context, String message, AlertCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.TR_ACEPTAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                callback.onClickAccept();
            }
        });
        builder.setNegativeButton(R.string.TR_CANCELAR, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                callback.onClickCancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

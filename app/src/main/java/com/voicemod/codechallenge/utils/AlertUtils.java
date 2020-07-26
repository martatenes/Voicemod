package com.voicemod.codechallenge.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.voicemod.codechallenge.R;

public class AlertUtils {

    public interface AlertAcceptCancelCallback{
        void onClickAccept();
        void onClickCancel();
    }

    public interface AlertAcceptCallback{
        void onClickAccept();
    }

    public static void ShowSimpleAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.TR_ACEPTAR, (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void ShowAlertWithCallback(Context context, String message, AlertAcceptCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.TR_ACEPTAR, (dialogInterface, i) -> {
            callback.onClickAccept();
            dialogInterface.dismiss();
        });
        builder.setNegativeButton(R.string.TR_CANCELAR, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void ShowAlertWithCallback(Context context, String message, AlertAcceptCancelCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.TR_ACEPTAR, (dialogInterface, i) -> {
            callback.onClickAccept();
            dialogInterface.dismiss();
        });
        builder.setNegativeButton(R.string.TR_CANCELAR, (dialogInterface, i) -> {
            //callback.onClickCancel();
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

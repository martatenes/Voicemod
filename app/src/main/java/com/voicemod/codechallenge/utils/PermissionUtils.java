package com.voicemod.codechallenge.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import com.voicemod.codechallenge.R;

public class PermissionUtils {



    public static boolean useRunTimePermissions() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static boolean hasPermission(Activity activity, String permission) {
        if (useRunTimePermissions()) {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permission, int requestCode) {
        if (useRunTimePermissions()) {
            activity.requestPermissions(permission, requestCode);
        }
    }


    public static boolean shouldShowRational(Activity activity, String permission) {
        if (useRunTimePermissions()) {
            return activity.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    public static boolean shouldAskForPermission(Activity activity, String permission) {
        if (useRunTimePermissions()) {
            return !hasPermission(activity, permission) &&
                    (!hasAskedForPermission(activity, permission) ||
                    shouldShowRational(activity, permission));
        }
        return false;
    }

    public static void goToAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity.getPackageName(), null));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static boolean hasAskedForPermission(Activity activity, String permission) {
        return PreferenceManager
                .getDefaultSharedPreferences(activity)
                .getBoolean(permission, false);
    }

    public static void markedPermissionAsAsked(Activity activity, String permission) {
        PreferenceManager
                .getDefaultSharedPreferences(activity)
                .edit()
                .putBoolean(permission, true)
                .apply();
    }

    public static void managePermission(Context context, String message, String permission, int requestCode){
            // Pedimos el permiso para poder visualizar los vídeos desde la galería
            if (shouldShowRational((Activity) context, permission)){
                // En el caso de que ya hayamos pedido previamente este permiso y el usuario lo haya denegado
                AlertUtils.ShowAlertWithCallback((Activity) context, message, new AlertUtils.AlertCallback() {
                    @Override
                    public void onClickAccept() {
                        requestPermissions((Activity) context, new String[]{permission}, requestCode);
                    }

                    @Override
                    public void onClickCancel() {
                        Toast.makeText((Activity) context, context.getString(R.string.TR_ES_NECESARIO_ACEPTAR_PERMISOS), Toast.LENGTH_LONG).show();
                    }
                });
            }else{ // Pedimos el permiso
                requestPermissions((Activity) context, new String[]{permission}, requestCode);
            }
    }


    public static void managePermissions(Context context, String[] permissions, int REQUEST_CODE){
        // Pedimos el permiso para poder visualizar los vídeos desde la galería
        for (String permission: permissions){


        if (shouldShowRational((Activity) context, permission)){
            // En el caso de que ya hayamos pedido previamente este permiso y el usuario lo haya denegado
            AlertUtils.ShowAlertWithCallback((Activity) context, context.getString(R.string.TR_ES_NECESARIO_ACEPTAR_PERMISOS), new AlertUtils.AlertCallback() {
                @Override
                public void onClickAccept() {
                    requestPermissions((Activity) context, new String[]{permission}, REQUEST_CODE);
                }

                @Override
                public void onClickCancel() {
                    Toast.makeText((Activity) context, context.getString(R.string.TR_ES_NECESARIO_ACEPTAR_PERMISOS), Toast.LENGTH_LONG).show();
                }
            });
        }else{ // Pedimos el permiso
            requestPermissions((Activity) context, new String[]{permission}, REQUEST_CODE);
        }
        }

    }
}
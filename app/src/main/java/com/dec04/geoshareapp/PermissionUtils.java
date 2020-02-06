package com.dec04.geoshareapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

class PermissionUtils {

    private Context context;
    private String TAG = "DEV";

    // Код ответа для запроса разрешений
    private int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Флаг сообщает о том, что после запроса разрешений - запрос отклонили\
    boolean flagPermissionDenied = false;

    // Обьяснение, зачем нужны разрешения
    private String explanation;

    // Конструкторы
    PermissionUtils(Context context, String explanation) {
        this.context = context;
        this.explanation = explanation;
    }

    // Проверяем, есть ли у пользователя необзодимые разрешения
    boolean check() {
        this.explanation = context.getResources().getString(R.string.request_permission_explanation);

        // Access to the location has been granted to the app.
        return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // Запрашиваем разрешение и если необходимо - показываем обьяснение
    void requestPermission() {
        // Permission is missing.
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(explanation)
                   .setPositiveButton(R.string.get, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION_REQUEST_CODE);
                        }
                    })
                   .create().show();
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

            // LOCATION_PERMISSION_REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    /**
     * Показываем диалог, обьясняющий что разрешения не получены.
     */
    void showMissingPermissionError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.missing_permission_explanation)
                .setNeutralButton(R.string.get_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                })
                .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }
}

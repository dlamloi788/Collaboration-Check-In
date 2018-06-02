package com.dlamloi.MAD.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.ActionMenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.dlamloi.MAD.R;

/**
 * Created by Don on 14/05/2018.
 */

public class Utility {

    public static void startIntent(Context context, Class destination) {
        context.startActivity(new Intent(context, destination));
    }

    public static void startIntent(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean areAnyRequiredFieldsEmpty(String...fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static AlertDialog setUpLeaveAlertDialog(Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.yes),
                ((dialog, which) -> {
                    ((Activity)context).finish();
                }));

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.no),
                ((dialog, which) -> {
                    alertDialog.hide();
                }));


        return alertDialog;
    }

}

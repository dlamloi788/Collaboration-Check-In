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

/**
 * Handles small context activites
 */
public class Utility {

    /**
     * Starts a new activity
     *
     * @param context     the context of the activity
     * @param destination the activity to start
     */
    public static void startIntent(Context context, Class destination) {
        context.startActivity(new Intent(context, destination));
    }

    /**
     * Starts a new activity
     *
     * @param context the context of the activity
     * @param intent the intent to start
     */
    public static void startIntent(Context context, Intent intent) {
        context.startActivity(intent);
    }

    /**
     * Shows a toast with a desired message
     *
     * @param context the context of the activity
     * @param message the message to display
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Determines if any texts are empty
     *
     * @param texts the texts to check if they are empty
     * @return true if any texts are empty; false otherwise
     */
    public static boolean areAnyTextEmpty(String... texts) {
        for (String field : texts) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets up a leave dialog
     *
     * @param context the context of the activity
     * @param message the message of the dialog
     * @return a leave dialog with negative and positive buttons and a specified message
     */
    public static AlertDialog setUpLeaveAlertDialog(Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.yes),
                ((dialog, which) -> {
                    ((Activity) context).finish();
                }));

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.no),
                ((dialog, which) -> {
                    alertDialog.hide();
                }));


        return alertDialog;
    }

}

package com.dlamloi.MAD.utilities;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

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

    public static boolean areAnyTextFieldsEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }




}

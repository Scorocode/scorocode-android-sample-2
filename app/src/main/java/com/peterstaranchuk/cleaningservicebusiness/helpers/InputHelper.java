package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.peterstaranchuk.cleaningservicebusiness.R;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

public class InputHelper {
    public static void checkForEmptyEnter(EditText viewForCheck, Action1<CharSequence> callbackAction) {
        RxTextView.textChanges(viewForCheck)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callbackAction);
    }

    public static boolean isNotEmpty(EditText editText) {
        if(editText != null && !editText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    public static void enableButton(Button button, int colorId) {
        //we extract this method for case if we will need to
        //change behaviour or appearance of all enabled buttons in app
        if(button != null) {
            button.setEnabled(true);
            if(colorId != 0) {
                button.setBackgroundColor(button.getResources().getColor(colorId));
            } else {
                button.setBackgroundColor(button.getResources().getColor(R.color.mainColor));
            }
        }
    }


    public static void enableButton(Button button) {
        //we extract this method for case if we will need to
        //change behaviour or appearance of all enabled buttons in app
        enableButton(button, 0);
    }

    public static void disableButton(Button button) {
        //we extract this method for case if we will need to
        //change behaviour or appearance of all disabled buttons in app
        if(button != null) {
            button.setEnabled(false);
            button.setBackgroundColor(button.getResources().getColor(R.color.disabledButtonColor));
        }
    }

    @NonNull
    public static String getStringFrom(TextView editText) {
        if(editText != null) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    public static void setFocusTo(EditText editText) {
        if(editText != null) {
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }
    }
}

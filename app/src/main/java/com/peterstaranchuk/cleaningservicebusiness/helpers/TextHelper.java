package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.peterstaranchuk.cleaningservicebusiness.R;

/**
 * Created by Peter Staranchuk.
 */

public class TextHelper {
    public static void setColoredTextInTextView(TextView textView, String text1, int color1, String text2, int color2, String delimiter) {
        Context context = textView.getContext();

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        stringBuilder.append(getColoredString(text1, color1, context));
        stringBuilder.append(delimiter);
        stringBuilder.append(getColoredString(text2, color2, context));

        textView.setText(stringBuilder);
    }

    @NonNull
    private static Spannable getColoredString(String text2, int color2, Context context) {
        Spannable spannableString2 = new SpannableString(text2);
        spannableString2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color2)), 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString2;
    }

    public static void setBlackAndGreyText(TextView textView, String title, String text, String delimiter) {
        setColoredTextInTextView(textView, title, R.color.black, text, R.color.darkerGray, delimiter);
    }
}

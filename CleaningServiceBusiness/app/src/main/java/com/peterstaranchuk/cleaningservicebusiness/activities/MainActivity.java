package com.peterstaranchuk.cleaningservicebusiness.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.peterstaranchuk.cleaningservicebusiness.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void display(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}


package com.idonans.example.appcontext;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.github.idonans.appcontext.AppContext;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = AppContext.getContext();
        if (context != null) {
            Timber.v("AppContext found");
        } else {
            Timber.e("AppContext not found");
        }
    }
}

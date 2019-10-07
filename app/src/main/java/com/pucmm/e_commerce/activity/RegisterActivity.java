package com.pucmm.e_commerce.activity;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.pucmm.e_commerce.R;

/**
 * @author Fred Pena fantpena@gmail.com
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.e("Register CheckPoint", "RegisterActivity started");
    }
}

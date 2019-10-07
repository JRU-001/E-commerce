package com.pucmm.e_commerce.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.pucmm.e_commerce.R;
import com.pucmm.e_commerce.usersession.UserSession;

/**
 * @author Fred Pena fantpena@gmail.com
 */
public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 4000;
    //to get user session data
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("Splash CheckPoint", "SplashActivity started");

        session = new UserSession(SplashActivity.this);

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

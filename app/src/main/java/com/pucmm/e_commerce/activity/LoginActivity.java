package com.pucmm.e_commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pucmm.e_commerce.R;
import com.pucmm.e_commerce.networksync.LoginRequest;
import com.pucmm.e_commerce.networksync.Response;
import com.pucmm.e_commerce.usersession.UserSession;
import com.pucmm.e_commerce.utils.ValidUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Fred Pena fantpena@gmail.com
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private UserSession session;
    // UI references.
    private EditText email, password;
    private View loginForm;
    private TextView forgotPass, registerNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("Login CheckPoint", "LoginActivity started");
        // Set up the login form.
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        session = new UserSession(getApplicationContext());

        //if user wants to register
        registerNow = findViewById(R.id.register_now);
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        //if user forgets password
        forgotPass = findViewById(R.id.forgot_pass);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginForm = findViewById(R.id.login_form);

        email.setText("fred@fredpena.com");
        password.setText("hello");
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (ValidUtil.isEmpty(this, this.email, this.password)) {
            return;
        }
        // Store values at the time of the login attempt.
        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();

        if (ValidUtil.isPasswordValid(this.password, password) && ValidUtil.isEmailValid(this.email, email)) {
            final KProgressHUD progressDialog = KProgressHUD.create(LoginActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Authenticating...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            LoginRequest loginRequest = new LoginRequest(email, password, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getBoolean("success")) {
                            //Passing all received data from server to next activity
                            final String sessionName = response.getString("name");
                            final String sessionMobile = response.getString("mobile");
                            final String sessionEmail = response.getString("email");
                            final String sessionPhoto = response.getString("url");

                            //create shared preference and store data
                            session.createLoginSession(sessionName, sessionMobile, sessionEmail, sessionPhoto);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(Exception error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            // loginRequest.execute((Void) null);
            loginRequest.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Login CheckPoint", "LoginActivity resumed");
        //check Internet Connection

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Login CheckPoint", "LoginActivity stopped");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}




package com.pucmm.e_commerce.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pucmm.e_commerce.R;
import com.pucmm.e_commerce.networksync.ForgotPasswordRequest;
import com.pucmm.e_commerce.networksync.Response;
import com.pucmm.e_commerce.utils.ValidUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Fred Pena fantpena@gmail.com
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgotPassEmail;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Log.e("ForgotPass CheckPoint", "ForgotPasswordActivity started");

        forgotPassEmail = findViewById(R.id.forgotPassEmail);
        back = findViewById(R.id.goBack);

        Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptForgotPassword();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void attemptForgotPassword() {
        if (ValidUtil.isEmpty(this, this.forgotPassEmail)) {
            return;
        }
        // Store values at the time of the login attempt.
        final String email = this.forgotPassEmail.getText().toString();

        if (ValidUtil.isEmailValid(this.forgotPassEmail, email)) {
            final KProgressHUD progressDialog = KProgressHUD.create(ForgotPasswordActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setDetailsLabel("Connecting...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ForgotPasswordRequest loginRequest = new ForgotPasswordRequest(email, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(ForgotPasswordActivity.this, "Password sent to Email Account", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ForgotPasswordActivity.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(Exception error) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            loginRequest.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check Internet Connection
    }

}

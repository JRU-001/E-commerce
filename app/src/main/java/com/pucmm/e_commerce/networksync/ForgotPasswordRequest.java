package com.pucmm.e_commerce.networksync;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Fred Pena fantpena@gmail.com
 */
public class ForgotPasswordRequest extends AsyncTask<Void, Void, Result> {

    private static final String LOGIN_URL = "";

    private final String email;
    private final Response.Listener listener;
    private final Response.ErrorListener errorListener;

    public ForgotPasswordRequest(String email, Response.Listener listener, Response.ErrorListener errorListener) {
        this.email = email;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    protected Result doInBackground(Void... voids) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return new Result<>(e.getCause());
        }
        return new Result<>("");
    }

    @Override
    protected void onPostExecute(final Result success) {
        if (success.getResult() != null) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", this.email);
                jsonObject.put("status", "VALID");
                jsonObject.put("success", "true");
                listener.onResponse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            errorListener.onErrorResponse(success.getError());
        }
    }
}

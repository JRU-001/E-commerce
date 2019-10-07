package com.pucmm.e_commerce.networksync;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Fred Pena fantpena@gmail.com
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class LoginRequest extends AsyncTask<Void, Void, Result> {

    private static final String LOGIN_URL = "";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "fred@fredpena.com:hello"
    };
    private final String email;
    private final String password;
    private final Response.Listener listener;
    private final Response.ErrorListener errorListener;

    public LoginRequest(String email, String password, Response.Listener listener, Response.ErrorListener errorListener) {
        this.email = email;
        this.password = password;
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    protected Result doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return new Result<>(e.getCause());
        }
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            // Account exists, return true if the password matches.
            if (!pieces[0].equals(email)) {
                return new Result<>(new Exception("No account found for email [" + email + "]"));
            } else if (!pieces[1].equals(password)) {
                return new Result<>(new Exception("No password found for email [" + email + "]"));
            }
            return new Result<>(pieces);
        }
        // TODO: register the new account here.
        return new Result<>(new Exception("No account found..."));
    }

    @Override
    protected void onPostExecute(final Result success) {
        if (success.getResult() != null) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", "Fred Pena");
                jsonObject.put("mobile", "'");
                jsonObject.put("email", this.email);
                jsonObject.put("url", "");
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

    @Override
    protected void onCancelled() {
        errorListener.onErrorResponse(new Exception("Task cancelled"));
    }
}



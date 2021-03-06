package storeowner.foodclearance.team9.com.foodclearancestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by KhizerHasan on 12/6/2015.
 */
public class LoginActivity extends AppCompatActivity {

    static final String APP_SERVER_URL = "http://project9.comxa.com/php/login.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        final EditText username = (EditText)findViewById(R.id.sUsernameLogin);
        final EditText password = (EditText)findViewById(R.id.sPasswordLogin);

        TextView SignUP =(TextView) findViewById(R.id.tvRegisterLink);
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button loginButton = (Button)findViewById(R.id.bLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String sUsername = username.getText().toString();
               final String sPassword = password.getText().toString();





                prgDialog.show();

                try {
                    jsonParams.put("sUsername", sUsername);
                    jsonParams.put("sPassword",sPassword);
                }
                catch (Exception e){
                    Log.d("Error", e.getMessage());
                }

                AsyncHttpClient client = new AsyncHttpClient();
                StringEntity entity = null;
                try {
                    entity = new StringEntity(jsonParams.toString());
                } catch (UnsupportedEncodingException e) {
                    Log.d("Error", e.getMessage());
                }

                client.post(applicationContext , APP_SERVER_URL, entity, "application/json",
                        new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                                // Hide Progress Dialog
                                prgDialog.hide();
                                if (prgDialog != null) {
                                    prgDialog.dismiss();
                                }

                                try {
                                  Log.d("array",jsonArray.toString());

                                    if (jsonArray.length() > 0) {
                                        SharedPreferences pref = getSharedPreferences("data", 0);
                                        final SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("username", sUsername);
                                        editor.putString("sPassword", sPassword);
                                        editor.commit();


                                        Toast.makeText(applicationContext,
                                                "Login Successful in store",
                                                Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
                                        //intent.putExtra("username",sUsername);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(applicationContext,
                                                "Incorrect Username or Password",
                                                Toast.LENGTH_LONG).show();
                                    }


                                } catch (Exception e) {

                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers,String responseBody, Throwable error) {
                       /* prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        Toast.makeText(applicationContext,
                                "Reg Id sharing with Web App not successful ",
                                Toast.LENGTH_LONG).show();
                    }*//*

                                // When the response returned by REST has Http
                                // response code '200'
                    *//*@Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        Toast.makeText(applicationContext,
                                "Reg Id shared successfully with Web App ",
                                Toast.LENGTH_LONG).show();
                       *//**//* Intent i = new Intent(applicationContext,
                                HomeActivity.class);
                        i.putExtra("regId", regId);
                        startActivity(i);
                            finish();
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        */prgDialog.hide();
                                if (prgDialog != null) {
                                    prgDialog.dismiss();
                                }
                                // When Http response code is '404'
                                if (statusCode == 404) {
                                    Toast.makeText(applicationContext,
                                            "Requested resource not found",
                                            Toast.LENGTH_LONG).show();
                                }
                                // When Http response code is '500'
                                else if (statusCode == 500) {
                                    Toast.makeText(applicationContext,
                                            "Something went wrong at server end",
                                            Toast.LENGTH_LONG).show();
                                }
                                // When Http response code other than 404, 500
                                else {
                                    Toast.makeText(
                                            applicationContext,
                                            "Unexpected Error occcured! [Most common Error: Device might "
                                                    + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });



            }
        });




    }
}

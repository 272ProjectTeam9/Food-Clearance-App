package storeowner.foodclearance.team9.com.foodclearancestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class ProfileActivity extends AppCompatActivity {
    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_update_storeProfile.php";
    static final String APP_SERVER_URL1 = "http://project9.comxa.com/php/db_select_storeProfile.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        SharedPreferences pref = getSharedPreferences("data", 0);
        final String username = pref.getString("username", "");



        final EditText storeName = (EditText) findViewById(R.id.sName);

        final EditText storeEmail = (EditText) findViewById(R.id.sEmail);


        final EditText storePassword = (EditText) findViewById(R.id.sPassword);

        final EditText storeAddress = (EditText) findViewById(R.id.sAddress);

        final EditText storeZipcode = (EditText) findViewById(R.id.sZipCode);

        final EditText storeCity = (EditText) findViewById(R.id.sCity);

        Button registerButton = (Button) findViewById(R.id.bRegister);

        try {
            jsonParams.put("sUsername",username);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Error", e.getMessage());
        }
        client.post(applicationContext, APP_SERVER_URL1, entity, "application/json",
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                        // Hide Progress Dialog

try{
                        int length = jsonArray.length();
                        List<String> listContents = new ArrayList<String>(length);
                        for (int i = 0; i < length; i++)
                        {
                            JSONObject j =  jsonArray.getJSONObject(i);
                            Log.d("JSON", j.toString());
                            storeName.setText(j.getString("sName"));
                            storeEmail.setText(j.getString("sEmail"));
                            storeAddress.setText(j.getString("sAddress"));
                            storeCity.setText(j.getString("sCity"));
                            storeZipcode.setText(j.getString("sZipCode"));
                            storePassword.setText(j.getString("sPassword"));



                        }
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        }
catch (Exception e){}
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {

                        prgDialog.hide();
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


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName = storeName.getText().toString();
                String sEmail = storeEmail.getText().toString();
                String sPassword = storePassword.getText().toString();
                String sAddress = storeAddress.getText().toString();
                String sZipCode = storeZipcode.getText().toString();
                String sCity = storeCity.getText().toString();

                prgDialog.show();
                try {
                    jsonParams.put("sName", sName);
                    jsonParams.put("sUsername", username);
                    jsonParams.put("sEmail", sEmail);
                    jsonParams.put("sPassword", sPassword);
                    jsonParams.put("sAddress", sAddress);
                    jsonParams.put("sCity", sCity);
                    jsonParams.put("sZipCode", sZipCode);
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }

                AsyncHttpClient client = new AsyncHttpClient();
                StringEntity entity = null;
                try {
                    entity = new StringEntity(jsonParams.toString());
                } catch (UnsupportedEncodingException e) {
                    Log.d("Error", e.getMessage());
                }
                client.post(applicationContext, APP_SERVER_URL, entity, "application/json",
                        new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                                // Hide Progress Dialog
                                prgDialog.hide();
                                if (prgDialog != null) {
                                    prgDialog.dismiss();
                                }
                                Toast.makeText(applicationContext,
                                        "Update Successful",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {

                                prgDialog.hide();
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

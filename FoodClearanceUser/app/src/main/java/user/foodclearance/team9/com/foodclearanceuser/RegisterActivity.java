package user.foodclearance.team9.com.foodclearanceuser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by KhizerHasan on 12/7/2015.
 */
public class RegisterActivity extends AppCompatActivity {

    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_insert_users.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);

        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        final EditText firstName = (EditText)findViewById(R.id.firstName);
        final EditText lastName = (EditText)findViewById(R.id.lastName);
        final EditText userEmail = (EditText)findViewById(R.id.sEmail);
        final EditText userUsername = (EditText)findViewById(R.id.sUsername);
        final EditText userPassword = (EditText)findViewById(R.id.sPassword);

        Button registerButton = (Button) findViewById(R.id.bRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String uEmail = userEmail.getText().toString();
                String uUsername = userUsername.getText().toString();
                String uPassword = userPassword.getText().toString();


                prgDialog.show();
                try {
                    jsonParams.put("username", uUsername);
                    jsonParams.put("password",uPassword);
                    jsonParams.put("email", uEmail);
                    jsonParams.put("firstName", fName);
                    jsonParams.put("lastName", lName);
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
                            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                                // Hide Progress Dialog
                                prgDialog.hide();
                                if (prgDialog != null) {
                                    prgDialog.dismiss();
                                }
                                Toast.makeText(applicationContext,
                                        "Registration Successful. Login to Continue",
                                        Toast.LENGTH_LONG).show();
                                        finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers , Throwable error,JSONObject responseBody) {
                       /* prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        Toast.makeText(applicationContext,
                                "Reg Id sharing with Web App not successful ",
                                Toast.LENGTH_LONG).show();
                    }*/

                                // When the response returned by REST has Http
                                // response code '200'
                    /*@Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        Toast.makeText(applicationContext,
                                "Reg Id shared successfully with Web App ",
                                Toast.LENGTH_LONG).show();
                       *//* Intent i = new Intent(applicationContext,
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

package storeowner.foodclearance.team9.com.foodclearancestore;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by KhizerHasan on 12/6/2015.
 */
public class UpdateProductActivity extends AppCompatActivity {

    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_update_products.php";
    ProgressDialog  prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_update_product);
        SharedPreferences pref=getSharedPreferences("data", 0);
        final String username=pref.getString("username","");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);


        applicationContext = getApplicationContext();


        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        Intent i = getIntent();
        Bundle b= getIntent().getExtras();
        final int productId = b.getInt("productId");

        final String productName = i.getStringExtra("productName");
        final String productDesc = i.getStringExtra("productDesc");
        final String productOPrice = i.getStringExtra("productOPrice");
        final String productNPrice = i.getStringExtra("productNPrice");
        final EditText productname=(EditText) findViewById(R.id.productname);
        productname.setText(productName);
        final EditText newPrice = (EditText) findViewById(R.id.newPrice);
        newPrice.setText(productNPrice);
        final EditText oldPrice = (EditText) findViewById(R.id.oldPrice);
        oldPrice.setText(productOPrice);
        final EditText description=(EditText) findViewById(R.id.description);
        description.setText(productDesc);
        //final DatePicker expDate = (DatePicker) findViewById(R.id.expDate);
        final DatePicker expDate = (DatePicker) findViewById(R.id.expDate);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        final Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sproductname= ""+ productname.getText().toString();
                String snewPrice=""+ newPrice.getText().toString();
                String soldPrice="" + oldPrice.getText().toString();
          //      String sExpDate="" +expDate.getMonth()+"-"+expDate.getDayOfMonth()+"-"+expDate.getYear()+"\n";
              //  String sdescription= description.getText().toString();
                String sExpDate="" +expDate.getMonth()+"-"+expDate.getDayOfMonth()+"-"+expDate.getYear()+"\n";
                String sdescription= sExpDate+description.getText().toString();


                prgDialog.show();
                try {
                    jsonParams.put("sUsername", username);
                    jsonParams.put("productId",productId);
                    jsonParams.put("productName", sproductname);
                    jsonParams.put("productDesc", sdescription);
                    jsonParams.put("productOPrice", soldPrice);
                    jsonParams.put("productNPrice", snewPrice);

                }
                catch (Exception e){
                    Log.d("Error",e.getMessage());
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
                                        "Successfully updated the product",
                                        Toast.LENGTH_LONG).show();
                                finish();
                                Intent i =new Intent(UpdateProductActivity.this,ProductListActivity.class);
                                startActivity(i);
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



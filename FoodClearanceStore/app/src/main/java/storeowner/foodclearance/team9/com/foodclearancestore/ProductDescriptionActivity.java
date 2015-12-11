package storeowner.foodclearance.team9.com.foodclearancestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 * Created by KhizerHasan on 12/7/2015.
 */
public class ProductDescriptionActivity extends AppCompatActivity {

    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_select_productDetails.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_desciption);

        SharedPreferences pref=getSharedPreferences("data", 0);
        final String username=pref.getString("username", "");
        // Intent i = getIntent();
        Intent i = getIntent();
        final String productName = i.getStringExtra("productName");
        Bundle extras = getIntent().getExtras();
        final int productId = extras.getInt("productId");

        Log.d("usernamr",username);
        TextView tv = (TextView)findViewById(R.id.ProductNameP);
        tv.setText(productName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProductDescription);
        setSupportActionBar(toolbar);

        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);


        prgDialog.show();
        try
        {
            jsonParams.put("sUsername", username);
            jsonParams.put("productName", productName);
            jsonParams.put("productId", productId);
        }

        catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonParams.toString());
            Log.d("params",jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Error", e.getMessage());
        }
        client.post(applicationContext, APP_SERVER_URL, entity, "application/json",
                new JsonHttpResponseHandler() {

                    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }


                        try {

                            int length = jsonArray.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject j = jsonArray.getJSONObject(i);
                                Log.d("JSON", j.toString());
                                final String productDesc = j.getString("productDesc");
                                final String productNPrice = j.getString("productNPrice");
                                final String productOPrice = j.getString("productOPrice");
                                final String Time = j.getString("Time");
                                TextView tv1 = (TextView) findViewById(R.id.ProductDescriptionP);
                                tv1.setText(productDesc);
                                TextView tv2 = (TextView) findViewById(R.id.NewPriceP);
                                tv2.setText(productNPrice);
                                TextView tv3 = (TextView) findViewById(R.id.OldPriceP);
                                tv3.setText(productOPrice);
                                TextView tv4 = (TextView) findViewById(R.id.SaleAddTimeP);
                                tv4.setText(Time);

                                final Button button = (Button) findViewById(R.id.button);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(ProductDescriptionActivity.this, UpdateProductActivity.class);
                                        intent.putExtra("productName",productName);
                                        intent.putExtra("productDesc",productDesc);
                                        intent.putExtra("productNPrice",  productNPrice);
                                        intent.putExtra("productOPrice",  productOPrice);
                                        intent.putExtra("productId",(int)productId);
                                        startActivity(intent);





                                    }
                                });


                            }


                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            // this is just an example
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                        Log.d("sri", "am in failue");
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

}

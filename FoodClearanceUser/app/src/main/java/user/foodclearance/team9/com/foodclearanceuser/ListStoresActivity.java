package user.foodclearance.team9.com.foodclearanceuser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
 * Created by KhizerHasan on 12/9/2015.
 */
public class ListStoresActivity extends AppCompatActivity{
    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_select_searchResults.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stores);

        SharedPreferences pref1=getSharedPreferences("DATA", 0);
        //Intent intent = getIntent();
        //String zipcode = intent.getStringExtra("zipcode");
        String zipcode = pref1.getString("zipcode","");

        final SharedPreferences pref=getSharedPreferences("StoreSubscription", 0);

        TextView t = (TextView) findViewById(R.id.storeH);
        t.setText("Search results for Zipcode: " + zipcode);

        final ListView lv = (ListView) findViewById(R.id.storesList);

        applicationContext = getApplicationContext();

        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = ((TextView) view).getText().toString();
                Log.d("List Item", itemSelected);
                Intent intent = new Intent(ListStoresActivity.this, ProductListSubActivity.class);
                String sUsername = pref.getString(itemSelected,"");
                intent.putExtra("sUsername", sUsername);
                intent.putExtra("sName", itemSelected);
                startActivity(intent);
            }
        });


        prgDialog.show();
        try {
            jsonParams.put("sZipCode", zipcode);
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


                        Log.d("Response", responseBody.toString());
                        /*Toast.makeText(applicationContext,
                                "Registration Successful",
                                Toast.LENGTH_LONG).show();
                        finish();*/
                    }

                    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        try {

                            int length = jsonArray.length();
                            List<String> listContents = new ArrayList<String>(length);
                            SharedPreferences.Editor editor=pref.edit();
                            for (int i = 0; i < length; i++) {
                                JSONObject j = jsonArray.getJSONObject(i);
                                Log.d("JSON", j.toString());
                                String sName = j.getString("sName");
                                String sUsername = j.getString("sUsername");
                                editor.putString(sName,sUsername);
                                listContents.add(sName);
                            }
                            editor.commit();
                            lv.setAdapter(new ArrayAdapter<String>(ListStoresActivity.this, android.R.layout.simple_list_item_1, listContents));
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                            // this is just an example
                        }


                        // Log.d("Response",responseBody.toString());
                        /*Toast.makeText(applicationContext,
                                "Registration Successful",
                                Toast.LENGTH_LONG).show();
                        finish();*/
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
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
                        */
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
                            /*Toast.makeText(
                                    applicationContext,
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();*/
                            Toast.makeText(
                                    applicationContext,
                                    "No Items to display",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }



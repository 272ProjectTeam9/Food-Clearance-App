package storeowner.foodclearance.team9.com.foodclearancestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by KhizerHasan on 12/7/2015.
 */
public class SubscriberListActivity extends AppCompatActivity {
    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_select_customers.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();
    List<Integer> productIdList= new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_subscriber_list);

        SharedPreferences pref=getSharedPreferences("data", 0);
        final String username=pref.getString("username","");
        // Intent i = getIntent();
        // String username = i.getStringExtra("username");

    // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSubscriberList);
      //  setSupportActionBar(toolbar);



        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);

        final ListView myListView = (ListView) findViewById(R.id.listView);


        prgDialog.show();
        try
        {
            jsonParams.put("sUsername", username);
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
        client.post(applicationContext, APP_SERVER_URL, entity, "application/json",
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }


                        Log.d("Response",responseBody.toString());
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
                                if(jsonArray.length()>0) {
                                    int length = jsonArray.length();
                                    List<String> listContents = new ArrayList<String>(length);
                                    for (int i = 0; i < length; i++) {
                                        JSONObject j = jsonArray.getJSONObject(i);
                                        Log.d("JSON", j.toString());
                                        String firstName = j.getString("firstName");
                                        String email = j.getString("email");
                                        listContents.add(firstName + "(" + email + ")");
                                    }
                                    myListView.setAdapter(new ArrayAdapter<String>(SubscriberListActivity.this, android.R.layout.simple_list_item_1, listContents));
                                }
                                    else{
                                        TextView text = (TextView) findViewById(R.id.subd);
                                        text.setText("No Subscribers Yet");
                                    }


                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                                // this is just an example
                            }
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




}

package user.foodclearance.team9.com.foodclearanceuser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 * Created by KhizerHasan on 12/7/2015.
 */

public class AddSubscriptionActivity extends AppCompatActivity {

    static final String APP_SERVER_URL = "http://project9.comxa.com/php/db_select_searchResults.php";
    ProgressDialog prgDialog;
    Context applicationContext;
    JSONObject jsonParams = new JSONObject();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        SharedPreferences pref = getSharedPreferences("data", 0);
        final EditText zipcodeET = (EditText) findViewById(R.id.zipcode);
        //final String zipcode = pref.getString("zipcode", "");
        final ListView myListView = (ListView) findViewById(R.id.listView);

        applicationContext = getApplicationContext();
        prgDialog = new ProgressDialog(this);





        Button searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prgDialog.show();
                try {
                    jsonParams.put("sZipCode", zipcodeET.getText().toString());
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
                                    for (int i = 0; i < length; i++) {
                                        JSONObject j = jsonArray.getJSONObject(i);
                                        Log.d("JSON", j.toString());
                                        String CityName = j.getString("sCity");
                                        String StoreName = j.getString("sName");
                                        final String Username= j.getString("sUsername");
                                        listContents.add(StoreName);
                                        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String selectedItem = ((TextView) view).getText().toString();
                                                Intent intent = new Intent(AddSubscriptionActivity.this, ProductListActivity.class);
                                                intent.putExtra("sUsername", Username);
                                                startActivity(intent);

                                            }
                                        });
                                    }

                                    myListView.setAdapter(new ArrayAdapter<String>(AddSubscriptionActivity.this, android.R.layout.simple_list_item_1, listContents));
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
        });
        Button subscriptionlist = (Button) findViewById(R.id.subscribed);
        subscriptionlist.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                Intent intent = new Intent(AddSubscriptionActivity.this, SubscribedListActivity.class);
                startActivity(intent);
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if(id==R.id.action_subscribe){
            Intent intent = new Intent(this, AddSubscriptionActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

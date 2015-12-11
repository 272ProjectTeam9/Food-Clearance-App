package user.foodclearance.team9.com.foodclearanceuser;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by KhizerHasan on 12/7/2015.
 */
public class AddSubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        final EditText zipcodeET = (EditText)findViewById(R.id.zipcode);


        Button b = (Button) findViewById(R.id.search);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String zipcode = zipcodeET.getText().toString();
                Log.d("Zipcode",zipcode);
                SharedPreferences pref = getSharedPreferences("DATA",0);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("zipcode",zipcode);
                editor.commit();
                Intent i = new Intent(AddSubscriptionActivity.this,ListStoresActivity.class);
                startActivity(i);
            }
        });



  }


}

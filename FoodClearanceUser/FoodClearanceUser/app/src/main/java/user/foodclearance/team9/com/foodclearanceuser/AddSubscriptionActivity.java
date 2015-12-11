package user.foodclearance.team9.com.foodclearanceuser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by KhizerHasan on 12/7/2015.
 */
public class AddSubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        EditText zipcodeET = (EditText)findViewById(R.id.zipcode);
        String zipcode = zipcodeET.getText().toString();


        Button searchButton = (Button)findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }


}

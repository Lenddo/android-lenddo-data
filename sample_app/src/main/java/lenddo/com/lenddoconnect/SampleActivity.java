package lenddo.com.lenddoconnect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lenddo.data.AndroidData;
import com.lenddo.data.DataManager;
import com.lenddo.data.utils.AndroidDataUtils;


public class SampleActivity extends Activity implements View.OnClickListener {

    private static final String TAG = SampleActivity.class.getName();
    private Button startButton;
    private EditText clientIdEdt;
    private View sessionActiveContainer;
    private View androidDataTestContainer;
    private Button logoutButton;
    private TextView deviceIdText;
    private TextView clientIdText;
    private TextView profieTypeText;
    private TextView wifiOnlyText;
    private TextView initialDataSentText;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        sessionActiveContainer = findViewById(R.id.session_active_container);
        androidDataTestContainer = findViewById(R.id.android_data_test_container);

        deviceIdText = (TextView)findViewById(R.id.device_id);
        profieTypeText = (TextView)findViewById(R.id.profile_type);
        clientIdText = (TextView) findViewById(R.id.client_id);
        wifiOnlyText = (TextView) findViewById(R.id.wifi_only_text);
        initialDataSentText = (TextView) findViewById(R.id.initial_data_sent_text);

        clientIdEdt = (EditText) findViewById(R.id.client_id_edt);

        refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(this);

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);

        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);

        checkSessionState();
    }

    private void checkSessionState() {
        if (AndroidData.statisticsEnabled(this)) {
            sessionActiveContainer.setVisibility(View.VISIBLE);
            androidDataTestContainer.setVisibility(View.GONE);

            clientIdText.setText(AndroidDataUtils.getUserId(this));
            deviceIdText.setText(AndroidDataUtils.getDeviceUID(this));
            profieTypeText.setText(AndroidData.getProfileType());
            DataManager dataManager = DataManager.getInstance(this);
            if (dataManager.isWifiOnly()) {
                wifiOnlyText.setText("WIFI Only");
            } else {
                wifiOnlyText.setText("WIFI + Mobile");
            }

            if (AndroidDataUtils.isInitialDataSent(this)) {
                initialDataSentText.setText("YES");
            } else {
                initialDataSentText.setText("NO");
            }

        } else {

            sessionActiveContainer.setVisibility(View.GONE);
            androidDataTestContainer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v == startButton) {
            String clientId = clientIdEdt.getText().toString();
            //Set client ID and start collection

            AndroidData.startAndroidData(this, clientId);

            Toast.makeText(this, "Data collection started!", Toast.LENGTH_LONG).show();
            checkSessionState();
        } else if (v == logoutButton) {
            AndroidData.clear(this);
            checkSessionState();
        } else if (v == refreshButton) {
            checkSessionState();
        }
    }


}

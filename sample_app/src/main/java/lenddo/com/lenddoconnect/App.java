package lenddo.com.lenddoconnect;

import android.app.Application;

import com.lenddo.data.AndroidData;
import com.lenddo.data.models.ClientOptions;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ClientOptions clientOptions = new ClientOptions();

        //Uncomment the next line when you want data to be uploaded only when wifi is available
        //clientOptions.setWifiOnly(true);

        AndroidData.setup(getApplicationContext(), getString(R.string.partner_script_id),
                getString(R.string.api_secret),
                clientOptions);
    }
}

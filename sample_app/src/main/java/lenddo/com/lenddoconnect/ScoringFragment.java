package lenddo.com.lenddoconnect;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.lenddo.core.LenddoCoreUtils;
import com.lenddo.data.AndroidData;
import com.lenddo.data.listeners.OnDataSendingCompleteCallback;
import com.lenddo.data.models.ClientOptions;
import com.lenddo.data.utils.AndroidDataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoringFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String PS_ID;
    String SECRET;

    // TODO: Rename and change types of parameters
    private TextInputEditText edt_applicationId;
    private Spinner spn_hostnames;
    private Spinner spn_connections;
    private CheckBox cb_enableDebugLogs;
    private CheckBox cb_enableSms;
    private CheckBox cb_enableCallLog;
    private CheckBox cb_enableContact;
    private CheckBox cb_enableCalendarEvents;
    private CheckBox cb_enableInstalledApps;
    private CheckBox cb_enableBrowserHistory;
    private CheckBox cb_enableLocation;
    private CheckBox cb_enableBatteryCharge;
    private CheckBox cb_enableGalleryMetaData;
    private CheckBox cb_enableSmsBody;
    private CheckBox cb_enablePhoneNumberHashing;
    private CheckBox cb_enableContactsNameHAshing;
    private CheckBox cb_enableContactsEmailHashing;
    private CheckBox cb_enableCalendarOrganizerHashing;
    private CheckBox cb_enableCalendarDisplayNameHashing;
    private CheckBox cb_enableCalendarEmailHashing;
    private CheckBox cb_enableCustomMPermission;
    private Button btn_start;

    private TextView tv_applicationId;
    private TextView tv_deviceId;
    private TextView tv_serviceToken;
    private TextView tv_installationId;
    private TextView tv_uploadMode;
    private TextView tv_hasUploadedInitial;

    public static final int STATE_NOTSTARTED = 0;
    public static final int STATE_STARTED = 1;

    private OnFragmentInteractionListener mListener;

    public ScoringFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScoringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoringFragment newInstance(String param1, String param2) {
        ScoringFragment fragment = new ScoringFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_scoring, container, false);
        setCredentials(null);
        initViews(fragmentView);
        updateDisplay(getContext());
        if (AndroidData.statisticsEnabled(getContext())) {
            edt_applicationId.setText(AndroidDataUtils.getApplicationId(getContext()));
            edt_applicationId.setSelection(edt_applicationId.length());
        }
        return fragmentView;
    }

    private void setCredentials(String hostname) {
        if (spn_hostnames == null) {
            PS_ID = getString(R.string.partner_script_id);
            SECRET = getString(R.string.api_secret);
        } else {
            if (hostname.equalsIgnoreCase(getResources().getStringArray(R.array.hostnames)[0])) {
                Log.i("DataSDK Demo", "Setting credentials to PROD.");
                PS_ID = getString(R.string.partner_script_id);
                SECRET = getString(R.string.api_secret);
            } else if (hostname.equalsIgnoreCase(getResources().getStringArray(R.array.hostnames)[1])) {
                Log.i("DataSDK Demo", "Setting credentials to PROD_KR.");
                PS_ID = getString(R.string.partner_script_id_kr);
                SECRET = getString(R.string.api_secret_kr);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (btn_start.getText().toString().equalsIgnoreCase("START DATA SDK")) {
                    Log.d("DataSDK Demo", "START button pressed.");
                    if (edt_applicationId.getText().length()>0) {
                        enableWidgets(false);
                        btn_start.setText("STOP&CLEAR DATA SDK");
                        tv_hasUploadedInitial.setText(Html.fromHtml("Data Sending Callback: <b>process currently running</b>"));
                        ((App) getActivity().getApplication()).setupDataSDK(PS_ID,SECRET,generateClientOptions());
                        AndroidData.startAndroidData(getActivity(), edt_applicationId.getText().toString());
                    } else {
                        enableWidgets(true);
                        edt_applicationId.requestFocus();
                        edt_applicationId.setError("This field is mandatory!");
                    }
                } else if (btn_start.getText().toString().equalsIgnoreCase("STOP&CLEAR DATA SDK")) {
                    Log.i("DataSDK Demo", "STOP button pressed. Clearing data.");
                    AndroidData.clear(getContext());
                    updateDisplay(getContext());
                    btn_start.setText("START DATA SDK");
                }
                break;
            default:
                break;
        }
    }

    private void initViews(View fragmentView) {
        edt_applicationId = (TextInputEditText) fragmentView.findViewById(R.id.edt_applicationId);
        spn_hostnames = (Spinner) fragmentView.findViewById(R.id.spn_hostnames);
        spn_connections = (Spinner) fragmentView.findViewById(R.id.spn_connections);
        cb_enableDebugLogs = (CheckBox) fragmentView.findViewById(R.id.cb_enableDebugLogs);
        cb_enableSms = (CheckBox) fragmentView.findViewById(R.id.cb_enableSms);
        cb_enableCallLog = (CheckBox) fragmentView.findViewById(R.id.cb_enableCallLog);
        cb_enableContact = (CheckBox) fragmentView.findViewById(R.id.cb_enableContact);
        cb_enableCalendarEvents = (CheckBox) fragmentView.findViewById(R.id.cb_enableCalendarEvents);
        cb_enableInstalledApps = (CheckBox) fragmentView.findViewById(R.id.cb_enableInstalledApps);
        cb_enableBrowserHistory = (CheckBox) fragmentView.findViewById(R.id.cb_enableBrowserHistory);
        cb_enableLocation = (CheckBox) fragmentView.findViewById(R.id.cb_enableLocation);
        cb_enableBatteryCharge = (CheckBox) fragmentView.findViewById(R.id.cb_enableBatteryCharge);
        cb_enableGalleryMetaData = (CheckBox) fragmentView.findViewById(R.id.cb_enableGalleryMetaData);
        cb_enableSmsBody = (CheckBox) fragmentView.findViewById(R.id.cb_enableSmsBody);
        cb_enablePhoneNumberHashing = (CheckBox) fragmentView.findViewById(R.id.cb_enablePhoneNumberHashing);
        cb_enableContactsNameHAshing = (CheckBox) fragmentView.findViewById(R.id.cb_enableContactsNameHashing);
        cb_enableContactsEmailHashing = (CheckBox) fragmentView.findViewById(R.id.cb_enableContactsEmailHashing);
        cb_enableCalendarOrganizerHashing = (CheckBox) fragmentView.findViewById(R.id.cb_enableCalendarOrganizerHashing);
        cb_enableCalendarDisplayNameHashing = (CheckBox) fragmentView.findViewById(R.id.cb_enableCalendarDisplayNameHashing);
        cb_enableCalendarEmailHashing = (CheckBox) fragmentView.findViewById(R.id.cb_enableCalendarEmailHashing);
        cb_enableCustomMPermission = (CheckBox) fragmentView.findViewById(R.id.cb_enableCustomMPermission);
        btn_start = (Button) fragmentView.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        tv_applicationId = (TextView) fragmentView.findViewById(R.id.tv_applicationId);
        tv_deviceId = (TextView) fragmentView.findViewById(R.id.tv_deviceId);
        tv_serviceToken = (TextView) fragmentView.findViewById(R.id.tv_serviceToken);
        tv_installationId = (TextView) fragmentView.findViewById(R.id.tv_installationId);
        tv_uploadMode = (TextView) fragmentView.findViewById(R.id.tv_uploadMode);
        tv_hasUploadedInitial = (TextView) fragmentView.findViewById(R.id.tv_hasUploadedInitial);

        // Set PS_ID when changing hostnames
        spn_hostnames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                setCredentials(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private ClientOptions generateClientOptions() {
        ClientOptions clientOptions = new ClientOptions();
        // Hostname (Gateway)
        clientOptions.setApiGatewayUrl(spn_hostnames.getSelectedItem().toString());
        // Upload Mode
        if (spn_connections.getSelectedItemPosition()==0) {
            clientOptions.setWifiOnly(false);
        } else {
            clientOptions.setWifiOnly(true);
        }
        // Debug Logs
        clientOptions.enableLogDisplay(cb_enableDebugLogs.isChecked());
        // Data types
        if (!cb_enableSms.isChecked()) clientOptions.disableSMSDataCollection();
        if (!cb_enableCallLog.isChecked()) clientOptions.disableCallLogDataCollection();
        if (!cb_enableContact.isChecked()) clientOptions.disableContactDataCollection();
        if (!cb_enableCalendarEvents.isChecked()) clientOptions.disableCalendarEventDataCollection();
        if (!cb_enableInstalledApps.isChecked()) clientOptions.disableInstalledAppDataCollection();
        if (!cb_enableBrowserHistory.isChecked()) clientOptions.disableBrowserHistoryDataCollection();
        if (!cb_enableLocation.isChecked()) clientOptions.disableLocationDataCollection();
        if (!cb_enableBatteryCharge.isChecked()) clientOptions.disableBattChargeDataCollection();
        if (!cb_enableGalleryMetaData.isChecked()) clientOptions.disableGalleryMetaDataCollection();
        // SMS Body Content
        if (!cb_enableSmsBody.isChecked()) clientOptions.disableSMSBodyCollection();
        //Data Hashing
        if (cb_enablePhoneNumberHashing.isChecked()) clientOptions.enablePhoneNumberHashing();
        if (cb_enableContactsNameHAshing.isChecked()) clientOptions.enableContactsNameHashing();
        if (cb_enableContactsEmailHashing.isChecked()) clientOptions.enableContactsEmailHashing();
        if (cb_enableCalendarOrganizerHashing.isChecked()) clientOptions.enableCalendarOrganizerHashing();
        if (cb_enableCalendarDisplayNameHashing.isChecked()) clientOptions.enableCalendarDisplayNameHashing();
        if (cb_enableCalendarEmailHashing.isChecked()) clientOptions.enableCalendarEmailHashing();
        //Custom M Permisson
        if (cb_enableCustomMPermission.isChecked()) {
            clientOptions.setCustomMPermissionLayout(
                    R.layout.custom_permissionintro,
                    R.layout.custom_permissionrationale,
                    R.layout.custom_permissionfeedback,
                    R.layout.custom_permissionthankyou);
            clientOptions.setThemeColor("#d61f64");
        }

        clientOptions.registerDataSendingCompletionCallback(new OnDataSendingCompleteCallback() {
            @Override
            public void onDataSendingSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateDisplay(getContext());
                        tv_applicationId.setText(Html.fromHtml("Application ID: <b>"+AndroidDataUtils.getApplicationId(getContext())));
                        tv_deviceId.setText(Html.fromHtml("Device ID: <b>"+AndroidDataUtils.getDeviceUID(getContext())));
                        tv_serviceToken.setText(Html.fromHtml("Service Token: <b>"+ AndroidDataUtils.getServiceToken(getContext())));
                        tv_installationId.setText(Html.fromHtml("Installation ID: <b>"+ LenddoCoreUtils.getInstallationId(getContext())));
                        tv_hasUploadedInitial.setText(Html.fromHtml("Data Sending Callback: <b>Success</b>"));
                    }
                });
            }

            @Override
            public void onDataSendingError(int statusCode, final String errorMessage) {
                if(isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateDisplay(getContext());
                            tv_serviceToken.setText(Html.fromHtml("Service Token: <b>"+AndroidDataUtils.getServiceToken(getContext())));
                            tv_installationId.setText(Html.fromHtml("Installation ID: <b>"+LenddoCoreUtils.getInstallationId(getContext())));
                            tv_hasUploadedInitial.setText(Html.fromHtml("Data Sending Callback: <b>Error:</b>"+errorMessage));
                        }
                    });
                }
            }

            @Override
            public void onDataSendingFailed(Throwable t) {
                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateDisplay(getContext());
                            tv_serviceToken.setText(Html.fromHtml("Service Token: <b>"+AndroidDataUtils.getServiceToken(getContext())));
                            tv_installationId.setText(Html.fromHtml("Installation ID: <b>"+LenddoCoreUtils.getInstallationId(getContext())));
                            tv_hasUploadedInitial.setText(Html.fromHtml("Data Sending Callback: <b>Failed</b>"));

                        }
                    });
                }
            }
        });
        return clientOptions;
    }

    private void updateDisplay(Context context) {
        if (AndroidData.statisticsEnabled(getContext())) {
            tv_applicationId.setText(Html.fromHtml("Application ID: <b>"+AndroidDataUtils.getApplicationId(context)));
            tv_deviceId.setText(Html.fromHtml("Device ID: <b>"+AndroidDataUtils.getDeviceUID(context)));
            tv_serviceToken.setText(Html.fromHtml("Service Token: <b>"+AndroidDataUtils.getServiceToken(context)));
            tv_installationId.setText(Html.fromHtml("Installation ID: <b>"+LenddoCoreUtils.getInstallationId(context)));
            tv_uploadMode.setText(Html.fromHtml("Upload Mode: <b>"+spn_connections.getSelectedItem().toString()));
            enableWidgets(false);
            btn_start.setText("STOP&CLEAR DATA SDK");
        } else {
            tv_applicationId.setText(Html.fromHtml("Application ID: <b>"+AndroidDataUtils.getApplicationId(context)));
//            tv_deviceId.setText(Html.fromHtml("Device ID: <b>"+AndroidDataUtils.getDeviceUID(context)));
            tv_serviceToken.setText(Html.fromHtml("Service Token: <b>"+AndroidDataUtils.getServiceToken(context)));
            tv_installationId.setText(Html.fromHtml("Installation ID: <b>"+LenddoCoreUtils.getInstallationId(context)));
            tv_uploadMode.setText(Html.fromHtml("Upload Mode: <b>"+spn_connections.getSelectedItem().toString()));
            tv_hasUploadedInitial.setText(Html.fromHtml("Data Sending Callback:"));
            enableWidgets(false);
            btn_start.setText("START DATA SDK");
            enableWidgets(true);
        }
        tv_hasUploadedInitial.requestFocus();
    }

    private void enableWidgets(boolean isEnable) {
        edt_applicationId.setEnabled(isEnable);
        spn_hostnames.setEnabled(isEnable);
        spn_connections.setEnabled(isEnable);
        cb_enableDebugLogs.setEnabled(isEnable);
        cb_enableSms.setEnabled(isEnable);
        cb_enableCallLog.setEnabled(isEnable);
        cb_enableContact.setEnabled(isEnable);
        cb_enableCalendarEvents.setEnabled(isEnable);
        cb_enableInstalledApps.setEnabled(isEnable);
        cb_enableBrowserHistory.setEnabled(isEnable);
        cb_enableLocation.setEnabled(isEnable);
        cb_enableBatteryCharge.setEnabled(isEnable);
        cb_enableGalleryMetaData.setEnabled(isEnable);
        cb_enableSmsBody.setEnabled(isEnable);
        cb_enablePhoneNumberHashing.setEnabled(isEnable);
        cb_enableContactsNameHAshing.setEnabled(isEnable);
        cb_enableContactsEmailHashing.setEnabled(isEnable);
        cb_enableCalendarOrganizerHashing.setEnabled(isEnable);
        cb_enableCalendarDisplayNameHashing.setEnabled(isEnable);
        cb_enableCalendarEmailHashing.setEnabled(isEnable);
        cb_enableCustomMPermission.setEnabled(isEnable);
    }
}

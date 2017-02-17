package lenddo.com.lenddoconnect;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lenddo.core.analytics.FormFillingAnalytics;
import com.lenddo.core.uiwidgets.TimedAutoCompleteTextView;
import com.lenddo.core.uiwidgets.TimedEditText;
import com.lenddo.data.AndroidData;
import com.lenddo.data.listeners.OnDataSendingCompleteCallback;
import com.lenddo.data.models.ApplicationPartnerData;
import com.lenddo.data.utils.AndroidDataUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TimedEditText tedt_referenceno;
    private TimedEditText tedt_application_payload;
    private TimedEditText tedt_firstname;
    private TimedEditText tedt_middlename;
    private TimedEditText tedt_lastname;
    private TimedEditText tedt_dob;
    private TimedEditText tedt_phone_mobile;
    private TimedEditText tedt_phone_home;
    private TimedEditText tedt_email;
    private TimedEditText tedt_employer;
    private TimedEditText tedt_university;
    private TimedEditText tedt_mother_firstname;
    private TimedEditText tedt_mother_middlename;
    private TimedEditText tedt_mother_lastname;
    private TimedEditText tedt_address_line1;
    private TimedEditText tedt_address_line2;
    private TimedEditText tedt_address_city;
    private TimedEditText tedt_address_adm_div;
    private TimedAutoCompleteTextView tedt_address_country_code;
    private TimedEditText tedt_address_postal_code;
    private TextInputEditText tedt_latitude;
    private TextInputEditText tedt_longitude;
    private Button btn_sendpartnerdata;

    private Spinner spn_providers;
    private TextInputEditText tedt_provider_id;
    private TextInputEditText tedt_prov_accesstoken;
    private TextInputEditText tedt_prov_extra_data;
    private TextInputEditText tedt_provider_expiration;
    private Button btn_sendprovideraccesstoken;

    private OnFragmentInteractionListener mListener;

    private TextView tv_sendpartnerdata_callback;
    private TextView tv_sendprovideraccesstoken_callback;


    public VerificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificationFragment newInstance(String param1, String param2) {
        VerificationFragment fragment = new VerificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_verification, container, false);
        initViews(fragmentView);

        if (AndroidData.statisticsEnabled(getContext())) {
            tedt_referenceno.setText(AndroidDataUtils.getClientId(getContext()));
            tedt_referenceno.setSelection(tedt_referenceno.length());
        }
        return fragmentView;
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

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    private void initViews(View fragmentView) {
        tedt_referenceno = (TimedEditText) fragmentView.findViewById(R.id.tedt_referenceno);
        tedt_application_payload = (TimedEditText) fragmentView.findViewById(R.id.tedt_application_payload);
        tedt_firstname = (TimedEditText) fragmentView.findViewById(R.id.tedt_firstname);
        tedt_middlename = (TimedEditText) fragmentView.findViewById(R.id.tedt_middlename);
        tedt_lastname = (TimedEditText) fragmentView.findViewById(R.id.tedt_lastname);
        tedt_dob = (TimedEditText) fragmentView.findViewById(R.id.tedt_dob);
        tedt_phone_mobile = (TimedEditText) fragmentView.findViewById(R.id.tedt_phone_mobile);
        tedt_phone_home = (TimedEditText) fragmentView.findViewById(R.id.tedt_phone_home);
        tedt_email = (TimedEditText) fragmentView.findViewById(R.id.tedt_email);
        tedt_employer = (TimedEditText) fragmentView.findViewById(R.id.tedt_employer);
        tedt_university = (TimedEditText) fragmentView.findViewById(R.id.tedt_university);
        tedt_mother_firstname = (TimedEditText) fragmentView.findViewById(R.id.tedt_mother_firstname);
        tedt_mother_middlename = (TimedEditText) fragmentView.findViewById(R.id.tedt_mother_middlename);
        tedt_mother_lastname = (TimedEditText) fragmentView.findViewById(R.id.tedt_mother_lastname);
        tedt_address_line1 = (TimedEditText) fragmentView.findViewById(R.id.tedt_address_line1);
        tedt_address_line2 = (TimedEditText) fragmentView.findViewById(R.id.tedt_address_line2);
        tedt_address_city = (TimedEditText) fragmentView.findViewById(R.id.tedt_address_city);
        tedt_address_adm_div = (TimedEditText) fragmentView.findViewById(R.id.tedt_address_adm_div);
        tedt_address_postal_code = (TimedEditText) fragmentView.findViewById(R.id.tedt_address_postal_code);
        tedt_address_country_code = (TimedAutoCompleteTextView) fragmentView.findViewById(R.id.tedt_address_country_code);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getContext(), android.R.layout.select_dialog_item, countries);
        tedt_address_country_code.setAdapter(adapter);
        tedt_latitude = (TextInputEditText) fragmentView.findViewById(R.id.tedt_latitude);
        tedt_longitude = (TextInputEditText) fragmentView.findViewById(R.id.tedt_longitude);
        btn_sendpartnerdata = (Button) fragmentView.findViewById(R.id.btn_sendpartnerdata);
        btn_sendpartnerdata.setOnClickListener(this);

        spn_providers = (Spinner) fragmentView.findViewById(R.id.spn_providers);
        tedt_provider_id = (TextInputEditText) fragmentView.findViewById(R.id.tedt_provider_id);
        tedt_prov_accesstoken = (TextInputEditText) fragmentView.findViewById(R.id.tedt_provider_accesstoken);
        tedt_prov_extra_data = (TextInputEditText) fragmentView.findViewById(R.id.tedt_provider_extradata);
        tedt_provider_expiration = (TextInputEditText) fragmentView.findViewById(R.id.tedt_provider_expiration);
        btn_sendprovideraccesstoken = (Button) fragmentView.findViewById(R.id.btn_sendprovideraccesstoken);
        btn_sendprovideraccesstoken.setOnClickListener(this);

        tv_sendpartnerdata_callback = (TextView) fragmentView.findViewById(R.id.tv_sendpartnerdata_callback);
        tv_sendprovideraccesstoken_callback = (TextView) fragmentView.findViewById(R.id.tv_sendprovideraccesstoken_callback);

        FormFillingAnalytics.getInstance(getContext()).add(tedt_referenceno.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_application_payload.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_firstname.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_middlename.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_lastname.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_dob.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_phone_mobile.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_phone_home.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_email.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_employer.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_university.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_mother_firstname.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_mother_middlename.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_mother_lastname.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_line1.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_line2.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_city.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_adm_div.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_country_code.getFormFillingAnalyticsDataValue());
        FormFillingAnalytics.getInstance(getContext()).add(tedt_address_postal_code.getFormFillingAnalyticsDataValue());
    }

    private String generatePartnerAppDataPayload() {
        ApplicationPartnerData.verification_data vd = new ApplicationPartnerData.verification_data();
        vd = new ApplicationPartnerData.verification_data();
        vd.address = new ApplicationPartnerData.verification_data.address();
        vd.employment_period = new ApplicationPartnerData.verification_data.employment_period();
        vd.mothers_maiden_name = new ApplicationPartnerData.verification_data.mothers_maiden_name();
        vd.name = new ApplicationPartnerData.verification_data.name();
        vd.phone = new ApplicationPartnerData.verification_data.phone();

        // Store data to model
        vd.name.first = Utils.returnNullIfEmpty(tedt_firstname.getText().toString());
        vd.name.middle = Utils.returnNullIfEmpty(tedt_middlename.getText().toString());
        vd.name.last = Utils.returnNullIfEmpty(tedt_lastname.getText().toString());
        vd.date_of_birth = Utils.returnNullIfEmpty(tedt_dob.getText().toString());
        vd.phone.mobile = Utils.returnNullIfEmpty(tedt_phone_mobile.getText().toString());
        vd.phone.home = Utils.returnNullIfEmpty(tedt_phone_home.getText().toString());
        vd.email = Utils.returnNullIfEmpty(tedt_email.getText().toString());
        vd.employer = Utils.returnNullIfEmpty(tedt_employer.getText().toString());
        vd.university = Utils.returnNullIfEmpty(tedt_university.getText().toString());
        vd.mothers_maiden_name.first = Utils.returnNullIfEmpty(tedt_mother_firstname.getText().toString());
        vd.mothers_maiden_name.middle = Utils.returnNullIfEmpty(tedt_mother_middlename.getText().toString());
        vd.mothers_maiden_name.last = Utils.returnNullIfEmpty(tedt_mother_lastname.getText().toString());
        vd.address.line_1 = Utils.returnNullIfEmpty(tedt_address_line1.getText().toString());
        vd.address.line_2 = Utils.returnNullIfEmpty(tedt_address_line2.getText().toString());
        vd.address.city = Utils.returnNullIfEmpty(tedt_address_city.getText().toString());
        vd.address.administrative_division = Utils.returnNullIfEmpty(tedt_address_adm_div.getText().toString());
        vd.address.country_code = Utils.returnNullIfEmpty(tedt_address_country_code.getText().toString());
        vd.address.postal_code = Utils.returnNullIfEmpty(tedt_address_postal_code.getText().toString());


        String lat = tedt_latitude.getText().toString();
        if (!lat.isEmpty()) {
            vd.address.latitude = Float.parseFloat(lat);
        }

        String longi = tedt_longitude.getText().toString();
        if (!lat.isEmpty()) {
            vd.address.longitude = Float.parseFloat(longi);
        }

        ApplicationPartnerData apd = new ApplicationPartnerData();
        apd.reference_number = Utils.returnNullIfEmpty(tedt_referenceno.getText().toString());
        String application_json = tedt_application_payload.getText().toString();
        if (application_json.isEmpty()) {
            apd.application = new JsonObject();
        } else {
            try {
                apd.application = new JsonParser().parse(application_json).getAsJsonObject();
            } catch (Exception e) {
                e.printStackTrace();
                apd.application = new JsonObject();
                tedt_application_payload.setError("Application payload not a valid JSON string");
            }
        }

        apd.verification_data = vd;

        String payload = new GsonBuilder().serializeSpecialFloatingPointValues().disableHtmlEscaping().create().toJson(apd);;
        Log.d("JSON", payload);
        return payload;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sendpartnerdata:
                if (getActivity()!=null) {
                    tv_sendpartnerdata_callback.setText(Html.fromHtml("Send Partner Data Callback: <b>process currently running</b>"));
                    AndroidData.sendPartnerApplicationData(getActivity(), generatePartnerAppDataPayload(), new OnDataSendingCompleteCallback() {
                        @Override
                        public void onDataSendingSuccess() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_sendpartnerdata_callback.setText(Html.fromHtml("Send Partner Data Callback: <b>Success</b>\nForm Filling Analytics sent!"));
                                    AndroidData.submitFormFillingAnalytics(getContext());
                                }
                            });
                        }

                        @Override
                        public void onDataSendingError(int statusCode, final String errorMessage) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_sendpartnerdata_callback.setText(Html.fromHtml("Send Partner Data Callback: <b>Error: </b>" + errorMessage));
                                }
                            });
                        }

                        @Override
                        public void onDataSendingFailed(final Throwable t) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_sendpartnerdata_callback.setText(Html.fromHtml("Send Partner Data Callback: <b>Failed: </b>" + t.getMessage()));
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.btn_sendprovideraccesstoken:
                tv_sendprovideraccesstoken_callback.setText(Html.fromHtml("Send Provider Access Token Callback: <b>process currently running</b>"));
                long expiration;
                if (tedt_provider_expiration.getText().toString().isEmpty()) {
                    expiration = 0;
                } else {
                    expiration = Long.parseLong(tedt_provider_expiration.getText().toString());
                }
                String extradata = tedt_prov_extra_data.getText().toString();
                extradata.trim();
                AndroidData.setProviderAccessToken(getContext(),
                        Utils.getProviderString(spn_providers.getSelectedItemPosition()),
                        tedt_provider_id.getText().toString(),
                        tedt_prov_accesstoken.getText().toString(),
                        extradata, expiration,
                        new OnDataSendingCompleteCallback() {
                            @Override
                            public void onDataSendingSuccess() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_sendprovideraccesstoken_callback.setText(Html.fromHtml("Send Provider Access Token Callback: <b>Success</b>"));
                                    }
                                });
                            }

                            @Override
                            public void onDataSendingError(int statusCode, final String errorMessage) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_sendprovideraccesstoken_callback.setText(Html.fromHtml("Send Provider Access Token Callback: <b>Error: </b>" + errorMessage));
                                    }
                                });

                            }

                            @Override
                            public void onDataSendingFailed(final Throwable t) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_sendprovideraccesstoken_callback.setText(Html.fromHtml("Send Provider Access Token Callback: <b>Failed: </b>" + t.getMessage()));
                                    }
                                });
                            }
                        });
                break;
            default:
                break;
        }
    }
}

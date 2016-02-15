Lenddo Data SDK
===============

The Lenddo Data SDK allows you to collect information about users in order for Lenddo to verify
the user's information and enhance its scoring capabilities. The Lenddo Data module collects
information transparently and in the background and can be activated as soon as the user has
finished the Lenddo Authorization process in your app (See LenddoSDK docs for details)

Prerequisites
=============

All requirements for the LenddoSDK apply to LenddoData as well. In addition to the following:

- Social Service secret Key
- Social Service API Key

Please ask for the information above from your Lenddo representative.

Installation Instructions
=========================

Android Studio (Gradle)
-----------------------

The Lenddo SDK should contain the LenddoData folder. Copy that folder to your project.

In the settings.gradle of your project, make sure to include LenddoData

    include ':LenddoSDK'
    include ':LenddoData'

Then add LenddoData as a dependency in your main apps, build.gradle, as below:

    dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        compile project(':AddressWidget')
        compile project(':LenddoSDK')

        ....

        compile project(':LenddoData')
    }

Add permissions
---------------

LenddoData will use information stored on the users' phone to obtain the information it needs.
However some of those require additional permissions. It is up to you to determine, which
permissions make sense for your app and for your users, however placing the recommended
permissions enable LenddoData to extract much more information for verification. The LenddoData
module will automatically detect which permissions are available and adjust its data collection
appropriately.

To Add/Remove permissions you may edit the LenddoData/src/main/AndroidManifest.xml.

Below is the list of required permissions:

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_SMS" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    <uses-permission android:name="android.permission.READ_CALENDAR" />
    <uses-permission android:name="android.permission.READ_CALL_LOG" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="com.android.browser.permission.READ_HISTORY_BOOKMARKS" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

If some permissions have already been defined in your main app, you simply ignore those here. Note
that this permissions will be presented to users during installation. Ensure also that enabling
these permissions are consistent with your Apps privacy policy.

Data Collection Mechanism
-------------------------

The LenddoData captures the following data stored on the phone consistent with the permissions defined:

* Contacts
* SMS (Performed Periodically)
* Call History
* User's Location (Performed Periodically)
* User's Browsing history
* Calendar Events
* Phone Number, Brand and Model

Initialize Data Collection
--------------------------

You need to add an Application module to your app (If it does not already have one)

See below for an example Application Class:

    package lenddo.com.lenddoconnect;

    import android.app.Application;

    import com.lenddo.sdk.core.Credentials;
    import com.lenddo.sdk.core.LenddoClient;

    public class SimpleLoan extends Application {
        @Override
        public void onCreate() {
            super.onCreate();

            Credentials memberServiceCredentials = new Credentials();
    //        memberServiceCredentials.setSecretKey(getString(R.string.member_service_secret));
    //        memberServiceCredentials.setUserId(getString(R.string.member_service_userid));

            Credentials productServiceCredentials = new Credentials();
    //        productServiceCredentials.setSecretKey(getString(R.string.product_service_secret));
    //        productServiceCredentials.setUserId(getString(R.string.product_service_userid));

            Credentials socialServiceCredentials = new Credentials();
            socialServiceCredentials.setSecretKey(getString(R.string.social_service_secret));
            socialServiceCredentials.setUserId(getString(R.string.social_service_userid));

            LenddoClient client = new LenddoClient(memberServiceCredentials, productServiceCredentials, socialServiceCredentials);
            DataManager.setup(client);
        }
    }

Note that you need to set the secret key and api key provided to you by Lenddo here:

            Credentials socialServiceCredentials = new Credentials();
            socialServiceCredentials.setSecretKey(getString(R.string.social_service_secret));
            socialServiceCredentials.setUserId(getString(R.string.social_service_userid));


After creating the Application class you need to add a android:name attribute to the application tag
in to your main apps' AndroidManifest.xml (example):

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="lenddo.com.lenddoconnect" >

        <application
            android:name=".SimpleLoan"
            android:allowBackup="true"
            android:icon="@drawable/ic_launcher"
            android:label="@string/app_name"
            android:theme="@style/AppTheme" >
        ......



Triggering Data Collection
--------------------------

You may trigger data collection as soon as the user has finished the Authorize process and a Lenddo
UserId is available. A perfect  place would be on the onAuthorizeComplete method of your Activity (
See the LenddoSDK for details)

    @Override
    public void onAuthorizeComplete(FormDataCollector collector) {
        Intent finishIntent = new Intent(SampleActivity.this, CompleteActivity.class);
        AuthorizationStatus status = collector.getAuthorizationStatus();
        finishIntent.putExtra("verification", status.getVerification());
        finishIntent.putExtra("status", status.getStatus());
        finishIntent.putExtra("userId", status.getUserId());
        finishIntent.putExtra("transId", status.getTransId());

        ......

        //Place LenddoData trigger here
        ......

        startActivity(finishIntent);
        finish();
    }

Adding this snippet will trigger Data Collection:

    DataManager.startAndroidData(this, status.getUserId());

This will look something like:

    @Override
    public void onAuthorizeComplete(FormDataCollector collector) {
        Intent finishIntent = new Intent(SampleActivity.this, CompleteActivity.class);
        AuthorizationStatus status = collector.getAuthorizationStatus();
        finishIntent.putExtra("verification", status.getVerification());
        finishIntent.putExtra("status", status.getStatus());
        finishIntent.putExtra("userId", status.getUserId());
        finishIntent.putExtra("transId", status.getTransId());

        Utils.setUserId(this, status.getUserId());
        DataManager.startAndroidData(this);

        startActivity(finishIntent);
        finish();
    }
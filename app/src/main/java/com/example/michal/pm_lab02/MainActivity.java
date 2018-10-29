package com.example.michal.pm_lab02;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SmsReceiver.Listener{


    private EditText numberEditText;
    private EditText messageEditText;
    private SharedPreferences sharedPreferences;

    private String messege;
    private String number;

    private SmsReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        /*init smsReceiver*/
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        smsReceiver = new SmsReceiver();
        smsReceiver.setScrollView(scrollView);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);

        /*init sharedPreferences*/
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        String res = preferences.getString("num", "empty");
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        if(!res.equals("empty")){
            numberEditText.setText(res);
        }
    }


    private void initViews() {
        numberEditText = (EditText) findViewById(R.id.et_number);
        messageEditText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.btn_normal_sms);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messege = messageEditText.getText().toString();
                number = numberEditText.getText().toString();

                Log.e("mess", messege);
                Log.e("mess", number);

                SendFragment fragment = SendFragment.newInstance(messege, number);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.linear_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                SmsHelper.sendDebugSms(number, messege);
                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        number = numberEditText.getText().toString();
        if(number.isEmpty()){
            editor.putString("num", "000");
        } else {
            editor.putString("num", number);
        }
        editor.commit();
    }


    @Override
    public void onSMSReceived(String text, String number) {
        SmsFragment fragment = SmsFragment.newInstance(text, number);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.linear_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

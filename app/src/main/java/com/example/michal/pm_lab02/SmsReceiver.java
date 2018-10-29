package com.example.michal.pm_lab02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";
    private ScrollView scrollView;
    private Listener listener;

    public SmsReceiver( ){

    }

    public void setScrollView(ScrollView view) {
        this.scrollView = view;
        Log.e("id", view.getId()+"");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
            }

            smsSender = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0].getOriginatingAddress();

            if(scrollView != null) {
                listener = (Listener)context;
                listener.onSMSReceived(smsBody, smsSender);
                Toast.makeText(context, smsSender + " " + smsBody, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "first reciver", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface Listener{
        public void onSMSReceived(String text, String number);
    }

}

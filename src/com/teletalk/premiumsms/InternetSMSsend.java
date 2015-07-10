package com.teletalk.premiumsms;



import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mediatek.telephony.SmsManagerEx;

public class InternetSMSsend extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet_smssend);
		Button buttonSend = (Button) findViewById(R.id.buttonSend);
        final EditText textSMS = (EditText) findViewById(R.id.SMS);
        final int simSelected = getIntent().getIntExtra("sim", 100);

        buttonSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String phoneNo = "39191";
                String sms = textSMS.getText().toString();

                try {String SENT = "SMS_SENT";
                    String DELIVERED = "SMS_DELIVERED";
                    PendingIntent sentPI = PendingIntent.getBroadcast(InternetSMSsend.this, 0, new Intent(SENT), 0);
                    PendingIntent deliveredPI = PendingIntent.getBroadcast(InternetSMSsend.this, 0, new Intent(DELIVERED), 0);
                    registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context arg0, Intent arg1) {
                            switch (getResultCode()) {
                                case Activity.RESULT_OK:
                                    Toast.makeText(getBaseContext(), "SMS sent",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    Toast.makeText(getBaseContext(), "Generic failure",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_NO_SERVICE:
                                    Toast.makeText(getBaseContext(), "No service",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_NULL_PDU:
                                    Toast.makeText(getBaseContext(), "Null PDU",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case SmsManager.RESULT_ERROR_RADIO_OFF:
                                    Toast.makeText(getBaseContext(), "Radio off",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }, new IntentFilter(SENT));
                    registerReceiver(new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context arg0, Intent arg1) {
                            switch (getResultCode()) {
                                case Activity.RESULT_OK:
                                    Toast.makeText(getBaseContext(), "SMS delivered",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case Activity.RESULT_CANCELED:
                                    Toast.makeText(getBaseContext(), "SMS not delivered",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }, new IntentFilter(DELIVERED));

//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNo, null, sms, sentPI, deliveredPI);
//                    Toast.makeText(getApplicationContext(), "SMS Sent!",
//                            Toast.LENGTH_LONG).show();
                    SmsManager smsManager = SmsManager.getDefault();
                    SmsManagerEx smsEx = SmsManagerEx.getDefault();

                    if (simSelected==0)
                        smsEx.sendTextMessage(phoneNo, null, sms, sentPI, deliveredPI, 0);
                    else if (simSelected==1)
                        smsEx.sendTextMessage(phoneNo, null, sms, sentPI, deliveredPI, 1);
                    else
                        smsManager.sendTextMessage(phoneNo, null, sms, sentPI, deliveredPI);

                    Log.e("PremiumSMS","InternetSMSSend: simSlot="+simSelected);
                    textSMS.setText("");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }


        });
		
	}


}

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
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class NcellMessage extends Activity {
    private int ps;
    private int status;
    private int cs;
    private final String phnum = String.valueOf("8000");
    private String msg;

    TextView serviceType;

    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncell_message);
        TextView serviceType = (TextView) findViewById(R.id.textView3n);
        ps = getIntent().getIntExtra("val1", 0);

        if(ps==horoscope_english){
            cs = getIntent().getIntExtra("val2", 0);
            String english = englishhoro(cs);
            serviceType.setText("Service Type: " + english);
            sendmsg();
        }
        else if(ps==horoscope_nepali){
            cs= getIntent().getIntExtra("val2",0);
            String nepali = nepalihoro(cs);
            serviceType.setText("Service Type: " + nepali);
            sendmsg();

        }
        else{
            cs = getIntent().getIntExtra("val2", 0);
            Log.e("PremiumSMS", "Else = "+cs);
            String typeOfService = getTypeOfService(cs);
            Log.e("PremiumSMS", "meg = "+msg);
            serviceType.setText("Service Type: " + typeOfService);
            sendmsg();
        }
    }

    private String getTypeOfService(int position) {
        SparseArray<String> message = new SparseArray<String>();
        message.put(3, "NEWS");
        message.put(4, "JOKES");
        message.put(5, "HEALTH");
        message.put(6, "BEAUTY");
        message.put(7, "LOVE");
        message.put(8, "JOBS");
        message.put(9, "DYK");
        message.put(10, "SHAYARI");
        message.put(11, "TOTD");
        message.put(12, "MOT");
        message.put(13, "FACTS");

        SparseArray<String> service = new SparseArray<String>();
        service.put(3, "News");
        service.put(4, "Jokes");
        service.put(5, "Health");
        service.put(6, "Beauty");
        service.put(7, "Love Tips");
        service.put(8, "Job Tips");
        service.put(9, "Do You Know");
        service.put(10, "Shayari");
        service.put(11, "Thought Of The Day");
        service.put(12, "Motivational Tips");
        service.put(13, "Amazing Facts");
        msg = message.get(position);
        return service.get(position);
    }
    void sendmsg() {
        TextView message = (TextView) findViewById(R.id.textView4n);
        message.setText("Message: " + msg);
        try {
            String SENT = "SMS_SENT";
            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
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
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phnum, null, msg, sentPI, null);
//            Toast.makeText(getApplicationContext(), "Message Send Successful.",
//                    Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Message Send Failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String nepalihoro(int position) {
        SparseArray<String> message = new SparseArray<String>();
        message.put(0, "MES");
        message.put(1, "BRI");
        message.put(2, "MIT");
        message.put(3, "KAR");
        message.put(4, "SIM");
        message.put(5, "KAN");
        message.put(6, "TUL");
        message.put(7, "VRI");
        message.put(8, "DHA");
        message.put(9, "MAK");
        message.put(10, "KUM");
        message.put(11, "MIN");

        SparseArray<String> service = new SparseArray<String>();
        service.put(0, "MESH");
        service.put(1, "BRISH");
        service.put(2, "MITHUN");
        service.put(3, "KARKAT");
        service.put(4, "SIMHA");
        service.put(5, "KANYA");
        service.put(6, "TULLA");
        service.put(7, "VRISHCHIK");
        service.put(8, "DHANU");
        service.put(9, "MAKAR");
        service.put(10, "KUMBHA");
        service.put(11, "MIN");

        msg = message.get(position);
        return service.get(position);

    }


    private String englishhoro(int position) {
        SparseArray<String> message = new SparseArray<String>();
        message.put(0, "ARI");
        message.put(1, "TAU");
        message.put(2, "GEM");
        message.put(3, "CAN");
        message.put(4, "LEO");
        message.put(5, "VIR");
        message.put(6, "LIB");
        message.put(7, "SCO");
        message.put(8, "SAG");
        message.put(9, "CAP");
        message.put(10, "AQU");
        message.put(11, "PIS");

        SparseArray<String> service = new SparseArray<String>();
        service.put(0, "ARIES");
        service.put(1, "TAURUS");
        service.put(2, "GEMINI");
        service.put(3, "CANCER");
        service.put(4, "LEO");
        service.put(5, "VIRGO");
        service.put(6, "LIBRA");
        service.put(7, "SCORPIO");
        service.put(8, "SAGITARIUS");
        service.put(9, "CAPRICORN");
        service.put(10, "AQUARIUS");
        service.put(11, "PISCES");

        msg = message.get(position);
        return service.get(position);


    }
}
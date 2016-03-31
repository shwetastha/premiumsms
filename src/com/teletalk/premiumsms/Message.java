package com.teletalk.premiumsms;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mediatek.telephony.SmsManagerEx;

public class Message extends Activity {
    //int[] values = new int[2];
    // ps bhanya val 1 jastai...horosope ko aagadi nepali ki english bhanya val2
    // chai subscribe ki unsubrsinhe ho.... tyo chai yeha status cha.... val3 chai current position... horoscope bnhaye rashi
    // horoscope hoina bhane tehe jokes news haru
    private int ps;
    private int status;
    private int cs;
    private int simSelected;

    private final String phnumNtc = String.valueOf("8000");
//    private final String phnumNcell = String.valueOf("8888");
    private final String phnum = String.valueOf("5000");
    private final String same = String.valueOf("39191");
    private final String voice_call_num = String.valueOf("1608");
    private String msg;
    TextView subUnsub;
    TextView simType;
    TextView serviceType;

    private final int voice_call = 1;
    private final int internet_sms = 2;
    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int service = 3;
    private final int misscall = 0;
    private final int statusSubscribe = 11111;
    private final int statusUnsubscribe = 10001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        try {
            TextView subUnsub = (TextView) findViewById(R.id.ser);

            TextView serviceType = (TextView) findViewById(R.id.textView3);

//            String typeOfSim = getTypeOfSim();

            ps = getIntent().getIntExtra("val1", 0);//1
            status = getIntent().getIntExtra("status", 0);//sub/unsub
            simSelected = getIntent().getIntExtra("sim", 100);

            if (ps == service) {
                if (status == statusSubscribe) {
                    subUnsub.setText("Subscribe Selected");
                } else if (status == statusUnsubscribe) {
                    subUnsub.setText("Unsubscribe Selected");
                }
                cs = getIntent().getIntExtra("val3", 0);
                if (cs == misscall) {
                    String typeOfService = getTypeOfService(cs);
                    serviceType.setText("Service Type: " + typeOfService);

                    sendmsg_misscall();
                } else {
                    String typeOfService = getTypeOfService(cs);
                    serviceType.setText("Service Type: " + typeOfService);

                    sendmsg();

                }
            } else if (ps == horoscope_english) {
                if (status == statusSubscribe) {
                    subUnsub.setText("Subscribe Selected");
                } else if (status == statusUnsubscribe) {
                    subUnsub.setText("Unsubscribe Selected");
                }
                cs = getIntent().getIntExtra("val3", 0);
                String english = englishhoro(cs);
                serviceType.setText("Service Type: " + english);
                sendmsg();
            } else if (ps == horoscope_nepali) {
                if (status == statusSubscribe) {
                    subUnsub.setText("Subscribe Selected");
                } else if (status == statusUnsubscribe) {
                    subUnsub.setText("Unsubscribe Selected");
                }
                cs = getIntent().getIntExtra("val3", 0);
                String nepali = nepalihoro(cs);
                serviceType.setText("Service Type: " + nepali);
                sendmsg();
            } else if (ps == internet_sms) {
                serviceType.setText("Service Type: Internet SMS");
                if (status == statusSubscribe) {
                    subUnsub.setText("Subscribe Selected");
                    msg = "Start Search";
                } else if (status == statusUnsubscribe) {
                    subUnsub.setText("Unsubscribe Selected");
                    msg = "Stop";
                }
                TextView message = (TextView) findViewById(R.id.textView4);
                message.setText("Message: " + msg);
                try {
                    String SENT = "SMS_SENT";
                    String DELIVERED = "SMS_DELIVERED";
                    PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
                    PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
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

                    SmsManager smsManager = SmsManager.getDefault();
                    //simType.setText(msg);


                        smsManager.sendTextMessage(same, null, msg,  sentPI, deliveredPI);
                        Toast.makeText(getApplicationContext(), "Message Send Successful.",
                                Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Message Send Failed.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else if (ps == voice_call) {
                serviceType.setText("Service Type: Voice Call");
                if (status == statusSubscribe) {
                    subUnsub.setText("Subscribe Selected");
                    msg = "SUB";
                } else if (status == statusUnsubscribe) {
                    subUnsub.setText("Unsubscribe Selected");
                    msg = "UNSUB";
                }
                sendmsg_voicecall();

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        message.put(0, "");

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
        service.put(0, "Miss Call Alert");
        switch (status) {
            case 11111:
                msg = "SUB " + message.get(position);
                return service.get(position);
            case 10001:
                msg = "UNSUB " + message.get(position);
                return service.get(position);
            default:
                return null;
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

        switch (status) {
            case 11111:
                msg = "RASHI " + message.get(position);
                return service.get(position);
            case 10001:
                msg = "UNSUB " + message.get(position);
                return service.get(position);
            default:
                return null;
        }
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

        switch (status) {
            case 11111:
                msg = "HORO " + message.get(position);
                return service.get(position);
            case 10001:
                msg = "UNSUB " + message.get(position);
                return service.get(position);
            default:
                return null;
        }
    }




    void sendmsg() {
        TextView message = (TextView) findViewById(R.id.textView4);
        message.setText("Message: " + msg);
        try {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";
            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
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
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI);

            SmsManager sms = SmsManager.getDefault();
            try {
                SmsManagerEx smsEx = SmsManagerEx.getDefault();

                if (simSelected == 0)
                    smsEx.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI, 0);
                else if (simSelected == 1)
                    smsEx.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI, 1);
                else
                    sms.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI);
            }catch (Error e){
                 sms.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Message Send Failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    void sendmsg_misscall() {
        TextView message = (TextView) findViewById(R.id.textView4);
        message.setText("Message: " + msg);
        try {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";
            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
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
            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phnum, null, msg,  sentPI, deliveredPI);
            try {
                SmsManagerEx smsEx = SmsManagerEx.getDefault();

                if (simSelected == 0)
                    smsEx.sendTextMessage(phnum, null, msg, sentPI, deliveredPI, 0);
                else if (simSelected == 1)
                    smsEx.sendTextMessage(phnum, null, msg, sentPI, deliveredPI, 1);
                else
                    sms.sendTextMessage(phnum, null, msg, sentPI, deliveredPI);
            }catch (Error e){
                sms.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Message Send Failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    void sendmsg_voicecall() {
        TextView message = (TextView) findViewById(R.id.textView4);
        message.setText("Message: " + msg);
        try {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";
            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
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
            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(voice_call_num, null, msg, sentPI, deliveredPI);
            try {
                SmsManagerEx smsEx = SmsManagerEx.getDefault();

                if (simSelected == 0)
                    smsEx.sendTextMessage(voice_call_num, null, msg, sentPI, deliveredPI, 0);
                else if (simSelected == 1)
                    smsEx.sendTextMessage(voice_call_num, null, msg, sentPI, deliveredPI, 1);
                else
                    sms.sendTextMessage(voice_call_num, null, msg, sentPI, deliveredPI);
            }catch (Error e){
                sms.sendTextMessage(phnumNtc, null, msg, sentPI, deliveredPI);
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Message Send Failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
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
}

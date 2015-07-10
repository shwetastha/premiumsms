package com.teletalk.premiumsms;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mediatek.telephony.TelephonyManagerEx;

public class Home_PremiumSms extends ListActivity {
    private final int operator_ntc = 88;
    private final int operator_ncell = 44;
    private final int operator_other = 0;
    private final int internet_sms = 2;
    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int serviceList = 3;
    private int statusSubscribe = 11111;
    private final int statusUnsubscribe = 10001;
    private final int call = 1;
    int operator, simSelected;
    String operator_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__premium_sms);
        simSelected = getIntent().getIntExtra("sim", 100);
        if (simSelected==0)
            operator =getTypeOfSim(0);
        else if (simSelected==1)
            operator = getTypeOfSim(1);
        else
            operator = getTypeOfSim();
        Log.e("PremiumSMS", "SELECTED = "+operator);
        setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.row, getResources().getStringArray(R.array.service_list)));
        ListView lv = getListView();


        //ListView OnclickListener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (operator == operator_ntc) {
                    Log.e("PremiumSMS", "SELECTED = NCELL");

                    if (position == call) {
                        //For Phone Call
                        Intent intent = new Intent(Home_PremiumSms.this, VoiceCall.class);
                        intent.putExtra("voice_call", call);
                        intent.putExtra("sim", simSelected);
                        startActivity(intent);
                    } else {
                        if (position == internet_sms) {
                            //For Internet SMS.
                            Intent intent = new Intent(Home_PremiumSms.this, InternetSMS.class);
                            intent.putExtra("internet_sms", internet_sms);
                            intent.putExtra("sim", simSelected);
                            startActivity(intent);
                        } else {
                            if (position == horoscope_nepali || position == horoscope_english) {
                                //For Horoscope.
                                Intent intent = new Intent(Home_PremiumSms.this, Horoscope.class);
                                //System.out.println("HOROSCOPE");
                                intent.putExtra("service", position);
                                intent.putExtra("sim", simSelected);
                                startActivity(intent);
                            } else {
                                //For News and other stuffs.
                                Intent intent = new Intent(Home_PremiumSms.this, SubscribeUnsubcribe.class);
                                System.out.println("servicelistIntet");

                                int val2 = 0;
                                intent.putExtra("service", serviceList);
                                intent.putExtra("val2", val2);
                                intent.putExtra("val3", position);
                                intent.putExtra("sim", simSelected);
                                startActivity(intent);
                            }
                        }
                    }
                } else if (operator == operator_ncell) {
                    Log.e("PremiumSMS", "SELECTED = NCELL");
                    if (position == call || position == internet_sms || position == 0) {
                        //For Phone Call
                        Toast.makeText(getBaseContext(), "This service is not allowed for Ncell", Toast.LENGTH_SHORT).show();
                    } else {
                        if (position == horoscope_nepali || position == horoscope_english) {
                            //For Horoscope.
                            Intent intent = new Intent(Home_PremiumSms.this, Horoscope.class);
                            intent.putExtra("sim", simSelected);
                            intent.putExtra("service", position);
                            startActivity(intent);
                        }
                        else {
                            //For News and other stuffs.
                            Intent intent = new Intent(Home_PremiumSms.this, NcellMessage.class);
                            int val2 = 0;

                            intent.putExtra("val1", val2);
                            intent.putExtra("val2", position);
                            intent.putExtra("sim", simSelected);
                            startActivity(intent);
                        }

                    }
                }
            }});
    }


    public class MyAdapter<T> extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         String[] strings) {
            super(context, resource, textViewResourceId, strings);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.custom_layout, parent, false);
            String[] items = getResources().getStringArray(R.array.service_list);
            TextView tv = (TextView) row.findViewById(R.id.row);
            tv.setText(items[position]);
            return row;
        }

    }
    private int getTypeOfSim() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //phone number line
        String OperatorName = tm.getSimOperatorName();

        if (OperatorName.equalsIgnoreCase("Namaste")) {
            return operator_ntc;
        } else if (OperatorName.equalsIgnoreCase("NCELL")) {
            return operator_ncell;
        } else
            return operator_other;
    }
    private int getTypeOfSim(int sim) {

        TelephonyManagerEx tm = new TelephonyManagerEx(Home_PremiumSms.this);
        //phone number line
        String OperatorName = tm.getSimOperatorName(sim);

        if (OperatorName.equalsIgnoreCase("Namaste")) {
            return operator_ntc;
        } else if (OperatorName.equalsIgnoreCase("NCELL")) {
            return operator_ncell;
        } else
            return operator_other;
    }

}

package com.teletalk.premiumsms;


import com.teletalk.premiumsms.Home_PremiumSms.MyAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Horoscope extends ListActivity {
    private int service;
    private int val1;
    private int val2;
    private int val3;

    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int operator_ntc = 88;
    private final int operator_ncell = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);
        final int operator =getTypeOfSim();
        service = getIntent().getExtras().getInt("service");
        if (service == horoscope_nepali) {
            setListAdapter(new NepaliAdapter<String>(this, android.R.layout.simple_list_item_1,R.id.row, getResources().getStringArray(R.array.HoroscopesNepali)));

            ListView lv = getListView();

            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    if (operator == operator_ntc) {
                        Intent intent = new Intent(Horoscope.this, SubscribeUnsubcribe.class);
                        intent.putExtra("service", service);
                        intent.putExtra("val3", position);
                        startActivity(intent);
                    }
                    else if(operator==operator_ncell){
                        Intent intent = new Intent(Horoscope.this, NcellMessage.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("val2", position);
                        startActivity(intent);
                    }
                }
            });
        } else if (service == horoscope_english) {
            setListAdapter(new EnglishAdapter<String>(this, android.R.layout.simple_list_item_1,R.id.row, getResources().getStringArray(R.array.HoroscopesEnglish)));

            ListView lv = getListView();

            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    if (operator == operator_ntc) {
                        Intent intent = new Intent(Horoscope.this, SubscribeUnsubcribe.class);

                        intent.putExtra("service", service);
                        intent.putExtra("val3", position);
                        startActivity(intent);
                    }
                    else if(operator==operator_ncell){
                        Intent intent = new Intent(Horoscope.this, NcellMessage.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("val2", position);
                        startActivity(intent);
                    }
                }
            });
        }

    }



    public class NepaliAdapter<T> extends ArrayAdapter<String> {

        public NepaliAdapter(Context context, int resource, int textViewResourceId,
                             String[] strings) {
            super(context, resource, textViewResourceId, strings);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.custom_layout, parent, false);
            String[] items = getResources().getStringArray(R.array.HoroscopesNepali);
            TextView tv = (TextView) row.findViewById(R.id.row);
            tv.setText(items[position]);
            return row;
        }
    }

    public class EnglishAdapter<T> extends ArrayAdapter<String> {

        public EnglishAdapter(Context context, int resource, int textViewResourceId,
                              String[] strings) {
            super(context, resource, textViewResourceId, strings);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.custom_layout, parent, false);
            String[] items = getResources().getStringArray(R.array.HoroscopesEnglish);
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
            return 0;
    }

}
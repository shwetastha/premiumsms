package com.teletalk.premiumsms;

import com.teletalk.premiumsms.Home_PremiumSms.MyAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SubscribeUnsubcribe extends ListActivity {
    int pos;
    private int val1;
    private int val2;
    private int val3;
    private final int voice_call = 1;
    private final int internet_sms = 2;
    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int serviceList = 3;
    private final int statusSubscribe = 11111;
    private final int statusUnsubscribe = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_unsubcribe);
        final int service = getIntent().getIntExtra("service", 0);
        setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.row, getResources().getStringArray(R.array.subscribeunsubscribe)));
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (position == 0) {
                    if (service == voice_call) {
                        Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("status", statusSubscribe);
                        startActivity(intent);
                    } else if (service == internet_sms) {

                        Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("status", statusSubscribe);
                        startActivity(intent);
                    } else {
                        if ((service == horoscope_nepali) || (service == horoscope_english)) {
                            val3 = getIntent().getIntExtra("val3", 0);
                            //serviceName
                            Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                            intent.putExtra("val1", service);
                            intent.putExtra("status", statusSubscribe);
                            intent.putExtra("val3", val3);
                            startActivity(intent);
                        } else {
                            int value1 = 3;
                            int value3 = getIntent().getIntExtra("val3", 0);
                            Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                            intent.putExtra("val3", value3);
                            intent.putExtra("status", statusSubscribe);
                            intent.putExtra("val1", value1);
                            startActivity(intent);
                        }
                    }
                }
                //unsubscrbe
                if (position == 1) {
                    if (service == voice_call) {
                        Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("status", statusUnsubscribe);
                        startActivity(intent);
                    }else if (service == internet_sms) {
                        val1 = getIntent().getIntExtra("service", 0);
                        Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                        intent.putExtra("val1", service);
                        intent.putExtra("status", statusUnsubscribe);
                        startActivity(intent);
                    } else {
                        if ((service == horoscope_nepali) || (service == horoscope_english)) {
                            val1 = getIntent().getIntExtra("service", 0);
                            val3 = getIntent().getIntExtra("val3", 0);
                            Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                            intent.putExtra("val1", service);
                            intent.putExtra("status", statusUnsubscribe);
                            intent.putExtra("val3", val3);
                            startActivity(intent);
                        } else {
                            int value1 = serviceList;
                            int value3 = getIntent().getIntExtra("val3", 0);
                            Intent intent = new Intent(SubscribeUnsubcribe.this, Message.class);
                            intent.putExtra("val3", value3);
                            intent.putExtra("status", statusUnsubscribe);
                            intent.putExtra("val1", value1);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
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
            String[] items = getResources().getStringArray(R.array.subscribeunsubscribe);
            TextView tv = (TextView) row.findViewById(R.id.row);
            tv.setText(items[position]);
            return row;
        }

    }
}

package com.teletalk.premiumsms;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ShwePC on 4/1/2015.
 */
public class VoiceCall extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call);
        final int voice_call = getIntent().getIntExtra("voice_call", 1);
        final int call = 1;
        setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.row, getResources().getStringArray(R.array.voicecall)));
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                if (position == call) {
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:1608"));
                    try {
                        startActivity(phoneIntent);
                        finish();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(VoiceCall.this, "Call failed, please try again later.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(VoiceCall.this, SubscribeUnsubcribe.class);
                    intent.putExtra("service", voice_call);
                    startActivity(intent);
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
            String[] items = getResources().getStringArray(R.array.voicecall);
            TextView tv = (TextView) row.findViewById(R.id.row);
            tv.setText(items[position]);
            return row;
        }


    }
}
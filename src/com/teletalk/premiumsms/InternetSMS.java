package com.teletalk.premiumsms;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class InternetSMS extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_internet_sms);
		final int internet_sms = getIntent().getIntExtra("internet_sms",14);
		final int message = 1;
		final int simSelected = getIntent().getIntExtra("sim", 100);

		setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1,R.id.row, getResources().getStringArray(R.array.internetsms)));
		ListView lv = getListView();
		  lv.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                      long arg3) {

                  if (position == message) {
                      Intent intent = new Intent(InternetSMS.this, InternetSMSsend.class);
					  intent.putExtra("sim", simSelected);
					  startActivity(intent);
                  } else {
                      Intent intent = new Intent(InternetSMS.this, SubscribeUnsubcribe.class );
                      intent.putExtra("service", internet_sms);
                      intent.putExtra("sim", simSelected);
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
		String[] items = getResources().getStringArray(R.array.internetsms); 
		TextView tv = (TextView) row.findViewById(R.id.row);
		tv.setText(items[position]);	
		return row;
		}
	 
		
		
	}

}

package com.teletalk.premiumsms;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceive extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		String phnum="";
		if (bundle != null)
		{
		//---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i=0; i<msgs.length; i++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				phnum =msgs[i].getOriginatingAddress().toString();
				str += msgs[i].getMessageBody().toString();
				
		}
			
			if (phnum.equalsIgnoreCase("39191"))
			{
				Toast.makeText(context,"Message received From COLORS WORLD", Toast.LENGTH_LONG).show();
			  Intent myIntent=new Intent(context, SMSReceived.class);   
			  myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			  myIntent.putExtra("name", str);
			  context.startActivity(myIntent);  
			}
			
			
			
		}

	}

}

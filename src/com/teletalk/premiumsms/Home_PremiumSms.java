package com.teletalk.premiumsms;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mediatek.telephony.TelephonyManagerEx;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;

public class Home_PremiumSms extends ListActivity {
    private final int operator_ntc = 88;
    private final int operator_ncell = 44;
    private final int operator_other = 0;
    private final int internet_sms = 2;
    private final int horoscope_nepali = 14;
    private final int horoscope_english = 15;
    private final int serviceList = 3;
    private final int statusUnsubscribe = 10001;
    private final int call = 1;
    int operator, simSelected;
    String operator_name;
    private int statusSubscribe = 11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__premium_sms);
        System.out.println("SIMINFO::"+isDualSimPresent());
        simSelected = getIntent().getIntExtra("sim", 100);
        try{
            if (simSelected==0)
                operator =getTypeOfSim(0);
            else if (simSelected==1)
                operator = getTypeOfSim(1);
            else
                operator = getTypeOfSim();
        }catch (Error e){
            operator = getTypeOfSim();
        }
        Log.e("PremiumSMS", "SELECTED = "+operator);
        setListAdapter(new MyAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.row, getResources().getStringArray(R.array.service_list)));
        ListView lv = getListView();


        //ListView OnclickListener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if (operator == operator_ntc) {
                    Log.e("PremiumSMS", "SELECTED = NTC");

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

    private boolean isDualSimPresent() {
        File dualSim = new File("/sdcard/.dualSimState.txt");

        try {
            dualSim.createNewFile();
            FileOutputStream fOut = new FileOutputStream(dualSim);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.close();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }


        Process p;
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("dumpsys | grep 'ACTION_IS_SIM_SMS_READY' > /sdcard/.dualSimState.txt\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        }catch(IOException e){
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;

            }
        }catch(InterruptedException e){
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                e1.printStackTrace();
                return false;

            }
        }

        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(dualSim));
            lnr.skip(Long.MAX_VALUE);
            System.out.println("line number "+lnr.getLineNumber()); //Add 1 because line index starts at 0
// Finally, the LineNumberReader object should be closed to prevent resource leak
            int line = lnr.getLineNumber();
            lnr.close();
            return ((line>2)?true:false);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

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

}

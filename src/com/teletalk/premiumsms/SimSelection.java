package com.teletalk.premiumsms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mediatek.telephony.TelephonyManagerEx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by ShwePC on 7/8/2015.
 */
public class SimSelection extends Activity {
    private final int operator_ntc = 88;
    private final int operator_ncell = 44;
    private final int operator_other = 0;

    Button sim1, sim2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_selection);
        int sim1_state, sim2_state;
        String sim1_op, sim2_op;
        try {
            TelephonyManagerEx tm = new TelephonyManagerEx(SimSelection.this);
            sim1_state = tm.getSimState(0);
            sim2_state = tm.getSimState(1);

            sim1_op = tm.getSimOperator(0);
            sim2_op = tm.getSimOperator(1);
        }catch (Error e){
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            sim1_state = tm.getSimState();
            sim2_state = TelephonyManager.SIM_STATE_ABSENT;

            sim1_op = tm.getSimOperator();
            sim2_op = "";
        }
        Log.e("PremiumSMS", "sim1_state=" + sim1_state + ",sim2_state=" + sim2_state);
        Log.e("PremiumSMS", "Simgetter=" + sim1_op + "==" + sim2_op);

        if ((sim1_state == TelephonyManager.SIM_STATE_READY && sim2_state ==TelephonyManager.SIM_STATE_READY)||isDualSimPresent().equalsIgnoreCase("true")) {
            Log.e("PremiumSMS", "case1");
            sim1 = (Button) findViewById(R.id.buttonSim1);
            sim2 = (Button) findViewById(R.id.buttonSim2);

            sim1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SimSelection.this, Home_PremiumSms.class);
                    intent.putExtra("sim", 0);
                    startActivity(intent);
                }
            });

            sim2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(SimSelection.this, Home_PremiumSms.class);
                    intent.putExtra("sim", 1);
                    startActivity(intent);
                }
            });
        }else {
            Intent intent = new Intent(SimSelection.this, Home_PremiumSms.class);
            startActivity(intent);
        }
    }


    private String isDualSimPresent() {
            File imei1 = new File("/sdcard/IMEI1.txt");
            File imei2 = new File("/sdcard/IMEI2.txt");

            try {
                imei1.createNewFile();
                imei2.createNewFile();
                FileOutputStream fOut = new FileOutputStream(imei1);
                FileOutputStream fOut2 = new FileOutputStream(imei2);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                OutputStreamWriter myOutWriter2 =
                        new OutputStreamWriter(fOut2);
                myOutWriter.close();
                myOutWriter2.close();
                fOut.close();
                fOut2.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "Exception";
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";

            }


            Process p;
            try{
                Process su = Runtime.getRuntime().exec("su");
                DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                outputStream.writeBytes("dumpsys telephony.registry0 | grep 'DataConnectionPossible' > /sdcard/IMEI1.txt\n");
                outputStream.writeBytes("dumpsys telephony.registry1 | grep 'DataConnectionPossible' > /sdcard/IMEI2.txt\n");
                outputStream.flush();
                outputStream.writeBytes("exit\n");
                outputStream.flush();
                su.waitFor();
            }catch(IOException e){
                try {
                    throw new Exception(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return "Exception";

                }
            }catch(InterruptedException e){
                try {
                    throw new Exception(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return "Exception";

                }
            }

            try {
                FileInputStream fIn = new FileInputStream(imei1);
            FileInputStream fIn2 = new FileInputStream(imei2);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
            BufferedReader myReader2 = new BufferedReader(
                    new InputStreamReader(fIn2));
                String temp = "";
                String c = "";
                while ((c = myReader.readLine()) != null) {
                    temp += c;
                }
                String temp2 = "";
                String c2 = "";
                while ((c2 = myReader2.readLine()) != null) {
                    temp2 += c2;
                }
//            String str_imei2 = temp2.substring(temp2.indexOf("Device ID ="),15);
                //string temp contains all the data of the file.
//            tv.setText("imei1=" + temp.replace("Device ID = ", "") + "\n==imei2=" + temp2.replace("Device ID = ", ""));
                myReader.close();
                myReader2.close();
                return temp.contains("true")&& temp2.contains("true")?"true":"false";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "Exception";
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }

    }

}

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

        if ((sim1_state == TelephonyManager.SIM_STATE_READY && sim2_state ==TelephonyManager.SIM_STATE_READY)) {
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
}

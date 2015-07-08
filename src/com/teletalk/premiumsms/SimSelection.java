package com.teletalk.premiumsms;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mediatek.telephony.TelephonyManagerEx;

import java.util.List;

/**
 * Created by ShwePC on 7/8/2015.
 */
public class SimSelection extends Activity {
    private final int operator_ntc = 88;
    private final int operator_ncell = 44;
    private final int operator_other = 0;

    TelephonyManagerEx tm;
    Button sim1, sim2;


    public int getSimSelected(int buttonPosition) {
        String operator_name;
        int simSelected;
        TelephonyManagerEx tm1 = new TelephonyManagerEx(getApplicationContext());
        operator_name = tm1.getSimOperator(buttonPosition);
        if (operator_name.equalsIgnoreCase("Namaste")) {
            simSelected = operator_ntc;
        } else if (operator_name.equalsIgnoreCase("NCELL")) {
            simSelected = operator_ncell;
        } else
            simSelected = operator_other;

        return simSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_selection);

        tm = new TelephonyManagerEx(SimSelection.this);
        int sim1_state = tm.getSimState(0);
        int sim2_state = tm.getSimState(1);

        String sim1_op = tm.getSimOperator(0);
        String sim2_op = tm.getSimOperator(1);
        Log.e("PremiumSMS", "sim1_state=" + sim1_state + ",sim2_state=" + sim2_state);
        Log.e("PremiumSMS", "Simgetter="+sim1_op+"=="+sim2_op);

        if (sim1_state == TelephonyManager.SIM_STATE_READY && sim2_state ==TelephonyManager.SIM_STATE_READY) {
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

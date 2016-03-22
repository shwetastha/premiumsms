package com.teletalk.premiumsms;

/**
 * Created by ShwePC on 2/17/2016.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ShellExecuter {

    public ShellExecuter() {

    }

    public String Executer(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes(command);
//            outputStream.writeBytes("dumpsys telephony.registry1 | grep 'DataConnectionPossible' > /sdcard/IMEI2.txt\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;

    }
}
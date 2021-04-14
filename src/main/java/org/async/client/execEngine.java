package org.async.client;

import commons.sender;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class execEngine {
    protected byte[] execute (String command, String workerName) {
        String result = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
//            processBuilder.inheritIO();
            processBuilder.command(command.split(" "));
            long start = System.nanoTime();
            Process process = processBuilder.start();
            process.waitFor();
            long elapsedTime = System.nanoTime() - start;
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String line="";

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
            }
            result = sb.toString();
            sender sender = new sender();
            byte[] fmsg = sender.setDataForMaster(result, elapsedTime, workerName);
            return fmsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

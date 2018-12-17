package com.del7a.nativelogs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class NativeLogs extends CordovaPlugin {
    private final String LOG_TAG = "CDVLOGCAT";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    private void clearLog() {
        LOG.d(LOG_TAG, "clearLog");
        try {
            Runtime.getRuntime().exec("logcat -c");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void testExceptionThrow() {
        if (true) throw new RuntimeException("DELIBERATELY THROWN TEST EXCEPTION");
    }

    public boolean execute(String action, JSONArray args, CallbackContext cbContext) throws JSONException {
        if (action.equals("getLog")) {
            String cliArgs = args.getString(0);

            LinkedList<String> log = getLogsFromLogCat(cliArgs);

            cbContext.success(new JSONArray(log));
            return true;
        }
        if (action.equals("testException")) {
            testExceptionThrow();
        }
        if (action.equals("clearLog")) {
            clearLog();
        }
        return false;
    }

    private  LinkedList<String> getLogsFromLogCat(String cliArgs) {
        LinkedList<String> logs = new LinkedList<String>();

        try {
            Process process = Runtime.getRuntime().exec("logcat -d -v time " + cliArgs);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line ;
            while (( line = bufferedReader.readLine()) != null) {
                logs.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }
}

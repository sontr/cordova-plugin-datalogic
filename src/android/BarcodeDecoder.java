package com.datalogic.cordova.decode;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.datalogic.decode.PropertyID;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeException;
import com.datalogic.decode.DecodeResult;
import com.datalogic.decode.ReadListener;
import com.datalogic.device.ErrorManager;

import android.util.Log;


public class BarcodeDecoder extends CordovaPlugin {

    private final String LOGTAG = getClass().getName();
    BarcodeManager decoder = null;
    ReadListener listener = null;
    String barcodeData = null;
    CallbackContext callbackContext = null;
    boolean multiTasking=true;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
        //initializeDecoder();
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext context) throws JSONException {

        if (action.equals("receive-barcode-data")) {
            callbackContext = context;
            // PluginResult result = new PluginResult(PluginResult.Status.OK, message);
            // result.setKeepCallback(true);
            // if(barcodeData == null){
            //     callbackContext.sendPluginResult(result);    
            // }
            return true;

        } else {
            
            return false;

        }
    }

    @Override
    public void onResume(boolean multiTasking){
        super.onResume(multiTasking);
        Log.i(LOGTAG, "onResume");

        // If the decoder instance is null, create it.
        if (decoder == null) { // Remember an onPause call will set it to null.
            decoder = new BarcodeManager();
        }

        // From here on, we want to be notified with exceptions in case of errors.
        ErrorManager.enableExceptions(true);

        try {

            // Create an anonymous class.
            listener = new ReadListener() {

                // Implement the callback method.
                @Override
                public void onRead(DecodeResult decodeResult) {
                    // Change the result to the current received result.
                    barcodeData = decodeResult.getText();
                    if(callbackContext != null){
                        PluginResult result = new PluginResult(PluginResult.Status.OK, barcodeData);
                        result.setKeepCallback(true);
                        callbackContext.sendPluginResult(result);
                    }
                }

            };

            // Remember to add it, as a listener.
            decoder.addReadListener(listener);

        } catch (DecodeException e) {
            Log.e(LOGTAG, "Error while trying to bind a listener to BarcodeManager", e);
        }
    }

    @Override
    public void onPause(boolean multiTasking) {
        super.onPause(multiTasking);

        Log.i(LOGTAG, "onPause");

        // If we have an instance of BarcodeManager.
        if (decoder != null) {
            try {
                // Unregister our listener from it and free resources.
                decoder.removeReadListener(listener);

                // Let the garbage collector take care of our reference.
                decoder = null;

                //gc callbackContext
                callbackContext = null;

            } catch (Exception e) {
                Log.e(LOGTAG, "Error while trying to remove a listener from BarcodeManager", e);
            }
        }
    }

    private void initializeDecoder(){
        // If the decoder instance is null, create it.
        if (decoder == null) { // Remember an onPause call will set it to null.
            decoder = new BarcodeManager();
        }

        // From here on, we want to be notified with exceptions in case of errors.
        ErrorManager.enableExceptions(true);

        try {

            // Create an anonymous class.
            listener = new ReadListener() {

                // Implement the callback method.
                @Override
                public void onRead(DecodeResult decodeResult) {
                    // Change the displayed text to the current received result.
                    barcodeData = decodeResult.getText();
                }

            };

            // Remember to add it, as a listener.
            decoder.addReadListener(listener);

        } catch (DecodeException e) {
            Log.e(LOGTAG, "Error while trying to bind a listener to BarcodeManager", e);
        }
    }
}

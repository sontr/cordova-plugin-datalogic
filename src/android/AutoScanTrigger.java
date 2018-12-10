package com.datalogic.cordova.device.input;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.datalogic.device.ErrorManager;
import com.datalogic.device.DeviceException;
import com.datalogic.device.input.KeyboardManager;
import com.datalogic.device.input.Trigger;
//import com.datalogic.device.input.AutoScanTrigger.Range;

import android.util.Log;
import android.os.Bundle;

import java.util.*;
import java.lang.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class AutoScanTrigger extends CordovaPlugin{
	private final String LOGTAG = getClass().getName();
    KeyboardManager keyboardManager = null;
    boolean multiTasking=true;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        try{
            // If the decoder instance is null, create it.
            if (keyboardManager == null) { // Remember an onPause call will set it to null.
                keyboardManager = new com.datalogic.device.input.KeyboardManager();
            }
            // From here on, we want to be notified with exceptions in case of errors.
            ErrorManager.enableExceptions(true);
        } 
        catch (Exception e) {
            Log.e(LOGTAG, "Error while initializing KeyboardManager", e);
            callbackContext.error("Error while initializing KeyboardManager");
            return false;
        }

        if(action.equals("isAvailable")) {
            JSONObject resultJson = new JSONObject();
            return isAvailable(callbackContext, resultJson);
        }
        else if(action.equals("getSupportedRanges")){
            JSONObject resultJson = new JSONObject();
            Gson gson = new Gson();
            return getSupportedRanges(callbackContext, resultJson, gson);
        }
        else if(action.equals("getCurrentRange")){
            JSONObject resultJson = new JSONObject();
            Gson gson = new Gson();
            return getCurrentRange(callbackContext, resultJson, gson);
        }
        else if(action.equals("setCurrentRange")){
            return setCurrentRange(callbackContext, data);
        }
        else{
            callbackContext.error("Incorrect action");
            return false;
        }
    }

    @Override
    public Bundle onSaveInstanceState(){
        Bundle state = new Bundle();
        return state;
    }

    @Override
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext){
        //this.callbackContext = callbackContext;
    }

    @Override
    public void onResume(boolean multiTasking){
        super.onResume(multiTasking);
        Log.i(LOGTAG, "onResume");
    }

    @Override
    public void onPause(boolean multiTasking) {
        super.onPause(multiTasking);
        Log.i(LOGTAG, "onPause");
    }

    private boolean isAvailable(CallbackContext callbackContext, JSONObject resultJson){
        boolean availableFlag = false;
    	try{
    		//DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();

            for(Trigger t : triggersList){
            	if(t instanceof com.datalogic.device.input.AutoScanTrigger){
            		availableFlag = true;
            		break;
            	}
            }

            resultJson.put("available", availableFlag);
            PluginResult result = new PluginResult(PluginResult.Status.OK, resultJson.toString());
            callbackContext.sendPluginResult(result);


    	}
    	catch (DeviceException e) {
    	    Log.e(LOGTAG, "Error while getting the available triggers", e);
    	    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the available triggers" );
    	    callbackContext.sendPluginResult(result);
    	    return false;
    	}
    	//for JSONException and other generic exceptions
        catch (Exception e) {
            Log.e(LOGTAG, "Error while getting the available triggers", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the available triggers" );
            callbackContext.sendPluginResult(result);
            return false;
        }

    	return availableFlag;
    }

    //retuns list of supported ranges. List will be empty if device does not support Auto Scan
    private boolean getSupportedRanges(CallbackContext callbackContext, JSONObject resultJson, Gson gson){
        try{
            //DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            List<com.datalogic.device.input.AutoScanTrigger.Range> rangeList = null;

            for(Trigger t : triggersList){
                if(t instanceof com.datalogic.device.input.AutoScanTrigger){
                    rangeList = ((com.datalogic.device.input.AutoScanTrigger)t).getSupportedRanges();
                    break;
                }
            }

            //If ranges list is not empty
            if (rangeList != null){
                resultJson.put("supportedRanges", new JSONArray(gson.toJson(rangeList)));
            }
            else{
                //empty array of ranges
                resultJson.put("supportedRanges", new JSONArray());
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, resultJson.toString());
            Log.d("AvailableAutoScanRanges", resultJson.toString() );
            callbackContext.sendPluginResult(result);

        }
        catch(DeviceException e){
            Log.e(LOGTAG, "Error while getting the available AutoScan ranges", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the available AutoScan ranges" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        //for JSONException and other generic exceptions
        catch (Exception e) {
            Log.e(LOGTAG, "Error while getting the available AutoScan ranges", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the available AutoScan ranges" );
            callbackContext.sendPluginResult(result);
            return false;
        }


        return true;
    }

    //retuns list of supported ranges. List will be empty if device does not support Auto Scan
    private boolean getCurrentRange(CallbackContext callbackContext, JSONObject resultJson, Gson gson){
        try{
            //DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            com.datalogic.device.input.AutoScanTrigger.Range currentRange = null;

            for(Trigger t : triggersList){
                if(t instanceof com.datalogic.device.input.AutoScanTrigger){
                    currentRange = ((com.datalogic.device.input.AutoScanTrigger)t).getCurrentRange();
                    break;
                }
            }

            //If range is not empty
            if (currentRange != null){
                resultJson.put("currentRange", new JSONObject(gson.toJson(currentRange)));
            }
            else{
                //null if not supported
                resultJson.put("currentRange", JSONObject.NULL);
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, resultJson.toString());
            Log.d("CurrentAutoScanRange", resultJson.toString() );
            callbackContext.sendPluginResult(result);

        }
        catch(DeviceException e){
            Log.e(LOGTAG, "Error while getting the current AutoScan ranges", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the current AutoScan ranges" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        //for JSONException and other generic exceptions
        catch (Exception e) {
            Log.e(LOGTAG, "Error while getting the current AutoScan ranges", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while getting the current AutoScan ranges" );
            callbackContext.sendPluginResult(result);
            return false;
        }


        return true;
    }

    //sets the current range for Auto Scan
    private boolean setCurrentRange(CallbackContext callbackContext, JSONArray data){
        try{
            //if the value at index doesn't exist or cannot be coerced to a int.
            int rangeId = data.getInt(0);
            boolean successFlag = false;

            //DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            List<com.datalogic.device.input.AutoScanTrigger.Range> rangeList = null;

            for(Trigger t : triggersList){
                if(t instanceof com.datalogic.device.input.AutoScanTrigger){
                    rangeList = ((com.datalogic.device.input.AutoScanTrigger)t).getSupportedRanges();
                    //check if rangeId is in the rangeList
                    for(com.datalogic.device.input.AutoScanTrigger.Range range : rangeList){
                        if(range.getId() == rangeId){
                            //true if the range has been set successfully, false otherwise.
                            successFlag = ((com.datalogic.device.input.AutoScanTrigger)t).setCurrentRange(range);
                            break;
                        }
                    }  
                    break;
                }
            }

            //If ranges list is empty, Device does not support AUtoScan. Retrun false with error response
            if(rangeList == null){
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Can not set AutoScan range since deive does not support AutoScan" );
                callbackContext.sendPluginResult(result);
                return false;
            }

            //check if range was set successfully
            if(!successFlag){
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Unable to successfully set the AutoScan Range" );
                callbackContext.sendPluginResult(result);
                return false;
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, "Successfully set the AutoScan Range" );
            callbackContext.sendPluginResult(result);
          
        }
        catch (DeviceException e) {
            Log.e(LOGTAG, "Error while setting the AutoScan Rage", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the AutoScan Rage" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch(JSONException e){
            Log.e(LOGTAG, "Incorrect integer value for setting the AutoScan Range", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Incorrect integer value for setting the AutoScan Range" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        //other generic exceptions
        catch (Exception e) {
            Log.e(LOGTAG, "Error while setting the AutoScan Range", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the AutoScan Range" );
            callbackContext.sendPluginResult(result);
            return false;
        }

        return true;
    }




}
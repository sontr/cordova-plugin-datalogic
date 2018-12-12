package com.datalogic.cordova.device.notification;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.datalogic.device.ErrorManager;
import com.datalogic.device.DeviceException;

import android.util.Log;
import android.os.Bundle;

import java.util.*;
import java.lang.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.datalogic.cordova.device.notification.LedConfig;


public class LedManager extends CordovaPlugin{
	private final String LOGTAG = getClass().getName();
	boolean multiTasking=true;
	com.datalogic.device.notification.LedManager ledManager = null;

	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
    	try{
            // From here on, we want to be notified with exceptions in case of errors.
            ErrorManager.enableExceptions(true);
            // If the ledManager instance is null, create it.
            if (ledManager == null) {
            	//DeviceException	in case of error.
                ledManager = new com.datalogic.device.notification.LedManager();
            }
        } 
        catch (Exception e) {
            Log.e(LOGTAG, "Error while initializing LedManager", e);
            callbackContext.error("Error while initializing LedManager");
            return false;
        }

        if(action.equals("setLed")) {
        	Gson gson = new Gson();
            return setLed(callbackContext, data, gson);
        }
        else if(action.equals("")){
            return true;
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

    //Enables or disables led
    private boolean setLed(CallbackContext callbackContext, JSONArray data, Gson gson){
    	boolean successFlag = false;
    	try{
    		JSONObject inputArg = data.getJSONObject(0);
    		//check if the json input is in correct format;
    		//JsonSyntaxException - if json is not a valid representation for an object of type classOfT
    		LedConfig ledConfig = gson.fromJson(inputArg.toString(), LedConfig.class);
    		//Log.d("Led", ledConfig.getLedFromName().toString() );
    		//check if the led is valid
    		if(ledConfig.getLedFromName() == null){
    			PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Invalid Led Enum value in Json input");
    			callbackContext.sendPluginResult(result);
    			return false;
    		}

    		//set the led
    		successFlag = ledManager.setLed(ledConfig.getLedFromName(), ledConfig.getEnable()) == DeviceException.SUCCESS ? true : false;

    		if(!successFlag){
    			PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Unable to set the Led");
    			callbackContext.sendPluginResult(result);
    			return false;
    		}

    		PluginResult result = new PluginResult(PluginResult.Status.OK, "Successfully set the Led" );
    		callbackContext.sendPluginResult(result);
    	}
    	catch(JsonSyntaxException e){
    	    Log.e(LOGTAG, e.getMessage(), e);
    	    PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Invalid Json input format");
    	    callbackContext.sendPluginResult(result);
    	    return false;
    	}
    	catch(JSONException e){
            Log.e(LOGTAG, "Invalid Json input format", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Invalid Json input format" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch (DeviceException e) {
            Log.e(LOGTAG, "Error while setting the Led", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the Led" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch (Exception e) {
            Log.e(LOGTAG, "Error while setting the Led", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the Led");
            callbackContext.sendPluginResult(result);
            return false;
        }

    	return successFlag;
    }


}

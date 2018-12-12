package com.datalogic.cordova.device.input;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.datalogic.decode.BarcodeManager;
//import com.datalogic.device.input.KeyboardManager;
import com.datalogic.device.input.Trigger;
import com.datalogic.device.ErrorManager;
import com.datalogic.device.DeviceException;
import com.datalogic.device.input.VScanEntry;

import android.util.Log;
import android.os.Bundle;

import java.util.*;
import java.lang.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

//import com.datalogic.cordova.device.input.Trigger;

public class KeyboardManager extends CordovaPlugin {

    private final String LOGTAG = getClass().getName();
    com.datalogic.device.input.KeyboardManager keyboardManager = null;
    boolean multiTasking=true;
    //set of all triggers
    //private HashMap allTriggers;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView){
        super.initialize(cordova, webView);
        // From here on, we want to be notified with exceptions in case of errors.
        //ErrorManager.enableExceptions(true);
        // allTriggers = new HashMap<Integer, String>();
        // allTriggers.put(5, "TRIGGER_ID_MOTION");
        // allTriggers.put(4, "TRIGGER_ID_AUTOSCAN");
        // allTriggers.put(3, "TRIGGER_ID_FRONT");
        // allTriggers.put(2, "TRIGGER_ID_PISTOL");
        // allTriggers.put(1, "TRIGGER_ID_RIGHT");
        // allTriggers.put(0, "TRIGGER_ID_LEFT");

    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        try{
            // From here on, we want to be notified with exceptions in case of errors.
            ErrorManager.enableExceptions(true);
            // If the keyboardManager instance is null, create it.
            if (keyboardManager == null) {
                keyboardManager = new com.datalogic.device.input.KeyboardManager();
            }
            
        } 
        catch (Exception e) {
            Log.e(LOGTAG, "Error while initializing KeyboardManager", e);
            callbackContext.error("Error while initializing KeyboardManager");
            return false;
        }

        if(action.equals("getAllAvailableTriggers")) {
            JSONObject triggers = new JSONObject();
            Gson gson = new Gson();
            return getAllAvailableTriggers(callbackContext, triggers, gson);
        } 
        else if(action.equals("setAllAvailableTriggers")){
            return setAllAvailableTriggers(callbackContext, data);
        }
        else if(action.equals("setTriggers")){
            Gson gson = new Gson();
            return setTriggers(callbackContext, data, gson);
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

        // // If we have an instance of BarcodeManager.
        // if (keyboardManager != null) {
        //     keyboardManager = null;
        // }
    }

    //sends available triggers on the device to the callbackcontext
    private boolean getAllAvailableTriggers(CallbackContext callbackContext, JSONObject triggers, Gson gson){

        try{
            //JSONException - If there is no value for the index or if the value is not convertible to boolean.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            
            JSONArray triggersObjArray = new JSONArray();

            for(Trigger t : triggersList){
                com.datalogic.cordova.device.input.Trigger trigger = new com.datalogic.cordova.device.input.Trigger(t.getId(), t.getName(), t.isEnabled());
                triggersObjArray.put(new JSONObject(gson.toJson(trigger)));
            }

            triggers.put("triggers", triggersObjArray);

            PluginResult result = new PluginResult(PluginResult.Status.OK, triggers.toString());
            Log.d("AllAvailableTriggers", triggers.toString() );
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

        return true;

    }

    //sets all trigger on the device
    private boolean setAllAvailableTriggers(CallbackContext callbackContext, JSONArray data){

        try{
            //JSONException - If there is no value for the index or if the value is not convertible to boolean.
            boolean enable = data.getBoolean(0);
            //DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            StringBuilder errMsg = new StringBuilder();
            for(Trigger t : triggersList){
                //if could not successfully set any of the available triggers
                if(!t.setEnabled(enable)){
                    errMsg.append(t.getName() + " ");
                }
            }

            //check if all trigers are set successfully
            if(errMsg.length() != 0){
                Log.e(LOGTAG, "Unable to set the triggers " + errMsg);
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Unable to set the triggers " + errMsg );
                callbackContext.sendPluginResult(result);
                return false;
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, "Successfully set all triggers");
            callbackContext.sendPluginResult(result);
        }
        catch (DeviceException e) {
            Log.e(LOGTAG, "Error while setting the available triggers", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the available triggers" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch(JSONException e){
            Log.e(LOGTAG, "Incorrect boolean value for setting the trigger", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Incorrect boolean value for setting the trigger" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        //other generic exceptions
        catch (Exception e) {
            Log.e(LOGTAG, "Error while setting the available triggers", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the available triggers" );
            callbackContext.sendPluginResult(result);
            return false;
        }

        return true;

    }

    private boolean setTriggers(CallbackContext callbackContext, JSONArray data, Gson gson){
        try{
            //JSONException - If there is no value for the index or if the value is not convertible to JSONArray.
            JSONArray triggersArray = data.getJSONArray(0);
            //id => enabled
            HashMap<Integer, Boolean> triggersMap = new HashMap<Integer, Boolean>();

            for(int i=0; i<triggersArray.length(); i++){
                //JSONException - If there is no value for the index or if the value is not a JSONObject
                JSONObject trigger = triggersArray.getJSONObject(i);
                //JsonSyntaxException - if json is not a valid representation for an object of type classOfT
                com.datalogic.cordova.device.input.Trigger triggerObj = gson.fromJson(trigger.toString(), com.datalogic.cordova.device.input.Trigger.class);
                triggersMap.put(triggerObj.getId(), triggerObj.getEnabled());
            }

            //DeviceException in case of error, when exceptions are enabled through the ErrorManager singleton.
            List<Trigger> triggersList = keyboardManager.getAvailableTriggers();
            StringBuilder errMsg = new StringBuilder();
            for(Trigger t : triggersList){
                //if user requested to set this trigger and could not successfully set 
                if( triggersMap.containsKey(t.getId()) && !t.setEnabled(triggersMap.get(t.getId())) ){
                    errMsg.append(t.getName() + " ");
                }
            }

            //check if all trigers are set successfully
            if(errMsg.length() != 0){
                Log.e(LOGTAG, "Unable to set the triggers " + errMsg);
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Unable to set the triggers " + errMsg );
                callbackContext.sendPluginResult(result);
                return false;
            }

            PluginResult result = new PluginResult(PluginResult.Status.OK, "Successfully set the triggers");
            callbackContext.sendPluginResult(result);



        }
        catch(JsonSyntaxException e){
            Log.e(LOGTAG, e.getMessage(), e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch(JSONException e){
            Log.e(LOGTAG, "Invalid input for setting the triggers. Input should should be a array of json objects and each json object should have a valid id and a boolean value to set the trigger.", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Invalid input for setting the triggers. Input should should be a array of json objects and each json object should have a valid id and a boolean value to set the trigger" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch (DeviceException e) {
            Log.e(LOGTAG, "Error while setting the triggers", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the triggers" );
            callbackContext.sendPluginResult(result);
            return false;
        }
        catch (Exception e) {
            Log.e(LOGTAG, "Error while setting the triggers", e);
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, "Error while setting the triggers" );
            callbackContext.sendPluginResult(result);
            return false;
        }

        return true;
    }

    
}

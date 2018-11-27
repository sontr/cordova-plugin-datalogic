package com.datalogic.cordova.test;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;


public class Test extends CordovaPlugin{

	@Override
    public boolean execute(String action, JSONArray data, CallbackContext context) throws JSONException {

        if (action.equals("test")) {
            
            context.success("Response from test feature");
            return true;

        } else {
            
            return false;

        }
    }
}
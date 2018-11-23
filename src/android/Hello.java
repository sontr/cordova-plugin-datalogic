package com.datalogic.cordova.decode;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import com.datalogic.decode.PropertyID;

public class Hello extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("greet")) {

            String name = data.getString(0);
            //String message = "Hello, " + name;
            String message = Integer.toString(PropertyID.AIM_ENABLE);
	    callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}

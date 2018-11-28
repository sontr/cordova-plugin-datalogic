package com.datalogic.cordova.decode.configuration;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeException;
import com.datalogic.decode.configuration.IntentDeliveryMode;
import com.datalogic.decode.configuration.IntentWedge;
//import com.datalogic.decode.configuration.ScannerProperties;
import com.datalogic.device.ErrorManager;
import com.datalogic.device.configuration.BooleanProperty;
import com.datalogic.device.configuration.CharacterProperty;
import com.datalogic.device.configuration.ConfigException;
import com.datalogic.device.configuration.EnumProperty;
import com.datalogic.device.configuration.NumericProperty;
import com.datalogic.device.configuration.TextProperty;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


public class ScannerProperties extends CordovaPlugin{

	@Override
    public boolean execute(String action, JSONArray data, CallbackContext context) throws JSONException {

        if (action.equals("edit")) {
            
            BarcodeManager manager = new BarcodeManager();    
            com.datalogic.decode.configuration.ScannerProperties configuration = 
                com.datalogic.decode.configuration.ScannerProperties.edit(manager);

    
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(EnumProperty.class, new EnumPropertySerializer());
            gsonBuilder.registerTypeAdapter(NumericProperty.class, new NumericPropertySerializer());
            gsonBuilder.registerTypeAdapter(BooleanProperty.class, new BooleanPropertySerializer());
            gsonBuilder.registerTypeAdapter(CharacterProperty.class, new CharacterPropertySerializer());
            gsonBuilder.registerTypeAdapter(TextProperty.class, new TextPropertySerializer());
    
            ExclusionStrategy strategy = new ExclusionStrategy() {
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
    
                @Override
                public boolean shouldSkipField(FieldAttributes field) {
                    if (field.getName().compareTo("_list") == 0)
                        return true;
                    if (field.getName().compareTo("_groupList") == 0)
                        return true;
                    else
                        return false;
                }
            };
    
            Gson gson = gsonBuilder
                    .setPrettyPrinting()
                    .addSerializationExclusionStrategy(strategy)
                    .create();

            String json = gson.toJson(configuration);

            context.success(json);
            return true;

        } else {
            
            return false;

        }
    }
}

class EnumPropertySerializer implements  JsonSerializer<EnumProperty> {
    public JsonElement serialize(EnumProperty src, Type typeofSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", src.get().toString());
        return jsonObject;
    }
}

class NumericPropertySerializer implements  JsonSerializer<NumericProperty> {
    public JsonElement serialize(NumericProperty src, Type typeofSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("max", src.getMax());
        jsonObject.addProperty("min", src.getMin());
        jsonObject.addProperty("value", src.get());
        return jsonObject;
    }
}

class BooleanPropertySerializer implements  JsonSerializer<BooleanProperty> {
    public JsonElement serialize(BooleanProperty src, Type typeofSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", src.get());
        return jsonObject;
    }
}

class CharacterPropertySerializer implements  JsonSerializer<CharacterProperty> {
    public JsonElement serialize(CharacterProperty src, Type typeofSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", src.get());
        return jsonObject;
    }
}

class TextPropertySerializer implements  JsonSerializer<TextProperty> {
    public JsonElement serialize(TextProperty src, Type typeofSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", src.get());
        return jsonObject;
    }
}
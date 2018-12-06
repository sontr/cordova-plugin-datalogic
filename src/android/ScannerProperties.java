package com.datalogic.cordova.decode.configuration;

import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.DecodeException;
import com.datalogic.decode.configuration.IntentDeliveryMode;
import com.datalogic.decode.configuration.IntentWedge;
import com.datalogic.device.ErrorManager;
import com.datalogic.device.configuration.BooleanProperty;
import com.datalogic.device.configuration.CharacterProperty;
import com.datalogic.device.configuration.ConfigException;
import com.datalogic.device.configuration.EnumProperty;
import com.datalogic.device.configuration.NumericProperty;
import com.datalogic.device.configuration.TextProperty;
import com.datalogic.device.configuration.PropertyGroup;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


public class ScannerProperties extends CordovaPlugin{

    private final String LOGTAG = "CODY";

    void propSetEnable(PropertyGroup pg, BooleanProperty en, JSONObject all, String key)
    {
        if (pg.isSupported())
        {
            try {
                en.set(all.getJSONObject(key).getBoolean("enable"));
            }
            catch (JSONException e) { }
        }
    }

	@Override
    public boolean execute(String action, JSONArray data, CallbackContext context) throws JSONException {
        
        if (action.equals("edit")) {
            
            return execute_edit_manual(context);

            //return execute_edit(context);

        } else if (action.equals("store")) {

            BarcodeManager manager = new BarcodeManager();    
            com.datalogic.decode.configuration.ScannerProperties cfg = 
                com.datalogic.decode.configuration.ScannerProperties.edit(manager);

            // data should look something like this:
            //[{"aztec":{"characterSetMode":"WINDOWS_1252","Length1":1,"Length2":3750,"lengthMode":"RANGE","enable":false,"userID":"d"},"codabar":{"convertToCLSI":false,
            //Log.d(LOGTAG, "data: " + data.toString());

            //Log.d(LOGTAG, "aztec.isSupported: " + cfg.aztec.isSupported() + ", .isFullySupported: " + cfg.aztec.isFullySupported());
            //Log.d(LOGTAG, "digimarc.isSupported: " + cfg.digimarc.isSupported() + ", .isFullySupported: " + cfg.digimarc.isFullySupported());

            JSONObject all = data.getJSONObject(0);
            Log.d(LOGTAG, "all: " + all.toString());

            //String aztecStr = all.getString("aztec");
            //Log.d(LOGTAG, "aztec: " + aztecStr);
            
            // TODO - pass ProprertyGroup instead. in propSetEnable, verify isSupported() first.
            propSetEnable(cfg.aztec, cfg.aztec.enable, all, "aztec");
            propSetEnable(cfg.codabar, cfg.codabar.enable, all, "codabar");
            propSetEnable(cfg.code128, cfg.code128.enable, all, "code128");
            propSetEnable(cfg.code39, cfg.code39.enable, all, "code39");
            propSetEnable(cfg.code93, cfg.code93.enable, all, "code93");
            propSetEnable(cfg.composite, cfg.composite.enable, all, "composite");
            propSetEnable(cfg.datamatrix, cfg.datamatrix.enable, all, "datamatrix");
            propSetEnable(cfg.discrete25, cfg.discrete25.enable, all, "discrete25");
            propSetEnable(cfg.ean13, cfg.ean13.enable, all, "ean13");
            propSetEnable(cfg.ean8, cfg.ean8.enable, all, "ean8");
            propSetEnable(cfg.gs1DataBar_14, cfg.gs1DataBar_14.enable, all, "gs1DataBar_14");
            propSetEnable(cfg.gs1DataBar_Expanded, cfg.gs1DataBar_Expanded.enable, all, "gs1DataBar_Expanded");
            propSetEnable(cfg.gs1DataBar_Limited, cfg.gs1DataBar_Limited.enable, all, "gs1DataBar_Limited");
            propSetEnable(cfg.interleaved25, cfg.interleaved25.enable, all, "interleaved25");
            propSetEnable(cfg.matrix25, cfg.matrix25.enable, all, "matrix25");
            propSetEnable(cfg.maxicode, cfg.maxicode.enable, all, "maxicode");
            propSetEnable(cfg.microQr, cfg.microQr.enable, all, "microQr");
            propSetEnable(cfg.micropdf417, cfg.micropdf417.enable, all, "micropdf417");
            propSetEnable(cfg.msi, cfg.msi.enable, all, "msi");
            propSetEnable(cfg.p4State, cfg.p4State.enable, all, "p4State");
            propSetEnable(cfg.pAus, cfg.pAus.enable, all, "pAus");
            propSetEnable(cfg.pJap, cfg.pJap.enable, all, "pJap");
            propSetEnable(cfg.pKix, cfg.pKix.enable, all, "pKix");
            propSetEnable(cfg.pPlanet, cfg.pPlanet.enable, all, "pPlanet");
            propSetEnable(cfg.pPostnet, cfg.pPostnet.enable, all, "pPostnet");
            propSetEnable(cfg.pRM, cfg.pRM.enable, all, "pRM");
            propSetEnable(cfg.pdf417, cfg.pdf417.enable, all, "pdf417");
            propSetEnable(cfg.qrCode, cfg.qrCode.enable, all, "qrCode");
            propSetEnable(cfg.upcA, cfg.upcA.enable, all, "upcA");
            propSetEnable(cfg.upcE, cfg.upcE.enable, all, "upcE");
        
            // not supported on Memor 10 - cfg.digimarc.enable.set(all.getJSONObject("digimarc").getBoolean("enable"));    
            // not supported on Memor 10 - cfg.dotcode.enable.set(all.getJSONObject("dotcode").getBoolean("enable"));

            // displayNotification
            // dnotification
            // format
            // goodread
            // intentWedge
            // keyboardWedge
            // linearQZ
            // multiScan
            // scannerOptions
            // upcEanExtensions
            // webWedge

            // should return com.datalogic.device.configuration.ConfigException.SUCCESS
            cfg.store(manager, true);

            // is this necessary?
            context.success("success");
            return true;

        } else {
            return false;

        }
    }

    // void jsonAddObjAndBoolean(JSONObject base, String objName, String key, boolean b)
    // {
    //     JSONObject obj = new JSONObject();
    //     try {
    //         obj.put(key, b);
    //         base.put(objName, obj);
    //     }
    //     catch (JSONException e) {

    //     }
    // }

    boolean execute_edit_manual(CallbackContext context)
    {
        BarcodeManager manager = new BarcodeManager();    
        com.datalogic.decode.configuration.ScannerProperties sp = 
            com.datalogic.decode.configuration.ScannerProperties.edit(manager);

        JSONObject cfg = new JSONObject();

        // newObj =   ... newObj.addExtra()     
        //jsonAddObjAndBoolean(cfg, "aztec", "enable", sp.aztec.enable.get());
        //jsonAddObjAndBoolean(cfg, "codabar", "enable", sp.codabar.enable.get());

        try {
            cfg.put("aztec", (new JSONObject()).put("enable", sp.aztec.enable.get()));
            cfg.put("codabar", (new JSONObject()).put("enable", sp.codabar.enable.get()));

        }
        catch (JSONException e) { }

        Log.d(LOGTAG, "execute_edit_manual: " + cfg.toString());

        context.success(cfg.toString());
        return true;
    }

    boolean execute_edit(CallbackContext context)
    {
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
                //.setPrettyPrinting()
                .addSerializationExclusionStrategy(strategy)
                .create();

        String json = gson.toJson(configuration);

        context.success(json);
        return true;
    }
}

class EnumPropertySerializer implements  JsonSerializer<EnumProperty> {
    public JsonElement serialize(EnumProperty src, Type typeofSrc, JsonSerializationContext context) {
        
        return new JsonPrimitive(src.get().toString());
        //JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("value", src.get().toString());
        //return jsonObject;
    }
}

class NumericPropertySerializer implements  JsonSerializer<NumericProperty> {
    public JsonElement serialize(NumericProperty src, Type typeofSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.get());
        //JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("max", src.getMax());
        //jsonObject.addProperty("min", src.getMin());
        //jsonObject.addProperty("value", src.get());
        //return jsonObject;
    }
}

class BooleanPropertySerializer implements  JsonSerializer<BooleanProperty> {
    public JsonElement serialize(BooleanProperty src, Type typeofSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.get());
        //JsonObject jsonObject = new JsonObject();
        //jsonObject.add();
        //jsonObject.addProperty("value", src.get());
        //return jsonObject;
    }
}

class CharacterPropertySerializer implements  JsonSerializer<CharacterProperty> {
    public JsonElement serialize(CharacterProperty src, Type typeofSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.get());
        //JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("value", src.get());
        //return jsonObject;
    }
}

class TextPropertySerializer implements  JsonSerializer<TextProperty> {
    public JsonElement serialize(TextProperty src, Type typeofSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.get());
        //JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("value", src.get());
        //return jsonObject;
    }
}
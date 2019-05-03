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

    private final String LOGTAG = "ScannerProperties";

	@Override
    public boolean execute(String action, JSONArray data, CallbackContext context) throws JSONException 
    {  
        if (action.equals("edit")) {
            
            return edit(context);

        } else if (action.equals("store")) {

            return store(data, context);

        } else {
            return false;

        }
    }

    boolean edit(CallbackContext context)
    {
        BarcodeManager manager = new BarcodeManager();    
        com.datalogic.decode.configuration.ScannerProperties sp = 
            com.datalogic.decode.configuration.ScannerProperties.edit(manager);

        JSONObject cfg = new JSONObject();

        try 
        {
            cfg.put("keyboardWedge", (new JSONObject()).put("enable", sp.keyboardWedge.enable.get()).put("supported", sp.keyboardWedge.isSupported()));

            cfg.put("aztec", (new JSONObject()).put("enable", sp.aztec.enable.get()).put("supported", sp.aztec.isSupported()));
            cfg.put("codabar", (new JSONObject()).put("enable", sp.codabar.enable.get()).put("supported", sp.codabar.isSupported()));
            cfg.put("code128", (new JSONObject()).put("enable", sp.code128.enable.get()).put("supported", sp.code128.isSupported()));
            cfg.put("code39", (new JSONObject()).put("enable", sp.code39.enable.get()).put("supported", sp.code39.isSupported()));
            cfg.put("code93", (new JSONObject()).put("enable", sp.code93.enable.get()).put("supported", sp.code93.isSupported()));
            cfg.put("composite", (new JSONObject()).put("enable", sp.composite.enable.get()).put("supported", sp.composite.isSupported()));
            cfg.put("datamatrix", (new JSONObject()).put("enable", sp.datamatrix.enable.get()).put("supported", sp.datamatrix.isSupported()));
            cfg.put("digimarc", (new JSONObject()).put("enable", sp.digimarc.enable.get()).put("supported", sp.digimarc.isSupported()));
            cfg.put("discrete25", (new JSONObject()).put("enable", sp.discrete25.enable.get()).put("supported", sp.discrete25.isSupported()));
            // error on Memor 10: cfg.put("dotcode", (new JSONObject()).put("enable", sp.dotcode.enable.get()).put("supported", sp.dotcode.isSupported()));
            cfg.put("ean13", (new JSONObject()).put("enable", sp.ean13.enable.get()).put("supported", sp.ean13.isSupported()));
            cfg.put("ean8", (new JSONObject()).put("enable", sp.ean8.enable.get()).put("supported", sp.ean8.isSupported()));
            cfg.put("gs1DataBar_14", (new JSONObject()).put("enable", sp.gs1DataBar_14.enable.get()).put("supported", sp.gs1DataBar_14.isSupported()));
            cfg.put("gs1DataBar_Expanded", (new JSONObject()).put("enable", sp.gs1DataBar_Expanded.enable.get()).put("supported", sp.gs1DataBar_Expanded.isSupported()));
            cfg.put("gs1DataBar_Limited", (new JSONObject()).put("enable", sp.gs1DataBar_Limited.enable.get()).put("supported", sp.gs1DataBar_Limited.isSupported()));
            cfg.put("interleaved25", (new JSONObject()).put("enable", sp.interleaved25.enable.get()).put("supported", sp.interleaved25.isSupported()));
            cfg.put("matrix25", (new JSONObject()).put("enable", sp.matrix25.enable.get()).put("supported", sp.matrix25.isSupported()));
            cfg.put("maxicode", (new JSONObject()).put("enable", sp.maxicode.enable.get()).put("supported", sp.maxicode.isSupported()));
            cfg.put("microQr", (new JSONObject()).put("enable", sp.microQr.enable.get()).put("supported", sp.microQr.isSupported()));
            cfg.put("micropdf417", (new JSONObject()).put("enable", sp.micropdf417.enable.get()).put("supported", sp.micropdf417.isSupported()));
            cfg.put("msi", (new JSONObject()).put("enable", sp.msi.enable.get()).put("supported", sp.msi.isSupported()));
            cfg.put("p4State", (new JSONObject()).put("enable", sp.p4State.enable.get()).put("supported", sp.p4State.isSupported()));
            cfg.put("pAus", (new JSONObject()).put("enable", sp.pAus.enable.get()).put("supported", sp.pAus.isSupported()));
            cfg.put("pJap", (new JSONObject()).put("enable", sp.pJap.enable.get()).put("supported", sp.pJap.isSupported()));
            cfg.put("pKix", (new JSONObject()).put("enable", sp.pKix.enable.get()).put("supported", sp.pKix.isSupported()));
            cfg.put("pPlanet", (new JSONObject()).put("enable", sp.pPlanet.enable.get()).put("supported", sp.pPlanet.isSupported()));
            cfg.put("pPostnet", (new JSONObject()).put("enable", sp.pPostnet.enable.get()).put("supported", sp.pPostnet.isSupported()));
            cfg.put("pRM", (new JSONObject()).put("enable", sp.pRM.enable.get()).put("supported", sp.pRM.isSupported()));
            cfg.put("pdf417", (new JSONObject()).put("enable", sp.pdf417.enable.get()).put("supported", sp.pdf417.isSupported()));
            cfg.put("qrCode", (new JSONObject()).put("enable", sp.qrCode.enable.get()).put("supported", sp.qrCode.isSupported()));
            cfg.put("upcA", (new JSONObject()).put("enable", sp.upcA.enable.get()).put("supported", sp.upcA.isSupported()));
            cfg.put("upcE", (new JSONObject()).put("enable", sp.upcE.enable.get()).put("supported", sp.upcE.isSupported()));
        }
        catch (JSONException e) 
        { 
            context.error("could not build JSON response");
            return false;
        }

        //Log.d(LOGTAG, "edit: " + cfg.toString());

        context.success(cfg.toString());
        return true;
    }

    boolean store(JSONArray data, CallbackContext context)
    {
        BarcodeManager manager = new BarcodeManager();    
        com.datalogic.decode.configuration.ScannerProperties cfg = 
            com.datalogic.decode.configuration.ScannerProperties.edit(manager);
        
        JSONObject all;
        try 
        {
            all = data.getJSONObject(0);
            //Log.d(LOGTAG, "store: " + all.toString());

            propSetEnable(cfg.keyboardWedge, cfg.keyboardWedge.enable, all, "keyboardWedge");

            // note: digimarc and dotcote are not supported on Memor 10
            propSetEnable(cfg.aztec, cfg.aztec.enable, all, "aztec");
            propSetEnable(cfg.codabar, cfg.codabar.enable, all, "codabar");
            propSetEnable(cfg.code128, cfg.code128.enable, all, "code128");
            propSetEnable(cfg.code39, cfg.code39.enable, all, "code39");
            propSetEnable(cfg.code93, cfg.code93.enable, all, "code93");
            propSetEnable(cfg.composite, cfg.composite.enable, all, "composite");
            propSetEnable(cfg.datamatrix, cfg.datamatrix.enable, all, "datamatrix");
            propSetEnable(cfg.digimarc, cfg.digimarc.enable, all, "digimarc");
            propSetEnable(cfg.discrete25, cfg.discrete25.enable, all, "discrete25");
            //propSetEnable(cfg.dotcode, cfg.dotcode.enable, all, "dotcode");
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
        }
        catch (JSONException e)
        {
            context.error("could not parse received JSON " + e.getMessage());
            return false;
        }

        // still need to handle these:
        // displayNotification
        // dnotification
        // format
        // goodread
        // intentWedge
        // linearQZ
        // multiScan
        // scannerOptions
        // upcEanExtensions
        // webWedge

        int ret = cfg.store(manager, true);
        if (ret != ConfigException.SUCCESS)
        {
            context.error("could not parse received JSON err " + ret);
            return false;
        }

        context.success("success");
        return true;
    }

    void propSetEnable(PropertyGroup pg, BooleanProperty en, JSONObject all, String key) throws JSONException 
    {
        if (pg.isSupported())
        {
            en.set(all.getJSONObject(key).getBoolean("enable"));
        }
    }
}

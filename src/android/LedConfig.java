package com.datalogic.cordova.device.notification;

import com.datalogic.device.notification.Led;

import java.util.*;
import java.lang.*;

import android.util.Log;


public class LedConfig{
	private String led;
	private boolean enable;
	private int argb;

	public LedConfig(String led, boolean enable){
		this.led = led;
		this.enable = enable;
	}

	public LedConfig(String led, boolean enable, int argb){
		this.led = led;
		this.enable = enable;
		this.argb = argb;
	}

	public String getLed(){
		return this.led;
	}

	public boolean getEnable(){
		return this.enable;
	}

	public int getArgb(){
		return this.argb;
	}

	public Led getLedFromName(){
		Led notificationLed = null;
		try{
			notificationLed = Led.valueOf(this.led.trim());
		}
		catch(Exception e){
			Log.e(getClass().getName(), "Error while converting Led Enum string Led Enum constant", e);
			return null;
		}

		return notificationLed;

	}
}
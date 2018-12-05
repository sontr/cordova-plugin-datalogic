package com.datalogic.cordova.device.input;

import java.util.*;
import java.lang.*;

public class Trigger{
	private int id;
	private String name;
	private boolean enabled;


	public Trigger(int id, boolean enabled){
		this.id = id;
		this.enabled = enabled;
	}

	public Trigger(int id, String name, boolean enabled){
		this.id = id;
		this.name = name;
		this.enabled = enabled;
	}

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public boolean getEnabled(){
		return this.enabled;
	}
}



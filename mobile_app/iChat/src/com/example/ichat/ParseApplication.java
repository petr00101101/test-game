package com.example.ichat;

import com.parse.Parse;
//import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application{
	
	public void onCreate()
	{
		super.onCreate();
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "FBeps34ylMieNIIGg7aYySDuQ64DptAWKVeHuixZ", "TRDP5DkAmBAJTvYB5VCG7apB73b9ha1kovh7lJHO");
	}

}

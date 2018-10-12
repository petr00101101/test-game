package com.example.ichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	

	public void logout(View view)
	{
	    Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	
	}
	
	public void chat(View view)
	{
		
		Intent intent = new Intent(this,contacts.class);
		startActivity(intent);
	    
	}
	public void changePass(View view)
	{
		
		Intent intent = new Intent(this,ChangePassword.class);
		startActivity(intent);
	    
	}
}

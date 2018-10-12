package com.example.ichat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity
{
	EditText old,new1,con;
	SharedPreferences settings;
	SharedPreferences.Editor edit;
	public static final String MyPREFERENCES = "MyPrefs" ;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pass);
	}
	
	public void done(View view)
	{
		
		old  = (EditText)findViewById(R.id.editText1);
		new1 = (EditText)findViewById(R.id.editText2);
		con  = (EditText)findViewById(R.id.editText3);
		
		String str = old.getText().toString();
		
		if(str.equals(MainActivity.check))
		{
			String str1 = new1.getText().toString();
			String str2 = con.getText().toString();
			
			if(str1.equals(str2))
			{
			    settings = getSharedPreferences(MyPREFERENCES,0);
				edit = settings.edit();
				edit.putString("passwordKey",str1);
				edit.commit();
				Toast t;
				t = Toast.makeText(getApplicationContext(),"Password changed!!",Toast.LENGTH_LONG);
				t.show();
			}
			else
			{
				Toast t;
				t = Toast.makeText(getApplicationContext(),"Password doesn't match!!",Toast.LENGTH_LONG);
				t.show();	
			}
		}
		else
		{
			Toast t;
			t = Toast.makeText(getApplicationContext(),"Old password not correct..!!",Toast.LENGTH_LONG);
			t.show();
		}
	}	

}

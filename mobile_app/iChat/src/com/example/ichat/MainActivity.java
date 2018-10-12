package com.example.ichat;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	   private EditText username,password;
	   public static final String MyPREFERENCES = "MyPrefs" ;
	   public static final String name = "nameKey"; 
	   public static String pass = "passwordKey";
	   public String button = "t";
	   public static String snd_no;
	   public static String check;
	  // public static String []phoneNo;
	   SharedPreferences setting;
	   SharedPreferences.Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "FBeps34ylMieNIIGg7aYySDuQ64DptAWKVeHuixZ", "TRDP5DkAmBAJTvYB5VCG7apB73b9ha1kovh7lJHO");
	    
	     setting = getSharedPreferences(MyPREFERENCES,0);
	     
		 String buttonView;
		    
		 buttonView = setting.getString(button,button);
		 
		 if(buttonView.equals("f"))
		 {
			  Button submit = (Button)findViewById(R.id.button3) ;
			   Button login = (Button)findViewById(R.id.button1) ;
			       username = (EditText)findViewById(R.id.editText1);
			       password = (EditText)findViewById(R.id.editText2);
			 TextView _pass =  (TextView)findViewById(R.id.contact_tv);
			    EditText no = (EditText)findViewById(R.id.no_et);
			 TextView no_tv = (TextView)findViewById(R.id.no);
			 
			 submit.setVisibility(View.INVISIBLE);
			 username.setVisibility(View.INVISIBLE);
			 password.setText("");
			 _pass.setVisibility(View.INVISIBLE);
			 no.setVisibility(View.INVISIBLE);
			 no_tv.setVisibility(View.INVISIBLE);
			 login.setVisibility(View.VISIBLE);
		 }
	     
		}
	
	protected void onRestart()
	{
		super.onRestart();
		
		 String buttonView;
		    
		 buttonView = setting.getString(button,button);
		 
		 if(buttonView.equals("f"))
		 {
			  Button submit = (Button)findViewById(R.id.button3) ;
			   Button login = (Button)findViewById(R.id.button1) ;
			       username = (EditText)findViewById(R.id.editText1);
			       password = (EditText)findViewById(R.id.editText2);
			 TextView _pass =  (TextView)findViewById(R.id.contact_tv);
			    EditText no = (EditText)findViewById(R.id.no_et);
			 TextView no_tv = (TextView)findViewById(R.id.no);
			 
			 submit.setVisibility(View.INVISIBLE);
			 username.setVisibility(View.INVISIBLE);
			 password.setText("");
			 _pass.setVisibility(View.INVISIBLE);
			 no.setVisibility(View.INVISIBLE);
			 no_tv.setVisibility(View.INVISIBLE);
			 login.setVisibility(View.VISIBLE);
		 }
		 

	}
	
	/*
	protected void onResume()
	{
		super.onResume();
		 
		 Button login = (Button)findViewById(R.id.button1);
		 Button submit = (Button)findViewById(R.id.button3);
		 
		 submit.setVisibility(View.INVISIBLE);
		 login.setVisibility(View.VISIBLE);
		 
	}
	*/
	
	public void bttnClicked(View view)
	{
		
	    password = (EditText)findViewById(R.id.editText2);
	    
	    String passView;
	    
	    passView = setting.getString(pass,pass);
	    snd_no   = setting.getString("number","not defined");
		
		if(password.getText().toString().equals(passView))
		{
			check = password.getText().toString();
			password.setText("");
			Intent i = new Intent(this,HomeActivity.class);
			startActivity(i);
		}
		else
		{
			Toast t;
			t = Toast.makeText(getApplicationContext(),"Wrong Password!!",Toast.LENGTH_LONG);
			t.show();
		}
	}
	
	public void bttn(View view)
	{

		edit = setting.edit();
	    username = (EditText)findViewById(R.id.editText1);
	    password = (EditText)findViewById(R.id.editText2);
	    EditText no = (EditText)findViewById(R.id.no_et);

	    
	    String u = username.getText().toString();
	    String p = password.getText().toString();
	    snd_no = no.getText().toString();
	    
	    if( !(u.equals("")) && !(p.equals("")) && !(snd_no.equals("")))
	    {
	    	int j=0;
	    	
	        for (char c : snd_no.toCharArray())
	        {
	            if (!Character.isDigit(c))
	            {
	    			Toast t;
	    			t = Toast.makeText(getApplicationContext(),"Mobile no not coorect",Toast.LENGTH_LONG);
	    			t.show();
	    			break;
	            }
	            j++;
	        }
	    	if(j<=9)
	    	{
    			Toast t;
    			t = Toast.makeText(getApplicationContext(),"Mobile no should be atleast 10 digits",Toast.LENGTH_LONG);
    			t.show();
	    		
	    	}
	    	else
	    	{
	    		ParseObject chat_data = new ParseObject("chat_data");
	    		chat_data.put("no", snd_no);
	    		chat_data.saveInBackground();
	    		
	    		check = password.getText().toString();
	        	
		    	edit.putString(name, u);
		    	edit.putString(pass, p);
		    	edit.putString(button,"f");
	    		edit.putString("number",snd_no);
	    		edit.commit();
	    		Intent i = new Intent(this,HomeActivity.class);
	    		startActivity(i);
	    	}
	    }
	    
	    else
	    {
			Toast t;
			t = Toast.makeText(getApplicationContext(),"Error..!!",Toast.LENGTH_LONG);
			t.show();
	    	
	    }
	    
	    
	}
	
}

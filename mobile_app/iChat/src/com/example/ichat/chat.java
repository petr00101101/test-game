package com.example.ichat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//@SuppressLint("NewApi")
public class chat extends Activity 
{
	TextView msgText;
    public String rec_no=null;
	String str = null;
	private static final String TAG = "chat";
	private static final String MyPREFERENCES = "MyPrefs";
	public String flag = "t";
	SharedPreferences setting;
	SharedPreferences.Editor edit;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		
		msgText = (TextView)findViewById(R.id.msgText);
   		//msg_tv = new TextView(this);
		
	    setting = getSharedPreferences(MyPREFERENCES,0);
		String check;    
		check = setting.getString(flag,flag);
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		
		if(check.equals("f"))
		{
			if(Intent.ACTION_SEND.equals(action) && "text/plain".equals(type))
			{
				EditText tv = (EditText)findViewById(R.id.ed);
				str = intent.getStringExtra(Intent.EXTRA_TEXT); 
				tv.setText(str);			
			
			}
		}
		
		
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		null,
		ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"='"+contacts.contactId[contacts.pos]+"'",
		null, null);
		
        int phoneNumberIndex = cursor
        .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
         String phoneNumber=null;
		    
         Log.d(TAG, String.valueOf(cursor.getCount()));

         if (cursor != null)
         {
        	 Log.v(TAG, "Cursor Not null");
             try 
             {
            	 if (cursor.moveToNext())
            	 {
                        Log.v(TAG, "Moved to first");
                        Log.v(TAG, "Cursor Moved to first and checking");
                        phoneNumber = cursor.getString(phoneNumberIndex);
            		    rec_no = phoneNumber;
                        System.out.println("HAGSd"+phoneNumber);
                 }
             } 
             finally 
             {
                    Log.v(TAG, "In finally");
                    cursor.close();
             }	   
         }

		
			Toast t;
	
	        new CountDownTimer(5000,1000)
	        {
	        	public void onFinish()
	        	{
	        		//TextView msgText = (TextView)findViewById(R.id.msgText);
	        		try
	        		{
	        			new DownloadWebpageTask_rec().execute("http://test.dmaticstechnologies.com/"+MainActivity.snd_no+"_latest");	
	        		}
	
	        		catch (Exception e)
	        		{
	        			msgText.setText(e.getMessage());
	        		}
	        	}

	        	@Override
	        	public void onTick(long arg0)
	        	{
				
	        	}
	          }.start();
	        
	   
	        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo != null && networkInfo.isConnected())
	        {
	        	t = Toast.makeText(getApplicationContext(),"Network Available",Toast.LENGTH_LONG);
	        	t.show();
	        } 
	        else
	        {
	        	t = Toast.makeText(getApplicationContext(),"No Network..!!",Toast.LENGTH_LONG);
	        	t.show();
	        }

	}

	public void notification()
	{
		NotificationCompat.Builder b = new NotificationCompat.Builder(this);
		
		b.setSmallIcon(R.drawable.ic_launcher);
		b.setContentTitle("Ichat : message for you");
		b.setContentText(msgText.getText().toString());
		
		NotificationManager nM;
		
		nM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nM.notify(1, b.build());
		try
		{
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		    r.play();
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
	
	public void lipi(View v)
	{
		edit = setting.edit();
		edit.putString(flag,"f");
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.canvas","com.canvas.Canvas1"));
		startActivity(intent);
		
	}
	
	public void send(View v)
	{
		EditText tv = (EditText)findViewById(R.id.ed);
		if(flag.equals("t"))
		{
			str = tv.getText().toString();	
		}
		tv.setText("");
		/*
		Toast t;
    	t = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG);
    	t.show();
    	*/
		
		new DownloadWebpageTask().execute("http://test.dmaticstechnologies.com/?action=send&to="+rec_no+"&from="+MainActivity.snd_no+"&msg="+str);
		//mLayout.addView(createNewTextView(msg_tv.getText().toString()));
	}
	/*
	
	public TextView createNewTextView(String text)
	{
		final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		msg_tv.setLayoutParams(lparams);
		return msg_tv;
	}
	*/
	
	public void showSoftKeyboard(View v)
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
	}
	
	private class DownloadWebpageTask extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String... urls)
		{
            try
            {
                return downloadUrl(urls[0]);
            }
            catch (IOException e)
            {
                return "msg can't be send";
            }
		}
        @Override
        protected void onPostExecute(String result)
        {
        	//msgText = (TextView)findViewById(R.id.msgText);
        	String []parts = result.split(" ");
        	String part1 = parts[0];
        	if(part1.equals("success"))
        	{
        	     msgText.setText("sent : "+str);
        	}
       }
	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	  
	    int len = 500;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        conn.connect();
	        is = conn.getInputStream();
           
	        String contentAsString = readIt(is, len);
	        
	        return contentAsString;
	      
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
	{
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}

	private class DownloadWebpageTask_rec extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String... urls)
		{
			try
			{
				return downloadUrl(urls[0]);
			}
			catch (IOException e)
			{
				return "no msg";
			}
		}
		@Override
		protected void onPostExecute(String result)
		{
			if(!result.equals("no msg"))
			{
				notification();
				msgText.setText(result);
			}
			
		}

	}
}


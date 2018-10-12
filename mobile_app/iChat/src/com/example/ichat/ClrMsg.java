package com.example.ichat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClrMsg extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clrmsg);
		
		Toast t;
		t = Toast.makeText(getApplicationContext(),"Do you really want to clear chat?",Toast.LENGTH_LONG);
		t.show();
	}
	public void yes(View view)
	{
		new DownloadWebpageTask().execute("http://test.dmaticstechnologies.com/?action=clear&user="+MainActivity.snd_no);	
	}
	public void no(View view)
	{
		Intent i = new Intent(this,contacts.class);
		startActivity(i);	
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
                return "msg can't be cleared";
            }
		}
        @Override
        protected void onPostExecute(String result)
        {
        
 
        	  Toast t;
        	  t = Toast.makeText(getApplicationContext(),"Chat cleared",Toast.LENGTH_LONG);
        	  t.show();
        	  
        }
	}
	
	private String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    int len = 500;
	        
	    try
	    {
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
	   
	    }
	    finally 
	    {
	        if (is != null)
	        {
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
}

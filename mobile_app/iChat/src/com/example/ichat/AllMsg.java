package com.example.ichat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class AllMsg extends Activity  
{
	TextView msg_tv;
	ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allmsg);
		
		msg_tv = (TextView)findViewById(R.id.textView1);
		
		new DownloadWebpageTask().execute("http://test.dmaticstechnologies.com/"+MainActivity.snd_no);
	}
	
	private class DownloadWebpageTask extends AsyncTask<String,Void,String>
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(AllMsg.this);
			// Set progressdialog title
			mProgressDialog.setTitle("All Messages");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}
		@Override
		protected String doInBackground(String... urls)
		{
            try
            {
                return downloadUrl(urls[0]);
            }
            catch (IOException e)
            {
                return "Can't receive msgs";
            }
		}
        @Override
        protected void onPostExecute(String result)
        {
			mProgressDialog.dismiss();
        	if(!result.equals("Can't receive msgs"))
        	{
        	  msg_tv.setText(result);
        	}
       }
        
    	private String downloadUrl(String myurl) throws IOException
    	{
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
}

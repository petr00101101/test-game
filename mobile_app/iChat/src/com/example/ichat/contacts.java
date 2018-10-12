package com.example.ichat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.provider.ContactsContract;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class contacts extends Activity {
	
	public static String []output;
	public static String []contactId;
	public static int pos;
	public ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		fetchContacts();
	}
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.item1 : 
			    Intent i1 = new Intent(this,AllMsg.class);
				startActivity(i1);	
			return true;
			
			case R.id.item2 : 
			    Intent i2 = new Intent(this,ClrMsg.class);
				startActivity(i2);	
			return true;
			
	        default:
	        return super.onOptionsItemSelected(item);
		}
	}
	public void fetchContacts() 
	{
		
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		//String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
		
		
		//Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		//String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		//String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        
		
		ContentResolver contentResolver = getContentResolver();
		
		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,"UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
		
		if(cursor.getCount()>0)
		{
			output = new String[cursor.getCount()];
			Integer imageId[] = new Integer[cursor.getCount()];
			contactId = new String[cursor.getCount()];
			//phoneNumber = new String[cursor.getCount()];
			int i = 0;
			while(cursor.moveToNext())
			{
				String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
				String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
				
				//int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
				
				//if(hasPhoneNumber>0)
				//{
					output[i] = name;
					imageId[i] = R.drawable.android2;
					contactId[i] = contact_id;
					
					
					//Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID+"=?", new String[]{contact_id},null);
	
					//phoneNumber[i] = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
					
					//phoneCursor.close();
				//}
				i++;
			 }
			
			list = (ListView)findViewById(R.id.contactInfo);
			
			CustomList adapter = new CustomList(contacts.this,imageId,output);
			list.setAdapter(adapter);

			list.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
			  @Override
			  public void onItemClick(AdapterView<?> parent,View view,int position,long id)
			  {
				pos = position;
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.example.ichat","com.example.ichat.chat"));
				startActivity(intent);
				  
			  }
			});
		 }		
	}
}

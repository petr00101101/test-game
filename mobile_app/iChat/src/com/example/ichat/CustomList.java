package com.example.ichat;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>
{
	Activity context;
	Integer[] imageId;
	String[] output;

	public CustomList(Activity context, Integer[] imageId, String[] output) 
	{
		super(context, R.layout.list_single, output);
		this.context = context;
		this.imageId = imageId;
		this.output = output;

	}
	@Override
	public View getView(int position,View view,ViewGroup parent)
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
		txtTitle.setText(output[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
	

}

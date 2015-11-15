package com.alex.hotcourts;

import java.util.ArrayList;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapterGroup extends BaseAdapter
{
	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;
	private Activity activity;
	
	private float mRate;
	
	public void setRate(float rate) {
		mRate = rate;
	}

	public GridviewAdapterGroup(Activity activity,ArrayList<String> listCountry, ArrayList<Integer> listFlag) {
		super();
		this.listCountry = listCountry;
		this.listFlag = listFlag;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCountry.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return listCountry.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder
	{
		public ImageView imgViewFlag;
		public TextView txtViewTitle;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		LayoutInflater inflator = activity.getLayoutInflater();

		if(convertView==null)
		{
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.groups_row, null);
			
			FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int) (146 * mRate), (int)(143 * mRate), Gravity.CENTER);
		
			((FrameLayout)convertView.findViewById(R.id.FrameLayoutItemContainer)).setLayoutParams(param);
			
//			param = new FrameLayout.LayoutParams((int) (108 * mRate), (int)(136 * mRate), Gravity.CENTER);

			view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
//			view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);
			
//			view.imgViewFlag.setLayoutParams(param);

			convertView.setTag(view);
		}
		else
		{
			view = (ViewHolder) convertView.getTag();
		}

		view.txtViewTitle.setText(listCountry.get(position));
//		view.imgViewFlag.setImageResource(listFlag.get(position));

		return convertView;
	}
}

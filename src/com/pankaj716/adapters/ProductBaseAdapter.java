package com.pankaj716.adapters;

import java.util.ArrayList;

import com.pankaj716.apphub.R;
import com.pankaj716.entities.Product;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductBaseAdapter extends BaseAdapter{

	Context context;
	ArrayList<Product> productArrayList;
	LayoutInflater inflater;
	
	public ProductBaseAdapter(Context context, ArrayList<Product> productArrayList) {
		this.context = context;
		this.productArrayList = productArrayList;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		if(productArrayList != null){
			return productArrayList.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return productArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.product_item_layout,
					parent, false);
			holder = new ViewHolder();
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.productItem_textview_name);
			holder.inappTextView = (TextView) convertView
					.findViewById(R.id.productItem_textview_inapp);
//			holder.imageVie
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		try{
		holder.nameTextView.setText(productArrayList.get(position).getName());
		
		Resources res = context.getResources();
		String inapp = String.format(res.getString(R.string.InApp), productArrayList.get(position).getInapp_purchase());
		
		holder.inappTextView.setText(inapp);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return convertView;
	}
	
	public static class ViewHolder {
		TextView nameTextView;
		TextView inappTextView;
		ImageView imageView;		
	}

}

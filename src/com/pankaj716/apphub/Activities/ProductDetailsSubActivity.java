package com.pankaj716.apphub.Activities;

import com.pankaj716.apphub.R;
import com.pankaj716.entities.Product;
import com.pankaj716.utils.AlertDialogMessage;
import com.pankaj716.utils.MyImageView;
import com.pankaj716.utils.PopoverView.PopoverViewDelegate;
import com.pankaj716.utils.TransparentProgressDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsSubActivity{

	Context context;
	Activity activity;
	LinearLayout inflateLayout;
	Product objProduct;

	MyImageView imageView;
	TextView nameTextView;
	TextView typeTextView;
	TextView inAppTextView;
	TextView lastUpdatedTextView;
	TextView ratingTextView;
	RatingBar ratingBar;
	PopoverViewDelegate popoverViewDelegate;
	
	TransparentProgressDialog transparentProgressDialog;

	public ProductDetailsSubActivity(Context context, Product objProduct, Activity activity) {
		this.context = context;
		this.activity = activity;
		this.objProduct = objProduct;
	}

	public void createLayout() {

		LinearLayout baseLayout = (LinearLayout) ((HomeActivity) context)
				.findViewById(R.id.homeLayout_baseContainer);
		inflateLayout = (LinearLayout) ((HomeActivity) context)
				.getLayoutInflater().inflate(R.layout.product_details_layout,
						null);
		baseLayout.removeAllViews();
		baseLayout.addView(inflateLayout);

		init();
		findViews();
		listeners();
		setValues();
	}
	
	private void init(){
		transparentProgressDialog = new TransparentProgressDialog(context,
				R.drawable.load_2arrow);
	}

	private void findViews() {
		imageView = (MyImageView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_imageView_image);
		nameTextView = (TextView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_textview_name);
		typeTextView = (TextView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_textView_type);
		inAppTextView = (TextView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_textView_inapp);
		ratingTextView = (TextView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_textView_rating);
		lastUpdatedTextView = (TextView) ((HomeActivity) context)
				.findViewById(R.id.productDetails_textView_lastUpdated);
		ratingBar = (RatingBar) ((HomeActivity) context)
				.findViewById(R.id.productDetails_ratingBar);
	}

	private void setValues() {
		ratingBar.setRating(Float.parseFloat(objProduct.getRating()));
		
		nameTextView.setText(objProduct.getName());
		typeTextView.setText(objProduct.getType());
		inAppTextView.setText(objProduct.getInapp_purchase());
		lastUpdatedTextView.setText(objProduct.getLast_updated());

		Callback objCallback = new Callback() {
			
			@Override
			public void onSuccess() {
				if(transparentProgressDialog.isShowing()){
					transparentProgressDialog.dismiss();
				}
			}
			
			@Override
			public void onError() {
				Toast.makeText(context, "Erro Occur while downloading Image" , Toast.LENGTH_SHORT)
				.show();				
			}
		};
		
		transparentProgressDialog.show();
		Toast.makeText(context, "Wait getting image...", Toast.LENGTH_SHORT).show();
		Picasso.with(context).load(objProduct.getImage())
		.into(imageView, objCallback);
	}

	private void listeners() {
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				ratingTextView.setText(""+rating);
			}
		});
		
		typeTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialogMessage(context).showMessage("Description", objProduct.getDescription());
			}
		});
	}

}


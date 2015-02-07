package com.pankaj716.apphub.Activities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.pankaj716.adapters.ProductBaseAdapter;
import com.pankaj716.apphub.R;
import com.pankaj716.entities.Product;
import com.pankaj716.utils.AlertDialogMessage;
import com.pankaj716.utils.ConnectionDetector;
import com.pankaj716.webservices.Constants;
import com.pankaj716.webservices.RestWebservices;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	ListView listView;
	TextView headerDeatailTextView;
	TextView productCountTextView;
	Button inAppButton;
	Button ratingButton;
	Button appStoreButton;
	Button shareButton;
	Button smsButton;
	Button backButton;
	ImageView closeImageView;
	LinearLayout bottomHomeLinearLayout;
	LinearLayout bottomDetailsLinearLayout;
	LinearLayout baseContainer;
	LinearLayout bottomMainLayout;

	ArrayList<Product> productArrayList = new ArrayList<Product>();
	public boolean isActivity = true; //for starting
	int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(!new ConnectionDetector(HomeActivity.this).isConnectedToInternet()){
			showMessage("Error", Constants.NO_ITERNET_CONNECTIVITY);
			return;
		}
		Toast.makeText(HomeActivity.this, "Fetching data...", Toast.LENGTH_SHORT).show();
		getProducts();
	}

	private void findView() {
		baseContainer = (LinearLayout)findViewById(R.id.homeLayout_baseContainer);
		LinearLayout inflateLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.listview_layout,null);
		baseContainer.removeAllViews();
		baseContainer.addView(inflateLayout);
		listView = (ListView) findViewById(R.id.homeLayout_listview);
		headerDeatailTextView = (TextView) findViewById(R.id.homeLayout_headerDetail);
		productCountTextView = (TextView) findViewById(R.id.homeLayout_textView_productCount);
		inAppButton = (Button) findViewById(R.id.homeLayout_button_inapp);
		ratingButton = (Button) findViewById(R.id.homeLayout_button_rating);
		backButton = (Button)findViewById(R.id.homeLayout_button_back);
		closeImageView = (ImageView)findViewById(R.id.homeLayout_imageView_close);
		bottomHomeLinearLayout = (LinearLayout)findViewById(R.id.homeLayout_bottomLayoutHome);
		bottomDetailsLinearLayout = (LinearLayout)findViewById(R.id.homeLayout_bottomLayoutDetails);
		bottomMainLayout = (LinearLayout)findViewById(R.id.homeLayout_bottomMainLayout);
		appStoreButton = (Button)findViewById(R.id.homeLayout_button_appStore);
		shareButton = (Button)findViewById(R.id.homeLayout_button_share);
		smsButton = (Button)findViewById(R.id.homeLayout_button_sms);
	}

	private void listeners() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(!new ConnectionDetector(HomeActivity.this).isConnectedToInternet()){
					new AlertDialogMessage(HomeActivity.this).showMessage("Error", Constants.NO_ITERNET_CONNECTIVITY);
					return;
				}
				HomeActivity.this.position = position;
				Product objProduct = (Product) parent
						.getItemAtPosition(position);
				bottomHomeLinearLayout.setVisibility(View.GONE);
				bottomDetailsLinearLayout.setVisibility(View.VISIBLE);
				headerDeatailTextView.setText("Product Details");
				isActivity = false;
				
				LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT, 2);
				layoutParams.weight = (float)1.4;
				baseContainer.setLayoutParams(layoutParams);
				new ProductDetailsSubActivity(HomeActivity.this, objProduct, HomeActivity.this)
						.createLayout();
			}
		});

		inAppButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Collections.sort(productArrayList, byInApp());
				setAdapter(productArrayList);
			}
		});

		ratingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Collections.sort(productArrayList, byRating());
				setAdapter(productArrayList);
			}
		});
		
		closeImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commonFunctionalityOnBack();
			}
		});
		
		appStoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url="";
				try {
					url = URLDecoder.decode(productArrayList.get(position).getUrl(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, 
					     Uri.parse(url));
					startActivity(intent);
			}
		});
		
		shareButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialogMessage(HomeActivity.this).showMessage("Message", "Functionality not implemented.");
			}
		});
		
		smsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialogMessage(HomeActivity.this).showMessage("Message", "Functionality not implemented.");
			}
		});
	}

	private void getProducts() {

		String extraParameters = Constants.PRODUCTS_PARAM1 + "json";
		new RestWebservices(HomeActivity.this) {
			public void onSuccess(String data,
					com.restservice.HttpResponse response) {
				try {
					JSONArray jsonArray = new JSONArray(data);
					setValues(jsonArray);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};

			public void onError(String message,
					com.restservice.HttpResponse response) {
				Toast.makeText(HomeActivity.this, "Error occur while fetching data...", Toast.LENGTH_SHORT).show();
				finish();
			};

			public void onFailure(com.restservice.NetworkError error) {
				Toast.makeText(HomeActivity.this, "Error occur while fetching data...", Toast.LENGTH_SHORT).show();
				finish();
			};
		}.serviceCall(Constants.PRODUCTS, extraParameters);
	}

	private void setValues(JSONArray jsonArray) {

		for (int i = 0; i < jsonArray.length(); i++) {
			Product objProduct = new Product();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = (JSONObject) jsonArray.get(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			try {
				objProduct.setName(jsonObject.getString("name"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setType(jsonObject.getString("type"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setUrl(jsonObject.getString("url"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setImage(jsonObject.getString("image"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setRating(jsonObject.getString("rating"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct
						.setLast_updated(jsonObject.getString("last updated"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setInapp_purchase(jsonObject
						.getString("inapp-purchase"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				objProduct.setDescription(jsonObject.getString("description"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (!objProduct.getName().equalsIgnoreCase("")) {
					productArrayList.add(objProduct);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		setContentView(R.layout.home_layout);
		findView();
		listeners();
		setAdapter(productArrayList);

		Resources res = getResources();
		String productCount = String.format(
				res.getString(R.string.ProductCount), productArrayList.size());
		productCountTextView.setText(productCount);
	}

	private void setAdapter(ArrayList<Product> productArrayList) {
		ProductBaseAdapter adapter = new ProductBaseAdapter(HomeActivity.this,
				productArrayList);
		listView.setAdapter(adapter);
	}

	private Comparator<Product> byInApp() {

		return new Comparator<Product>() {

			@Override
			public int compare(Product lhs, Product rhs) {
				return rhs.getInapp_purchase().compareTo(
						lhs.getInapp_purchase());
			}

		};
	}

	private Comparator<Product> byRating() {

		return new Comparator<Product>() {

			@Override
			public int compare(Product lhs, Product rhs) {
				return rhs.getRating().compareTo(lhs.getRating());
			}

		};
	}
	
	@Override
	public void onBackPressed() {
		commonFunctionalityOnBack();
	}
	
	private void commonFunctionalityOnBack(){
		if(isActivity){
			finish();
		}else{					
			isActivity = true;
			findView();
			LinearLayout.LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT, 1);
			baseContainer.setLayoutParams(layoutParams);
			headerDeatailTextView.setText("Home");
			listeners();
			setAdapter(productArrayList);
			bottomDetailsLinearLayout.setVisibility(View.GONE);
			bottomHomeLinearLayout.setVisibility(View.VISIBLE);
		}
	}
	
	public void showMessage(String header, String message) {

		AlertDialog.Builder altDialog = new AlertDialog.Builder(HomeActivity.this);
		altDialog.setMessage(message);
		altDialog.setTitle(header);
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		altDialog.show();

	}
}

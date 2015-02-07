package com.pankaj716.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogMessage {

	Context context;

	public AlertDialogMessage(Context context) {
		this.context = context;
	}

	public void showMessage(String header, String message) {

		AlertDialog.Builder altDialog = new AlertDialog.Builder(context);
		altDialog.setMessage(message);
		altDialog.setTitle(header);
		altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		altDialog.show();

	}

}

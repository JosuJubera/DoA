package com.aro.defenseofatroth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainClass(), config);

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder exitDialog= new AlertDialog.Builder(this);
		exitDialog
				.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				})
				.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel(); // no value is returned to the caller
						//dialog.dismiss(); // the value for negativeButton is returned to the caller
					}
				});
		exitDialog.setMessage(R.string.exit_msg);
		exitDialog.show();
	}
}

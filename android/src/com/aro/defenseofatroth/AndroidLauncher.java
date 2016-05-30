package com.aro.defenseofatroth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		String chartBoostAppId = "5729fb26f6cd454fb4045d16";
		String chartBoostAppSignature = "cdec3ab3d2bfccd738a75728472ecfe3615b5c07";
		Chartboost.startWithAppId(this, chartBoostAppId, chartBoostAppSignature);
		Chartboost.onCreate(this);
		showChartBoostIntersititial();
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

	@Override
	public void showChartBoostIntersititial() {

		Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
		Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
	}

	@Override
	public void onStart() {
		super.onStart();
		Chartboost.onStart(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Chartboost.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Chartboost.onPause(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		Chartboost.onStop(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Chartboost.onDestroy(this);
	}
}

package com.genesys.androidsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.genesys.chatlib.LoginActivity;

public class HomeActivity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	public void startLogin(View view) {
		Intent libraryIntent = new Intent(this, LoginActivity.class);
		startActivity(libraryIntent);
	}
}

package com.hr.fighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 
	}
	
	public void host(View v){
		startActivity(new Intent(this, serverActivity.class));
	}
	
	public void client(View v){
		startActivity(new Intent(this, clientActivity.class));
	}
		
}
package com.hr.fighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Button bHostknop = (Button) findViewById(R.id.host);
		bHostknop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.hr.fighter.serverActivity"));
				
			}
			
	});
		
		
		Button bJoinknop = (Button) findViewById(R.id.join);
		bJoinknop.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.hr.fighter.JOIN"));
				
			}
			
	});
		
		
		Button bHighscoresknop = (Button) findViewById(R.id.highscores);
		bHighscoresknop.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.hr.fighter.HIGHSCORES"));
				
			}
			
	});	
		
		
		Button bHelpknop = (Button) findViewById(R.id.help);
		bHelpknop.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent("com.hr.fighter.HELP"));
				
			}
			
	});	

}}

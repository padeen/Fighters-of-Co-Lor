package com.proandroidgames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.proandroidgames.R;


public class SFMainMenu extends Activity {
    /** Called when the activity is first created. */
	final SFEngine engine = new SFEngine();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SFEngine.context = getApplicationContext();
        
        Button bHostknop = (Button) findViewById(R.id.host);
		bHostknop.setOnClickListener(new View.OnClickListener() {
			
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent server = new Intent(getApplicationContext(),serverActivity.class);
			SFMainMenu.this.startActivity(server);
			
		}
			
	});
		
		Button bJoinknop = (Button) findViewById(R.id.join);
		bJoinknop.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent client = new Intent(getApplicationContext(),clientActivity.class);
				SFMainMenu.this.startActivity(client);
				
			}
			
	});
		
		
		Button bHighscoresknop = (Button) findViewById(R.id.highscores);
		bHighscoresknop.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/** Start Game!!!! */
				Intent game = new Intent(getApplicationContext(),SFGame.class);
				SFMainMenu.this.startActivity(game);
				
			}
			
	});	
		
		
		Button bHelpknop = (Button) findViewById(R.id.help);
		bHelpknop.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				boolean clean = false;
				clean = engine.onExit(v);	
				if (clean)
				{
					int pid= android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
				
			}
			
	});	
       
        
        /** Fire up background music */
       SFEngine.musicThread = new Thread(){
        	public void run(){
        		Intent bgmusic = new Intent(getApplicationContext(), SFMusic.class);
        		startService(bgmusic);
  
        	}
        };
        SFEngine.musicThread.start();
        

        
    }
}

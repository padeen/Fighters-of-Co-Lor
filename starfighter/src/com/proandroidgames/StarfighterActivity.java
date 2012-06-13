package com.proandroidgames;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.proandroidgames.R;

public class StarfighterActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	SFEngine.display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        super.onCreate(savedInstanceState);
        /*display the splash screen image*/
        setContentView(R.layout.splashscreen);
        /*start up the splash screen and main MainMenu in a time delayed thread*/
        SFEngine.context = this;
        new Handler().postDelayed(new Thread() {
        		@Override
        		public void run() {
                   Intent MainMenu = new Intent(StarfighterActivity.this, SFMainMenu.class);
                   StarfighterActivity.this.startActivity(MainMenu); 	
                   StarfighterActivity.this.finish();
                   overridePendingTransition(R.layout.fadein,R.layout.fadeout);
        		}
        	}, SFEngine.GAME_THREAD_DELAY);
        
    }
}
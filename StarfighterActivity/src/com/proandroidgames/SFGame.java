package com.proandroidgames;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import com.proandroidgames.R;

public class SFGame extends Activity {
	final SFEngine gameEngine = new SFEngine();
	private SFGameView gameView;
	private float width = SFEngine.display.getWidth();
	private float height = SFEngine.display.getHeight();
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new SFGameView(this);
        setContentView(gameView);
    }
    @Override
    protected void onResume() {
       super.onResume();
       gameView.onResume();
    }

    @Override
    protected void onPause() {
       super.onPause();
       gameView.onPause();
    }

   	@Override
   	public boolean onTouchEvent(MotionEvent event) {
   		//
   		float x = event.getX();
        float y = event.getY();
        if ((x > (SFEngine.playerBankPosX-1)*width/9) && (x < (SFEngine.playerBankPosX+1)*width/9)){
        	switch (event.getAction()){
        	case MotionEvent.ACTION_DOWN:
        		break;
        	case MotionEvent.ACTION_MOVE:
        		SFEngine.playerBankPosX = x/(width/9);
        		SFEngine.playerBankPosY = ((y-height)*-1)/(height/9);
        		SFEngine.playerFlightAction = SFEngine.PLAYER_BANK_MOVE;
        		break;
        	case MotionEvent.ACTION_UP:
        		SFEngine.playerFlightAction = SFEngine.PLAYER_RELEASE;
        		break;
        	}        	
        }
		return false;
    }
	
}

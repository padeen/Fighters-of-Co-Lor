package com.project56;

import java.io.IOException;
import java.io.OptionalDataException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import com.project56.R;

public class SFGame extends Activity {
	final SFEngine gameEngine = new SFEngine();
	private SFGameView gameView;
	private float width = SFEngine.display.getWidth();
	private float height = SFEngine.display.getHeight();
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Listening().execute();
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

   		
   		float x = event.getX()/(width/9);
   		//display x coordinaat converteren naar gl coordinaat 
        float y = (event.getY()-height)*-1/(height/9);
        //display y coordinaat converteren naar gl coordinaat + inverse
        
        if(Connector.playerSide == Connector.SERVER){
        	if ((x > SFEngine.player1BankPosX-1) && (x < SFEngine.player1BankPosX+1) &&
            		(y > SFEngine.player1BankPosY-1) && (y < SFEngine.player1BankPosY+1)){
        		switch (event.getAction()){
            	case MotionEvent.ACTION_DOWN:
            		PlayerEvent AD = new PlayerEvent();
            		AD.player = Connector.SERVER;
            		AD.action = MotionEvent.ACTION_DOWN;
            		try {
            			Connector.OOS.writeObject(AD);
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		break;
            	case MotionEvent.ACTION_MOVE:
           			PlayerEvent AM = new PlayerEvent();
            		AM.player = Connector.SERVER;
           			AM.x =  event.getX();
           			AM.y = event.getY();
           			AM.action = MotionEvent.ACTION_MOVE;
           			AM.playerFlightAction = SFEngine.PLAYER_BANK_MOVE;
            		try {
            			Connector.OOS.writeObject(AM);
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		SFEngine.player1BankPosX = x;
            		SFEngine.player1BankPosY = y;
            		SFEngine.playerFlightAction = SFEngine.PLAYER_BANK_MOVE;
            		break;
            	case MotionEvent.ACTION_UP:
           			PlayerEvent AP = new PlayerEvent();
            		AP.player = Connector.SERVER;
           			AP.action = MotionEvent.ACTION_UP;
           			AP.playerFlightAction = SFEngine.PLAYER_RELEASE;
        			try {
						Connector.OOS.writeObject(AP);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		SFEngine.playerFlightAction = SFEngine.PLAYER_RELEASE;
            		break;
            	}        	        		
        	}   	
        }
        
        else if(Connector.playerSide == Connector.CLIENT){
        	if ((x > SFEngine.player2BankPosX-1) && (x < SFEngine.player2BankPosX+1) &&		
            		(y > SFEngine.player2BankPosY-1) && (y < SFEngine.player2BankPosY+1)){
            	switch (event.getAction()){
            	case MotionEvent.ACTION_DOWN:
            		PlayerEvent AD = new PlayerEvent();
            		AD.player = Connector.CLIENT;
            		AD.action = MotionEvent.ACTION_DOWN;
            		try {
            			Connector.OOS.writeObject(AD);
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		break;
            	case MotionEvent.ACTION_MOVE:
           			PlayerEvent AM = new PlayerEvent();
           			AM.player = Connector.CLIENT;
           			AM.x =  event.getX();
           			AM.y = event.getY();
           			AM.action = MotionEvent.ACTION_MOVE;
           			AM.playerFlightAction = SFEngine.PLAYER_BANK_MOVE;
            		try {
            			Connector.OOS.writeObject(AM);
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            		SFEngine.player2BankPosX = x;
            		SFEngine.player2BankPosY = y;
            		SFEngine.playerFlightAction = SFEngine.PLAYER_BANK_MOVE;
            		break;
            	case MotionEvent.ACTION_UP:
           			PlayerEvent AP = new PlayerEvent();
           			AP.player = Connector.CLIENT;
           			AP.action = MotionEvent.ACTION_UP;
           			AP.playerFlightAction = SFEngine.PLAYER_RELEASE;
        			try {
						Connector.OOS.writeObject(AP);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		SFEngine.playerFlightAction = SFEngine.PLAYER_RELEASE;
            		break;
            	}        	        		
        	}
        }
		return false;
    }
   	
   	private class Listening extends AsyncTask<Void, String, Void>{
		
		protected Void doInBackground(Void... params) {
			boolean connected = true;
			while(connected){
				try {
					PlayerEvent PE = (PlayerEvent) Connector.OIS.readObject();
					if(PE != null){
						if(PE.player == Connector.CLIENT)
						switch (PE.action){
		            	case MotionEvent.ACTION_DOWN:
		            		break;
		            	case MotionEvent.ACTION_MOVE:
							SFEngine.player2BankPosX = 	PE.x/(width/9);;
		            		SFEngine.player2BankPosY =	(PE.y-height)*-1/(height/9);
		            		SFEngine.playerFlightAction = PE.action;
		            		break;
		            	case MotionEvent.ACTION_UP:
		            		SFEngine.playerFlightAction = PE.playerFlightAction;
		            		break;
		            	}else if(PE.player == Connector.SERVER){
							switch (PE.action){
			            	case MotionEvent.ACTION_DOWN:
			            		break;
			            	case MotionEvent.ACTION_MOVE:
								SFEngine.player1BankPosX = 	PE.x/(width/9);;
			            		SFEngine.player1BankPosY =	(PE.y-height)*-1/(height/9);
			            		SFEngine.playerFlightAction = PE.action;
			            		break;
			            	case MotionEvent.ACTION_UP:
			            		SFEngine.playerFlightAction = PE.playerFlightAction;
			            		break;
			            	}	
		            	}
					}
				} catch (OptionalDataException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}
	
}

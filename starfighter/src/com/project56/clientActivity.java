package com.project56;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.project56.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class clientActivity extends Activity {
    /** Called when the activity is first created. */
	
	

	private Socket socket;
	private boolean wasConnected =false;
	private String name = "hans";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client); 
    }
    
    public String getIP(){
    	EditText editText = (EditText) findViewById(R.id.serverIP);
    	return editText.getText().toString();
    }

    public void connectToHost(View v){
    	try{
    		socket = new Socket("10.0.2.2", 5000);
    		wasConnected = true;
    		Connector.OOS = new ObjectOutputStream(socket.getOutputStream());
   		 	Connector.OIS = new ObjectInputStream(socket.getInputStream());
   		 	Connector.playerSide = Connector.CLIENT;
   		 	Intent game = new Intent(getApplicationContext(),SFGame.class);
			clientActivity.this.startActivity(game);
   		 	//new Listening().execute();
    	}catch(UnknownHostException e){
    		System.err.println("Kan geen verbinding maken met dit IP adres.");
    		System.exit(1);
    	}catch(IOException e){
    		System.err.println("Geen I/O");
    		System.exit(1);
    	}
    }
    
	public void finish() {
		super.finish();
		try{
			if(wasConnected){
				//Connector.OOS.writeObject(new PlayerEvent(false));
				Connector.OOS.close();
				Connector.OIS.close();
				socket.close();
			}
		}catch (IOException e) {
			System.err.println("Sluiting van input output of socket faalt.");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
}
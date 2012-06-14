package com.proandroidgames;

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

import android.app.Activity;
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
	
	
	private ObjectOutputStream OOS;
	private ObjectInputStream OIS;
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
    
    private class Listening extends AsyncTask<Void, String, Void>{
		
		protected Void doInBackground(Void... params) {
			boolean connected = true;
			while(connected){
				try {
					PlayerEvent PE = (PlayerEvent) OIS.readObject();
					if(PE != null){
						if(!PE.gameIsRunning){
							connected = false;
						}else{
							publishProgress("\n" + PE.getUsername() + "" + PE.getMessage());	
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
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			TextView test = (TextView) findViewById(R.id.test);
			test.append(values[0]);
		}

		protected void onPostExecute(Void result) {
			finish();
	    }
    }
    
    

    public void connectToHost(View v){
    	try{
    		socket = new Socket("10.0.2.2", 5000);
    		wasConnected = true;
    		OOS = new ObjectOutputStream(socket.getOutputStream());
   		 	OIS = new ObjectInputStream(socket.getInputStream());
   		 	new Listening().execute();
    	}catch(UnknownHostException e){
    		System.err.println("Kan geen verbinding maken met dit IP adres.");
    		System.exit(1);
    	}catch(IOException e){
    		System.err.println("Geen I/O");
    		System.exit(1);
    	}
    	
    	setContentView(R.layout.connectie);
    }
    
    
    
    
    public void submit(View v){
    	EditText input = (EditText) findViewById(R.id.chat);
    	TextView local = (TextView) findViewById(R.id.test);
    	String message = input.getText().toString();
    	local.append("\n"+message);
    	
    	try {
			OOS.writeObject(new PlayerEvent(name, message));
		} catch (IOException e) { e.printStackTrace();}
    	input.setText(null);
    	

    }
    
    
	public void finish() {
		super.finish();
		try{
			if(wasConnected){
				OOS.writeObject(new PlayerEvent(false));
				OOS.close();
				OIS.close();
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
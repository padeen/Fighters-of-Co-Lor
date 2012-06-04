package com.proandroidgames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import com.proandroidgames.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class clientActivity extends Activity {
    /** Called when the activity is first created. */
	
	private PrintWriter out;
	private Socket socket;
	private BufferedReader in;
	private boolean wasConnected =false;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        TextView textView = (TextView) findViewById(R.id.ip);
        //textView.setText(getLocalIpAddress());   
    }
    
    public String getIP(){
    	EditText editText = (EditText) findViewById(R.id.serverIP);
    	return editText.getText().toString();
    }    

    public void connectToHost(View v){
    	try{
    		socket = new Socket("10.0.2.2", 5000);
    		out = new PrintWriter(socket.getOutputStream(), true);
    		in = new BufferedReader(new InputStreamReader(
    									socket.getInputStream()));
    		wasConnected = true;
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
    	local.setText(message);
    	out.println(message);
    	input.setText(null);
    }
    
    
	public void finish() {
		super.finish();
		try{
			if(wasConnected){
				out.println("!BYE.@");	
				out.close();
				in.close();
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
package com.proandroidgames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import com.proandroidgames.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class serverActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ObjectOutputStream OOS;
	private ObjectInputStream OIS;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Scanner scanner;
	private boolean wasConnected = false;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        TextView textView = (TextView) findViewById(R.id.ip);
        textView.setText(getLocalIpAddress());
    }

	@Override
	protected void onStart() {
		super.onStart();
		prepareServer();
		new Connect().execute();
	}

	public void prepareServer(){
    	try{
    		serverSocket = new ServerSocket();
    		serverSocket.bind(new InetSocketAddress("10.0.2.15", 6000));
    	}catch(IOException e){
    		System.err.println("Kan geen verbinding maken met poort 6000");
    		System.exit(1);
    	}
    }
	
	private class Connect extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			try{
	    		clientSocket = serverSocket.accept();
	    	}catch(IOException e){
	    		System.err.println("Acceptatie mislukt");
	    		System.exit(1);
	    	}
			
			
			return null;
		}
		
	     protected void onPostExecute(Void result) {
	    	 try{
	    		 
	    		 out = new PrintWriter(clientSocket.getOutputStream(),true);
	    		 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    		 
	    		 OOS = new ObjectOutputStream(clientSocket.getOutputStream());
	    		 OIS = new ObjectInputStream(clientSocket.getInputStream());
	    		 
	    		 
	    		 scanner = new Scanner(clientSocket.getInputStream());
	    		 
	    		 new Listening().execute();
	    		 wasConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
	         setContentView(R.layout.connectie);     
	    }
	}
	
	private class Listening extends AsyncTask<Void, String, Void>{
		
		protected Void doInBackground(Void... params) {
			boolean connected = true;
			while(connected){
				try {
					String inputline = in.readLine();
					if(inputline != null){
						if(inputline.equals("!BYE.@")){
							connected = false;
						}else{
							publishProgress("\nClient: "+inputline+"\n");
						}
					}
				} catch (IOException e) {}
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

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                       return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "Geen IP-Adres gevonden.";
    }
    
    
    public void finish() {
		super.finish();
		
		try{
			if(wasConnected){
				out.println("!BYE.@");	
				out.close();
				in.close();
		    	scanner.close();
		    	clientSocket.close();
		    	serverSocket.close();
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
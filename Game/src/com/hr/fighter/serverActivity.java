package com.hr.fighter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class serverActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private Scanner scanner;
	private Listening listening;  
	
	
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
	
	private class Connect extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			awaitingConnection();
			return null;
		}
		
	     protected void onPostExecute(Void result) {
	    	 try {
		    	out = new PrintWriter(clientSocket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(
						  					clientSocket.getInputStream()));
				scanner = new Scanner(clientSocket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
	         setContentView(R.layout.connectie);     
	    }
	}
	
	
	
	private class Listening extends AsyncTask<Void, String, Void>{
		
		protected Void doInBackground(Void... params) {
			while(true){
				try {
					String inputline; 
					if((inputline = in.readLine()) != null){	
						publishProgress("\nClient: "+inputline+"\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			TextView test = (TextView) findViewById(R.id.test);
			test.append(values[0]);
		}

		protected void onPostExecute(Void result) {  
	    }
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
	
	public void awaitingConnection(){
    	try{
    		clientSocket = serverSocket.accept();
    		listening = new Listening();
    		listening.execute();
    	}catch(IOException e){
    		System.err.println("Acceptatie mislukt");
    		System.exit(1);
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
    /**
    public void finish() {
		super.finish();
		
		try{
			listening.
    		out.close();
	    	scanner.close();
			in.close();
	    	clientSocket.close();
	    	serverSocket.close();
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
	}*/
}
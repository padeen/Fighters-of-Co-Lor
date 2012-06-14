package com.proandroidgames;

import java.io.Serializable;

public class PlayerEvent implements Serializable {
	
	private String message;
	private String username;
	public boolean gameIsRunning;
	
	
	public PlayerEvent(String name, String message){
		username = name;
		this.message = message;
		gameIsRunning = true;
	}
	
	public PlayerEvent(Boolean gameIsRunning){
		this.gameIsRunning = gameIsRunning;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}

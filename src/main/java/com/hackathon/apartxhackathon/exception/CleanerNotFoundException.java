package com.hackathon.apartxhackathon.exception;

public class CleanerNotFoundException extends Exception{
	@Override
	public String getMessage(){
		return "Уборщик не найден";
	}
}

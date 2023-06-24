package com.hackathon.apartxhackathon.exception;

public class CityNotFoundException extends Exception{
	@Override
	public String getMessage(){
		return "Наш сервис пока не представлен в данном городе";
	}
}

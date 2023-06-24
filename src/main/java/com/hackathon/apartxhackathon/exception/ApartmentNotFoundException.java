package com.hackathon.apartxhackathon.exception;

public class ApartmentNotFoundException extends Exception{
	@Override
	public String getMessage(){
		return "Данной квартиры/дома не существует";
	}
}

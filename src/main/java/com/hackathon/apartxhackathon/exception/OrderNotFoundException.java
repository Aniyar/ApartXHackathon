package com.hackathon.apartxhackathon.exception;

public class OrderNotFoundException extends Exception{
	@Override
	public String getMessage(){
		return "Заказ не найден";
	}
}


package com.hackathon.apartxhackathon.exception;

public class LandLordNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Данного владельца квартиры не найдено";
    }
}

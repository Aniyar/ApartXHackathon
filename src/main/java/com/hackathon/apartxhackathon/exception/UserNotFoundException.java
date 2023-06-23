package com.hackathon.apartxhackathon.exception;

public class UserNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Пользователя с данным адресом электронной почты н существует";
    }
}

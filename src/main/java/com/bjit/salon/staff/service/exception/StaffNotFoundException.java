package com.bjit.salon.staff.service.exception;

public class StaffNotFoundException extends RuntimeException {
    public StaffNotFoundException(String message){
        super(message);
    }
}

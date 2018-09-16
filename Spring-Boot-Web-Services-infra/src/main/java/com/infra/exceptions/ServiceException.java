package com.infra.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private int exErrorCode;

    public ServiceException(){super();}

    public ServiceException(String message){super(message);}

    public ServiceException(int errorCode, String message){super(message); exErrorCode = errorCode;}

    public ServiceException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        exErrorCode = errorCode;
    }

    public int getExErrorCode(){ return exErrorCode;}

    public void setExErrorCode(int errorCode){ exErrorCode = errorCode;}

    public String toString(){
        return String.format("error code: %d. ex: %s", exErrorCode, super.toString());
    }
}

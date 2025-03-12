package com.payment.simulator.common.exception;


public class SimulateException extends RuntimeException{
    private String internalMessage = "";
    private ErrorCode errorCode = null;

    public SimulateException(ErrorCode errorCode, String internalMessage){
        super(internalMessage);
        this.internalMessage = internalMessage;
        this.errorCode = errorCode;
    }

    public SimulateException(ErrorCode errorCode){
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
    public String getCode(){
        return this.errorCode.getCode();
    }



    public String getInternalMessage() {
        return internalMessage;
    }

    public void setInternalMessage(String internalMessage) {
        this.internalMessage = internalMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
package me.n1ar4.clazz.obfuscator.api;

public class Result {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    private String message;
    private byte[] data;

    public static Result Success(byte[] input) {
        Result result = new Result();
        result.message = SUCCESS;
        result.data = input;
        return result;
    }

    public static Result Error(String message) {
        Result result = new Result();
        result.message = ERROR;
        result.data = message.getBytes();
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

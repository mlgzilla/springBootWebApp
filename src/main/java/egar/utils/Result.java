package egar.utils;

public class Result<T> {
    private final T object;
    private final String errorMessage;

    private Result(T object, String errorMessage) {
        this.object = object;
        this.errorMessage = errorMessage;
    }

    public static <T> Result<T> ok(T object){
        return new Result<>(object, null);
    }

    public static<T> Result<T> error(String errorMessage){
        return new Result<>(null, errorMessage);
    }

    public boolean isOk(){
        return object != null;
    }

    public boolean isError(){
        return object == null;
    }

    public T getObject() {
        return object;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

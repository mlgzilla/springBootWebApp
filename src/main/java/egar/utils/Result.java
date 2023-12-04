package egar.utils;

public class Result<T> {
    private final T object;
    private final String errorMessage;

    public Result(T object, String errorMessage) {
        this.object = object;
        this.errorMessage = errorMessage;
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

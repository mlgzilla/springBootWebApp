package task_tracker.utils;

public class Result<T> {
    private final T object;
    private final String message;
    private final String code;

    private Result(T object, String message, String code) {
        this.object = object;
        this.message = message;
        this.code = code;
    }

    public static <T> Result<T> ok(T object) {
        return new Result<>(object, null, null);
    }

    public static <T> Result<T> error(String message, String code) {
        return new Result<>(null, message, code);
    }

    public boolean isOk() {
        return object != null;
    }

    public boolean isError() {
        return object == null;
    }

    public T getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

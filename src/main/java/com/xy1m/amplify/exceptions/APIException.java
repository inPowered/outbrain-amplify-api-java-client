package com.xy1m.amplify.exceptions;

import com.xy1m.amplify.model.APIError;

public abstract class APIException extends RuntimeException {

    private APIError error = APIError.EMPTY;

    public APIException(String message, Object... params) {
        super(String.format(message, params));
    }

    public APIException(String message, Throwable cause, Object... params) {
        super(String.format(message, params), cause);
    }

    public APIException(APIError error, String message, Object... params) {
        super(String.format(message + parseError(error), params));
        this.error = error;
    }

    public APIException(APIError error, String message, Throwable t, Object... params) {
        super(String.format(message + parseError(error), params), t);
        this.error = error;
    }

    private static String parseError(APIError error) {
        if (error == null || APIError.EMPTY.equals(error)) {
            return "";
        }

        return String.format(". Response error: %s", error);
    }

    public APIError getError() {
        return error;
    }
}

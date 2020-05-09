package com.xy1m.amplify.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy1m.amplify.exceptions.APIClientException;
import com.xy1m.amplify.exceptions.APIServerException;
import com.xy1m.amplify.exceptions.APIUnauthorizedException;
import com.xy1m.amplify.model.APIError;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class SynchronousCallAdapterFactory extends CallAdapter.Factory {

    private static final Logger logger = LogManager.getLogger(SynchronousCallAdapterFactory.class);
    private static final int UNAUTHORIZED_HTTP_STATUS_CODE = 401;
    private static final int BAD_REQUEST_HTTP_STATUS_CODE = 400;
    private static final int INTERNAL_SERVER_ERROR_HTTP_STATUS_CODE = 500;

    private final ObjectMapper objectMapper;

    public static SynchronousCallAdapterFactory create(ObjectMapper objectMapper) {
        return new SynchronousCallAdapterFactory(objectMapper);
    }

    private SynchronousCallAdapterFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CallAdapter<Object, Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (returnType instanceof Call ||
                returnType.toString().contains("retrofit2.Call")) {

            return null;
        }

        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return returnType;
            }

            @Override
            public Object adapt(Call<Object> call) {
                Object obj;
                try {
                    Response<Object> response = call.execute();
                    if (response.isSuccessful()) {
                        obj = response.body();
                    }
                    else {
                        int responseCode = response.code();
                        if (responseCode == UNAUTHORIZED_HTTP_STATUS_CODE) {
                            throw new APIUnauthorizedException();

                        }
                        else if (responseCode >= BAD_REQUEST_HTTP_STATUS_CODE && responseCode < INTERNAL_SERVER_ERROR_HTTP_STATUS_CODE) {
                            throw new APIClientException(responseCode, parseError(response));
                        }

                        throw new APIServerException(responseCode);
                    }
                }
                catch (IOException e) {
                    logger.error(e);
                    throw new APIServerException(e);
                }

                return obj;
            }
        };
    }

    private APIError parseError(Response errorResponse) {
        ResponseBody errorBody = errorResponse.errorBody();
        if (errorBody != null) {
            try {
                return objectMapper.readValue(errorBody.bytes(), APIError.class);
            }
            catch (Throwable e) {
                logger.error("Failed to parse API error response object [{}]", errorResponse.message());
                return new APIError(errorResponse.message());
            }
        }
        else {
            APIError error = new APIError();
            error.setMessage(errorResponse.message());
            return error;
        }
    }
}

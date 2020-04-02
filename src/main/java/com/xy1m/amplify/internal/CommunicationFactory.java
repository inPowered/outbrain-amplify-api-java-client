package com.xy1m.amplify.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy1m.amplify.internal.config.CommunicationConfig;
import com.xy1m.amplify.internal.config.SerializationConfig;
import com.xy1m.amplify.internal.interceptors.CommunicationInterceptor;
import com.xy1m.amplify.internal.interceptors.UserAgentInterceptor;
import com.xy1m.amplify.internal.serialization.SerializationMapperCreator;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public final class CommunicationFactory {

    private final ObjectMapper objectMapper;
    private final Retrofit retrofit;
    private final Retrofit authRetrofit;

    public CommunicationFactory(CommunicationConfig communicationConfig, SerializationConfig serializationConfig) {
        this.objectMapper = SerializationMapperCreator.createObjectMapper(serializationConfig);
        Retrofit.Builder retrofitBuilder = createRetrofitBuilder(communicationConfig);

        this.authRetrofit = retrofitBuilder.baseUrl(communicationConfig.getAuthenticationBaseUrl()).build();
        this.retrofit = retrofitBuilder.baseUrl(communicationConfig.getBaseUrl()).build();
    }

    private HttpLoggingInterceptor createLoggingInterceptor(CommunicationConfig config) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new CommunicationInterceptor());
        if (config.isDebug()) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.redactHeader("Authorization");
            loggingInterceptor.redactHeader("Cookie");
        }
        else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        return loggingInterceptor;
    }

    private Retrofit.Builder createRetrofitBuilder(CommunicationConfig config) {
        return new Retrofit.Builder()
                //TODO addContent ability to start retrofit2 in mock mode {option retrofit-mock}
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(SynchronousCallAdapterFactory.create(objectMapper))
                .client(createOkHttpClient(config));
    }

    private OkHttpClient createOkHttpClient(CommunicationConfig config) {
        return new OkHttpClient.Builder()
                //TODO use global connection pool to prevent OkHttpClient default behaviour from creating too many file descriptors when performing async calls
                .addInterceptor(createLoggingInterceptor(config))
                .addInterceptor(new UserAgentInterceptor(config.getUserAgent()))
                .readTimeout(config.getReadTimeoutMillis(), TimeUnit.MILLISECONDS)
                .writeTimeout(config.getWriteTimeoutMillis(), TimeUnit.MILLISECONDS)
                .connectTimeout(config.getConnectionTimeoutMillis(), TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(config.getMaxIdleConnections(),
                        config.getKeepAliveDurationMillis(), TimeUnit.MILLISECONDS))
                .build();
    }

    public <E> E createRetrofitAuthEndpoint(Class<E> clazz) {
        return authRetrofit.create(clazz);
    }

    public <E> E createRetrofitEndpoint(Class<E> clazz) {
        return retrofit.create(clazz);
    }
}

package com.xy1m.amplify;

import com.xy1m.amplify.internal.AuthenticationEndpoint;
import com.xy1m.amplify.internal.CommunicationFactory;
import com.xy1m.amplify.internal.GeoEndpoint;
import com.xy1m.amplify.internal.InterestEndpoint;
import com.xy1m.amplify.internal.MarketerEndpoint;
import com.xy1m.amplify.internal.PerformanceReportEndpoint;
import com.xy1m.amplify.internal.config.CommunicationConfig;
import com.xy1m.amplify.internal.config.SerializationConfig;
import com.xy1m.amplify.internal.factories.AmplifyEndpointsFactory;
import com.xy1m.amplify.internal.factories.AmplifyEndpointsRetrofitFactory;
import com.xy1m.amplify.service.AuthenticationService;
import com.xy1m.amplify.service.AuthenticationServiceImpl;
import com.xy1m.amplify.service.CampaignService;
import com.xy1m.amplify.service.CampaignServiceImpl;
import com.xy1m.amplify.service.GeoService;
import com.xy1m.amplify.service.GeoServiceImpl;
import com.xy1m.amplify.service.InterestService;
import com.xy1m.amplify.service.InterestServiceImpl;
import com.xy1m.amplify.internal.CampaignEndpoint;
import com.xy1m.amplify.internal.PromotedLinkEndpoint;
import com.xy1m.amplify.service.MarketerService;
import com.xy1m.amplify.service.MarketerServiceImpl;
import com.xy1m.amplify.service.PerformanceReportService;
import com.xy1m.amplify.service.PerformanceReportServiceImpl;
import com.xy1m.amplify.service.PromotedLinkService;
import com.xy1m.amplify.service.PromotedLinkServiceImpl;

public class Amplify {
    private static Amplify instance = Amplify.builder().build();
    private final AuthenticationService authenticationService;
    private final GeoService geoService;
    private final InterestService interestService;
    private final CampaignService campaignService;
    private final PromotedLinkService promotedLinkService;
    private final MarketerService marketerService;
    private final PerformanceReportService performanceReportService;

    private Amplify(AuthenticationService authenticationService,
                    GeoService geoService,
                    InterestService interestService,
                    CampaignService campaignService,
                    PromotedLinkService promotedLinkService,
                    MarketerService marketerService,
                    PerformanceReportService performanceReportService) {
        this.authenticationService = authenticationService;
        this.geoService = geoService;
        this.interestService = interestService;
        this.campaignService = campaignService;
        this.promotedLinkService = promotedLinkService;
        this.marketerService = marketerService;
        this.performanceReportService = performanceReportService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public GeoService getGeoService() {
        return geoService;
    }

    public InterestService getInterestService() {
        return interestService;
    }

    public CampaignService getCampaignService() {
        return campaignService;
    }

    public PromotedLinkService getPromotedLinkService() {
        return promotedLinkService;
    }

    public MarketerService getMarketerService() {
        return marketerService;
    }

    public PerformanceReportService getPerformanceReportService() {
        return performanceReportService;
    }

    public static Amplify getInstance() {
        return instance;
    }

    public static AmplifyBuilder builder() {
        return new AmplifyBuilder();
    }

    public static class AmplifyBuilder {
        private static final String DEFAULT_HOST = "https://api.outbrain.com/";
        private static final String DEFAULT_AUTH_HOST = "https://api.outbrain.com/";
        private static final String DEFAULT_USER_AGENT = "Amplify API Java Client";
        private static final String VERSION = "v1.0";
        private static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;
        private static final long DEFAULT_KEEP_ALIVE_DURATION_MILLIS = 300_000L;
        private static final SerializationConfig DEFAULT_SERIALIZATION_CONFIG = new SerializationConfig();

        private String baseUrl = DEFAULT_HOST;
        private String authBaseUrl = DEFAULT_AUTH_HOST;
        private String userAgent = DEFAULT_USER_AGENT;
        private long writeTimeoutMillis = 0L;
        private long connectionTimeoutMillis = 0L;
        private long readTimeoutMillis = 0L;
        private int maxIdleConnections = DEFAULT_MAX_IDLE_CONNECTIONS;
        private long keepAliveDurationMillis = DEFAULT_KEEP_ALIVE_DURATION_MILLIS;
        private boolean debug = false;
        private boolean organizeDynamicColumns = true;
        private SerializationConfig serializationConfig = DEFAULT_SERIALIZATION_CONFIG;

        public AmplifyBuilder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public AmplifyBuilder setAuthBaseUrl(String authBaseUrl) {
            this.authBaseUrl = authBaseUrl;
            return this;
        }

        public AmplifyBuilder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public AmplifyBuilder setConnectionTimeoutMillis(Long connectionTimeoutMillis) {
            this.connectionTimeoutMillis = connectionTimeoutMillis;
            return this;
        }

        public AmplifyBuilder setReadTimeoutMillis(Long readTimeoutMillis) {
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }

        public AmplifyBuilder setWriteTimeoutMillis(Long writeTimeoutMillis) {
            this.writeTimeoutMillis = writeTimeoutMillis;
            return this;
        }

        public AmplifyBuilder setMaxIdleConnections(Integer maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
            return this;
        }

        public AmplifyBuilder setKeepAliveDurationMillis(Long keepAliveDurationMillis) {
            this.keepAliveDurationMillis = keepAliveDurationMillis;
            return this;
        }

        public AmplifyBuilder setDebug(Boolean debug) {
            this.debug = debug;
            return this;
        }

        public AmplifyBuilder setOrganizeDynamicColumns(Boolean organizeDynamicColumns) {
            this.organizeDynamicColumns = organizeDynamicColumns;
            return this;
        }

        public AmplifyBuilder setSerializationConfig(SerializationConfig serializationConfig) {
            this.serializationConfig = serializationConfig;
            return this;
        }

        public Amplify build() {
            String finalUserAgent = String.format("RevContent/%s (%s)", VERSION, userAgent);
            CommunicationConfig config = new CommunicationConfig(baseUrl, authBaseUrl, connectionTimeoutMillis,
                    readTimeoutMillis, writeTimeoutMillis, maxIdleConnections, keepAliveDurationMillis,
                    finalUserAgent, debug);
            CommunicationFactory communicator = new CommunicationFactory(config, serializationConfig);
            AmplifyEndpointsFactory endpointsFactory = new AmplifyEndpointsRetrofitFactory(communicator);
            return new Amplify(
                    new AuthenticationServiceImpl(endpointsFactory.createAuthEndpoint(AuthenticationEndpoint.class)) {},
                    new GeoServiceImpl(endpointsFactory.createAuthEndpoint(GeoEndpoint.class)) {},
                    new InterestServiceImpl(endpointsFactory.createAuthEndpoint(InterestEndpoint.class)) {},
                    new CampaignServiceImpl(endpointsFactory.createAuthEndpoint(CampaignEndpoint.class)) {},
                    new PromotedLinkServiceImpl(endpointsFactory.createAuthEndpoint(PromotedLinkEndpoint.class)) {},
                    new MarketerServiceImpl(endpointsFactory.createAuthEndpoint(MarketerEndpoint.class)) {},
                    new PerformanceReportServiceImpl(endpointsFactory.createAuthEndpoint(PerformanceReportEndpoint.class))
            );
        }
    }
}

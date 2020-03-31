package com.xy1m.internal.factories;

import com.xy1m.internal.CommunicationFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class AmplifyEndpointsRetrofitFactory implements AmplifyEndpointsFactory {

    private static final Logger logger = LogManager.getLogger(AmplifyEndpointsRetrofitFactory.class);

    private final CommunicationFactory communicationFactory;

    public AmplifyEndpointsRetrofitFactory(CommunicationFactory communicationFactory) {
        this.communicationFactory = communicationFactory;
    }

    @Override
    public <A> A createAuthEndpoint(Class<A> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        logger.debug("creating authentication endpoint using retrofit for class [{}]", clazz::toString);
        return communicationFactory.createRetrofitAuthEndpoint(clazz);
    }

    @Override
    public <E> E createEndpoint(Class<E> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        logger.debug("creating endpoint using retrofit for class [{}]", clazz::toString);
        return communicationFactory.createRetrofitEndpoint(clazz);
    }
}

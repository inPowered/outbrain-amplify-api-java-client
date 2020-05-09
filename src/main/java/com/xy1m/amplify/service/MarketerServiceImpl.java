package com.xy1m.amplify.service;

import com.xy1m.amplify.exceptions.APIException;
import com.xy1m.amplify.internal.MarketerEndpoint;
import com.xy1m.amplify.model.auth.Authentication;
import com.xy1m.amplify.model.budget.BudgetListResponse;
import com.xy1m.amplify.model.marketer.Marketer;
import com.xy1m.amplify.model.marketer.MarketerListResponse;

public class MarketerServiceImpl implements MarketerService {

    private final Boolean performValidations;
    private final MarketerEndpoint endpoint;

    public MarketerServiceImpl(Boolean performValidations, MarketerEndpoint endpoint) {
        this.performValidations = performValidations;
        this.endpoint = endpoint;
    }

    @Override
    public Marketer get(Authentication auth, String marketerId, String extraFields) throws APIException {
        return endpoint.getById(auth.getToken().getAccessToken(), marketerId);
    }

    @Override
    public MarketerListResponse getAll(Authentication auth, String extraFields) throws APIException {
        return endpoint.getAll(auth.getToken().getAccessToken());
    }

    @Override
    public BudgetListResponse getBudgets(Authentication auth, String marketerId, boolean detachedOnly, String extraFields) throws APIException {
        return endpoint.getBudgetsByMarketerId(auth.getToken().getAccessToken(), marketerId, detachedOnly);
    }

}

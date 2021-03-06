package com.xy1m.amplify.service;

import com.xy1m.amplify.exceptions.APIException;
import com.xy1m.amplify.model.auth.Authentication;
import com.xy1m.amplify.model.budget.Budget;
import com.xy1m.amplify.model.budget.BudgetListResponse;
import com.xy1m.amplify.model.marketer.Marketer;
import com.xy1m.amplify.model.marketer.MarketerListResponse;

public interface MarketerService {

    Budget createBudget(Authentication auth, String marketerId, Budget budget) throws APIException;

    Marketer get(Authentication auth, String marketerId) throws APIException;

    MarketerListResponse getAll(Authentication auth) throws APIException;

    BudgetListResponse getBudgets(Authentication auth, String marketerId, boolean detachedOnly) throws APIException;

}

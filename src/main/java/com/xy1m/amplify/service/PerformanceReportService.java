package com.xy1m.amplify.service;

import com.xy1m.amplify.exceptions.APIException;
import com.xy1m.amplify.model.auth.Authentication;
import com.xy1m.amplify.model.performance.reporting.periodic.PeriodicCampaign;
import com.xy1m.amplify.model.performance.reporting.publisher.PublisherCampaign;

import java.util.Date;

public interface PerformanceReportService {

    PeriodicCampaign getPeriodicByCampaign(Authentication auth, String id, Date from, Date to, int limit, int offSet, String breakdown,
                                           boolean includeArchivedCampaigns, boolean includeConversionDetails,
                                           boolean conversionsByClickDate) throws APIException;

    PublisherCampaign getPublishersByCampaign(Authentication auth, String id, Date from, Date to, int limit, int offSet,
                                              boolean includeArchivedCampaigns, boolean includeConversionDetails,
                                              boolean conversionsByClickDate) throws APIException;
}

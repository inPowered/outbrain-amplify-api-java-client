package com.xy1m.amplify.service;

import com.xy1m.amplify.exceptions.APIException;
import com.xy1m.amplify.model.auth.Authentication;
import com.xy1m.amplify.model.campaign.Campaign;
import com.xy1m.amplify.model.campaign.CampaignCreate;
import com.xy1m.amplify.model.campaign.CampaignExtraField;
import com.xy1m.amplify.model.campaign.MultipleCampaignsResponse;
import com.xy1m.amplify.model.campaign.SingleCampaignUpdateResponse;
import com.xy1m.amplify.model.reference.types.FetchType;
import com.xy1m.amplify.model.resource.GeoLocation;

import java.util.List;


public interface CampaignService {

    Campaign get(Authentication auth, String id, CampaignExtraField... extraFields) throws APIException;

    Campaign update(Authentication auth, Campaign campaign, CampaignExtraField... extraFields) throws APIException;

    Campaign create(Authentication auth, CampaignCreate campaign, CampaignExtraField... extraFields) throws APIException;

    List<SingleCampaignUpdateResponse> batchUpdate(Authentication auth, List<Campaign> campaigns);

    MultipleCampaignsResponse batchGet(Authentication auth, List<String> ids, String extraFields);

    MultipleCampaignsResponse getByBudgetId(Authentication auth, String budgetId);

    MultipleCampaignsResponse getByMarketerId(Authentication auth,
                                              String marketerId,
                                              Boolean includeArchived,
                                              FetchType fetch,
                                              String extraFields,
                                              Integer limit,
                                              Integer offset);

    List<GeoLocation> getGeoLocationsByCampaignId(Authentication auth, String campaignId);

}

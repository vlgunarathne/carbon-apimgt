/*
*  Copyright (c) 2005-2011, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.apimgt.api;

import org.wso2.carbon.apimgt.api.model.*;

import java.util.Map;
import java.util.Set;

/**
 * APIConsumer responsible for providing helper functionality
 */
public interface APIConsumer extends APIManager {

    /**
     * @param subscriberId id of the Subscriber
     * @return Subscriber
     * @throws APIManagementException if failed to get Subscriber
     */
    public Subscriber getSubscriber(String subscriberId) throws APIManagementException;

    /**
     * Returns a list of #{@link org.wso2.carbon.apimgt.api.model.API} bearing the selected tag
     *
     * @param tag name of the tag
     * @return set of API having the given tag name
     * @throws APIManagementException if failed to get set of API
     */
    public Set<API> getAPIsWithTag(String tag) throws APIManagementException;

    /**
     * Returns a paginated list of #{@link org.wso2.carbon.apimgt.api.model.API} bearing the selected tag
     *
     * @param tag name of the tag
     * @param start starting number
     * @param end ending number
     * @return set of API having the given tag name
     * @throws APIManagementException if failed to get set of API
     */
    public Map<String,Object> getPaginatedAPIsWithTag(String tag, int start, int end) throws APIManagementException;

    /**
     * Returns a list of all published APIs. If a given API has multiple APIs,
     * only the latest version will be included
     * in this list.
     *
     * @return set of API
     * @throws APIManagementException if failed to API set
     */
    public Set<API> getAllPublishedAPIs(String tenantDomain) throws APIManagementException;
 /**
     * Returns a paginated list of all published APIs. If a given API has multiple APIs,
     * only the latest version will be included
     * in this list.
     * @param tenantDomain tenant domain
     * @param start starting number
     * @param end ending number
     * @return set of API
     * @throws APIManagementException if failed to API set
     */
    public Map<String,Object> getAllPaginatedPublishedAPIs(String tenantDomain, int start, int end)
            throws APIManagementException;

    /**
     * Returns top rated APIs
     *
     * @param limit if -1, no limit. Return everything else, limit the return list to specified value.
     * @return Set of API
     * @throws APIManagementException if failed to get top rated APIs
     */
    public Set<API> getTopRatedAPIs(int limit) throws APIManagementException;

    /**
     * Get recently added APIs to the store
     *
     * @param limit if -1, no limit. Return everything else, limit the return list to specified value.
     * @return set of API
     * @throws APIManagementException if failed to get recently added APIs
     */
    public Set<API> getRecentlyAddedAPIs(int limit,String tenantDomain) throws APIManagementException;

    /**
     * Get all tags of published APIs
     *
     * @return a list of all Tags applied to all APIs published.
     * @throws APIManagementException if failed to get All the tags
     */
    public Set<Tag> getAllTags(String tenantDomain) throws APIManagementException;

    /**
     * Returns all tags with their descriptions.
     *
     * NOTE : The reason for having a separate method to get the tags with their attributes is,
     * because of the implementation of addition tag attributes.
     * Tag attributes are saved in a registry location with convention.
     * e.g.  governance/apimgt/applicationdata/tags/{tag_name}/description.txt.
     * In most of the use cases these attributes are not needed.
     * So not fetching the description if it is not needed is healthy for performance.
     *
     * @param tenantDomain Tenant domain.
     * @return The description of the tag.
     * @throws APIManagementException if there is a failure in getting the description.
     */
    public Set<Tag> getTagsWithAttributes(String tenantDomain)throws APIManagementException;

    /**
     * Rate a particular API. This will be called when subscribers rate an API
     *
     * @param apiId  The API identifier
     * @param rating The rating provided by the subscriber
     * @param user Username of the subscriber providing the rating
     * @throws APIManagementException If an error occurs while rating the API
     */
    public void rateAPI(APIIdentifier apiId, APIRating rating, String user) throws APIManagementException;
    /**
     * Remove an user rating of a particular API. This will be called when subscribers remove their rating on an API
     *
     * @param apiId  The API identifier
     * @param user Username of the subscriber providing the rating
     * @throws APIManagementException If an error occurs while rating the API
     */
    public void removeAPIRating(APIIdentifier apiId, String user) throws APIManagementException;

    /**
     * Returns a set of SubscribedAPI purchased by the given Subscriber
     *
     * @param subscriber Subscriber
     * @return Set<API>
     * @throws APIManagementException if failed to get API for subscriber
     */
    public Set<SubscribedAPI> getSubscribedAPIs(Subscriber subscriber) throws APIManagementException;

    /**
     * Returns a set of SubscribedAPIs filtered by the given application name.
     *
     * @param subscriber Subscriber
     * @return Set<API>
     * @throws APIManagementException if failed to get API for subscriber
     */
    public Set<SubscribedAPI> getSubscribedAPIs(Subscriber subscriber, String applicationName) throws APIManagementException;

    /**
     *
     * @param jsonString this string will contain oAuth app details
     * @param userName user name of logged in user.
     * @param clientId this is the consumer key of oAuthApplication
     * @param applicationName this is the APIM appication name.
     * @return
     * @throws APIManagementException
     */
    public Map<String,Object> saveSemiManualClient(String jsonString, String userName, String clientId,
                                                   String  applicationName) throws APIManagementException;

    /**
     * Returns true if a given user has subscribed to the API
     *
     * @param apiIdentifier APIIdentifier
     * @param userId        user id
     * @return true, if giving api identifier is already subscribed
     * @throws APIManagementException if failed to check the subscribed state
     */
    public boolean isSubscribed(APIIdentifier apiIdentifier, String userId) throws APIManagementException;

    /**
     * Add new Subscriber
     *
     * @param identifier    APIIdentifier
     * @param userId        id of the user
     * @param applicationId Application Id
     * @return String subscription status
     * @throws APIManagementException if failed to add subscription details to database
     */
    public String addSubscription(APIIdentifier identifier, String userId, int applicationId)
            throws APIManagementException;

    /**
     * Unsubscribe the specified user from the specified API in the given application
     *
     * @param identifier    APIIdentifier
     * @param userId        id of the user
     * @param applicationId Application Id
     * @throws APIManagementException if failed to add subscription details to database
     */
    public void removeSubscription(APIIdentifier identifier, String userId, int applicationId)
            throws APIManagementException;

    /**
     * Remove a Subscriber
     *
     * @param identifier APIIdentifier
     * @param userId     id of the user
     * @throws APIManagementException if failed to add subscription details to database
     */
    public void removeSubscriber(APIIdentifier identifier, String userId)
            throws APIManagementException;

    /**
     * This method is to update the subscriber.
     *
     * @param identifier    APIIdentifier
     * @param userId        user id
     * @param applicationId Application Id
     * @throws APIManagementException if failed to update subscription
     */
    public void updateSubscriptions(APIIdentifier identifier, String userId, int applicationId)
            throws APIManagementException;

    /**
     * @param identifier Api identifier
     * @param comment comment text
     * @param user Username of the comment author                        
     * @throws APIManagementException if failed to add comment for API
     */
    public void addComment(APIIdentifier identifier, String comment,
                           String user) throws APIManagementException;

    /**
     * @param identifier Api identifier
     * @return Comments
     * @throws APIManagementException if failed to get comments for identifier
     */
    public Comment[] getComments(APIIdentifier identifier) throws APIManagementException;

    /**
     * Adds an application
     *
     * @param application Application
     * @param userId      User Id
     * @throws APIManagementException if failed to add Application
     */
    public String addApplication(Application application, String userId) throws APIManagementException;

    /**
     * Updates the details of the specified user application.
     *
     * @param application Application object containing updated data
     * @throws APIManagementException If an error occurs while updating the application
     */
    public void updateApplication(Application application) throws APIManagementException;

    public void removeApplication(Application application) throws APIManagementException;

    /**
     * Creates a request for getting Approval for Application Registration.
     *
     * @param userId Subsriber name.
     * @param  applicationName of the Application.
     * @param tokenType Token type (PRODUCTION | SANDBOX)
     * @param callbackUrl Callback URL for the Application.
     * @param
     * @throws APIManagementException if failed to applications for given subscriber
     */
    public Map<String,String> requestApprovalForApplicationRegistration(String userId, String applicationName, String tokenType,
                                                            String callbackUrl, String[] allowedDomains, String validityTime)
        throws APIManagementException;

    /**
     * Creates a request for getting Approval for Application Registration.
     *
     * @param userId Subsriber name.
     * @param  applicationName of the Application.
     * @param tokenType Token type (PRODUCTION | SANDBOX)
     * @param jsonString Callback URL for the Application.
     * @param
     * @throws APIManagementException if failed to applications for given subscriber
     */
    public Map<String,Object> requestApprovalForApplicationRegistration(String userId, String applicationName,
                                                                        String tokenType,
                                                                        String callbackUrl, String[] allowedDomains,
                                                                        String validityTime,
                                                                        String jsonString)
            throws APIManagementException;

    /**
     * Creates a request for application update.
     *
     * @param userId Subsriber name.
     * @param  applicationName of the Application.
     * @param tokenType Token type (PRODUCTION | SANDBOX)
     * @param jsonString json String with oAuthApplication parameters.
     * @param
     * @throws APIManagementException if failed to applications for given subscriber
     */
    public Map<String,Object> updateAuthClient(String userId, String applicationName, String tokenType,
                                               String jsonString)
            throws APIManagementException;

    /**
     * Delete oAuth application from Key manager and remove key manager mapping from APIM.
     * @param consumerKey Client id of oAuthApplication.
     * @throws APIManagementException
     */
    public void deleteAuthApplication(String consumerKey)
            throws APIManagementException;


    /**
     * Returns a list of applications for a given subscriber
     *
     * @param subscriber Subscriber
     * @return Applications
     * @throws APIManagementException if failed to applications for given subscriber
     */
    public Application[] getApplications(Subscriber subscriber) throws APIManagementException;

    /**
     * This will return APIM application by giving name and subscriber
     * @param userId APIM subscriber ID.
     * @param ApplicationName APIM application name.
     * @return it will return Application.
     * @throws APIManagementException
     */
    public Application getApplicationsByName(String userId , String ApplicationName) throws
            APIManagementException;

    public Set<SubscribedAPI> getSubscribedIdentifiers(Subscriber subscriber,
                                                       APIIdentifier identifier) throws APIManagementException;
    
    public Set<APIIdentifier> getAPIByConsumerKey(String accessToken) throws APIManagementException;

    public Set<API> searchAPI(String searchTerm, String searchType,String tenantDomain) throws APIManagementException;
    public Map<String,Object> searchPaginatedAPIs(String searchTerm, String searchType,String tenantDomain,int start,int end) throws APIManagementException;
    public int getUserRating(APIIdentifier apiId, String user) throws APIManagementException;

    /**
     * Get a list of published APIs by the given provider.
     *
     * @param providerId , provider id
     * @param loggedUser logged user
     * @param limit Maximum number of results to return. Pass -1 to get all.
     * @return set of API
     * @throws APIManagementException if failed to get set of API
     */
    public Set<API> getPublishedAPIsByProvider(String providerId,String loggedUser, int limit, String apiOwner) throws APIManagementException;/**

     /** Get a list of published APIs by the given provider.
     *
     * @param providerId , provider id
     * @param limit Maximum number of results to return. Pass -1 to get all.
     * @return set of API
     * @throws APIManagementException if failed to get set of API
     */
    public Set<API> getPublishedAPIsByProvider(String providerId, int limit) throws APIManagementException;

    /**
     * Check whether an application access token is already persist in database.
     * @param accessToken
     * @return
     * @throws APIManagementException
     */
    public boolean isApplicationTokenExists(String accessToken) throws APIManagementException;

    /**
     * Add allowed domains for given application which is identified by OAuth consumer key
     * This will directly add allowed domains in to API Manager database
     * @param oAuthConsumerKey OAuth consumer key
     * @return
     * @throws APIManagementException
     */
    public void addAccessAllowDomains(String oAuthConsumerKey, String[] accessAllowDomains)
            throws APIManagementException;

    /**
     * Update exiting access allowing domain list
     * @param accessToken
     * @param accessAllowDomains
     * @throws APIManagementException
     */
    public void updateAccessAllowDomains(String accessToken, String[] accessAllowDomains) throws APIManagementException;
    
    /**
     * Returns a list of Tiers denied for the current user
     *
     * @return Set<String>
     * @throws APIManagementException if failed to get the tiers
     */
    public Set<String> getDeniedTiers()throws APIManagementException;
    
    /**
     * Check whether given Tier is denied for the user
     * @param tierName
     * @return 
     * @throws APIManagementException if failed to get the tiers
     */
    public boolean isTierDeneid(String tierName)throws APIManagementException;


    /**
     * Complete Application Registration process.If the Application registration fails before
     * generating the Access Tokens, this method should be used to resume registration.
     * @param userId Tenant Aware userID
     * @param applicationName Name of the Application
     * @param tokenType Type of the Token (PRODUCTION | SANDBOX)
     * @return a Map containing the details of the OAuth application.
     * @throws APIManagementException if failed to get the tiers
     */
    public Map<String,String> completeApplicationRegistration(String userId, String applicationName, String tokenType) throws APIManagementException;

    /**
     * Returns details of an API information in low profile
     *
     * @param identifier APIIdentifier
     * @return An API object related to the given identifier or null
     * @throws APIManagementException if failed get API from APIIdentifier
     */
    public API getAPIInfo(APIIdentifier identifier) throws APIManagementException;
    
    /**
     * Returns a paginated list of all APIs in given Status. If a given API has multiple APIs,
     * only the latest version will be included
     * in this list.
     * @param tenantDomain tenant domain
     * @param start starting number
     * @param end ending number
     * @return set of API
     * @throws APIManagementException if failed to API set
     */
    public Map<String,Object> getAllPaginatedAPIsByStatus(String tenantDomain,int start,int end, String Status) throws APIManagementException;

    /**
     * Revokes the oldAccessToken generating a new one.
     *
     * @param oldAccessToken          Token to be revoked
     * @param clientId                Consumer Key for the Application
     * @param clientSecret            Consumer Secret for the Application
     * @param validityTime            Desired Validity time for the token
     * @param accessAllowDomainsArray List of domains that this access token should be allowed to.
     * @param jsonInput               Additional parameters if Authorization server needs any.
     * @return Details of the newly generated Access Token.
     * @throws APIManagementException
     */
    AccessTokenInfo renewAccessToken(String oldAccessToken, String clientId, String clientSecret, String validityTime,
                                     String[] accessAllowDomainsArray, String jsonInput) throws APIManagementException;
}

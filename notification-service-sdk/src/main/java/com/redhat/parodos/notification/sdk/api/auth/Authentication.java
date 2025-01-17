/*
 * Parodos Notification Service API
 * This is the API documentation for the Parodos Notification Service. It provides operations to send out and check notification. The endpoints are secured with oAuth2/OpenID and cannot be accessed without a valid token.
 *
 * The version of the OpenAPI document: v1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.redhat.parodos.notification.sdk.api.auth;

import com.redhat.parodos.notification.sdk.api.Pair;
import com.redhat.parodos.notification.sdk.api.ApiException;

import java.net.URI;
import java.util.Map;
import java.util.List;

public interface Authentication {

	/**
	 * Apply authentication settings to header and query params.
	 * @param queryParams List of query parameters
	 * @param headerParams Map of header parameters
	 * @param cookieParams Map of cookie parameters
	 * @param payload HTTP request body
	 * @param method HTTP method
	 * @param uri URI
	 * @throws ApiException if failed to update the parameters
	 */
	void applyToParams(List<Pair> queryParams, Map<String, String> headerParams, Map<String, String> cookieParams,
			String payload, String method, URI uri) throws ApiException;

}

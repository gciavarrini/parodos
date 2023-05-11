package com.redhat.parodos.tasks.kubeapi;

import java.io.IOException;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.generic.dynamic.DynamicKubernetesApi;
import io.kubernetes.client.util.generic.dynamic.DynamicKubernetesObject;

class KubernetesApiImpl implements KubernetesApi {

	@Override
	public DynamicKubernetesObject get(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			String namespace, String name) throws ApiException, IOException {
		return new DynamicKubernetesApi(apiGroup, apiVersion, kindPluralName, client).get(namespace, name)
				.throwsApiException().getObject();
	}

	@Override
	public void create(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			DynamicKubernetesObject obj) throws ApiException, IOException {
		new DynamicKubernetesApi(apiGroup, apiVersion, kindPluralName, client).create(obj).throwsApiException();
	}

	@Override
	public void update(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			DynamicKubernetesObject obj) throws ApiException, IOException {
		new DynamicKubernetesApi(apiGroup, apiVersion, kindPluralName, client).update(obj).throwsApiException();
	}

}

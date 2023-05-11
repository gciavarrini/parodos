package com.redhat.parodos.tasks.kubeapi;

import java.io.IOException;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.generic.dynamic.DynamicKubernetesObject;

interface KubernetesApi {

	DynamicKubernetesObject get(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			String namespace, String name) throws ApiException, IOException;

	void create(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			DynamicKubernetesObject obj) throws ApiException, IOException;

	void update(ApiClient client, String apiGroup, String apiVersion, String kindPluralName,
			DynamicKubernetesObject obj) throws ApiException, IOException;

}

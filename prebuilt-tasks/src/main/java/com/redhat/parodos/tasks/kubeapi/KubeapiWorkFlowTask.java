package com.redhat.parodos.tasks.kubeapi;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.redhat.parodos.workflow.exception.MissingParameterException;
import com.redhat.parodos.workflow.parameter.WorkParameter;
import com.redhat.parodos.workflow.parameter.WorkParameterType;
import com.redhat.parodos.workflow.task.BaseWorkFlowTask;
import com.redhat.parodos.workflow.task.enums.WorkFlowTaskOutput;
import com.redhat.parodos.workflows.work.DefaultWorkReport;
import com.redhat.parodos.workflows.work.WorkContext;
import com.redhat.parodos.workflows.work.WorkReport;
import com.redhat.parodos.workflows.work.WorkStatus;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.generic.dynamic.DynamicKubernetesObject;
import io.kubernetes.client.util.generic.dynamic.Dynamics;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * KubeapiWorkFlowTask A task for get/create/update Kubernetes resource User provides
 * kubeconfig and then each execution performs the action See 'getWorkFlowTaskParameters'
 * for detailed parameters list
 */
@Slf4j
public class KubeapiWorkFlowTask extends BaseWorkFlowTask {

	static public enum OperationType {

		CREATE("create"), UPDATE("update"), GET("get");

		private String strOp;

		private OperationType(String strOp) {
			this.strOp = strOp;
		}

		public String toString() {
			return this.strOp;
		}

	};

	private KubernetesApi api;

	public KubeapiWorkFlowTask() {
		this.api = new KubernetesApiImpl();
	}

	KubeapiWorkFlowTask(KubernetesApi api, String beanName) {
		this.api = api;
		this.setBeanName(beanName);
	}

	@Override
	public @NonNull List<WorkParameter> getWorkFlowTaskParameters() {
		LinkedList<WorkParameter> params = new LinkedList<>();
		params.add(WorkParameter.builder().key("kubeconfig-json").type(WorkParameterType.TEXT).optional(true)
				.description("kubeconfig in json format").build());
		params.add(WorkParameter.builder().key("api-group").type(WorkParameterType.TEXT).optional(false)
				.description("API group of resource").build());
		params.add(WorkParameter.builder().key("api-version").type(WorkParameterType.TEXT).optional(false)
				.description("API version of resource").build());
		params.add(WorkParameter.builder().key("kind-plural-name").type(WorkParameterType.TEXT).optional(false)
				.description("Plural name of the resource kind. E.g. crontabs").build());
		params.add(WorkParameter.builder().key("operation").type(WorkParameterType.TEXT).optional(false)
				.description("Operation type/name. Can be one of " + Arrays.toString(OperationType.values())).build());
		params.add(WorkParameter.builder().key("resource-json").type(WorkParameterType.TEXT).optional(true)
				.description("The JSON to be used in create and update operations").build());
		params.add(WorkParameter.builder().key("resource-name").type(WorkParameterType.TEXT).optional(true)
				.description("Name of resource for get operation").build());
		params.add(WorkParameter.builder().key("resource-namespace").type(WorkParameterType.TEXT).optional(true)
				.description("Namespace of resource for get operation").build());
		params.add(WorkParameter.builder().key("work-ctx-key").type(WorkParameterType.TEXT).optional(true)
				.description("In get operation the result is stored in WorkContext with the provided key").build());
		return params;
	}

	@Override
	public @NonNull List<WorkFlowTaskOutput> getWorkFlowTaskOutputs() {
		return List.of(WorkFlowTaskOutput.OTHER);
	}

	@Override
	public WorkReport execute(WorkContext workContext) {
		String operation = "";

		try {
			// Get the required parameters
			String kubeconfigJson = getOptionalParameterValue(workContext, "kubeconfig-json", null);
			String apiGroup = getRequiredParameterValue(workContext, "api-group");
			String apiVersion = getRequiredParameterValue(workContext, "api-version");
			String kindPluralName = getRequiredParameterValue(workContext, "kind-plural-name");
			operation = getRequiredParameterValue(workContext, "operation");

			ApiClient client;
			if (kubeconfigJson != null) {
				String kubeConfig = new YAMLMapper().writeValueAsString(new ObjectMapper().readTree(kubeconfigJson));
				client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new StringReader(kubeConfig))).build();
			}
			else {
				client = ClientBuilder.defaultClient();
			}
			OperationType operationType = OperationType.valueOf(operation.toUpperCase());

			switch (operationType) {
				case UPDATE:
					update(workContext, client, apiGroup, apiVersion, kindPluralName);
					break;
				case CREATE:
					create(workContext, client, apiGroup, apiVersion, kindPluralName);
					break;
				case GET:
					get(workContext, client, apiGroup, apiVersion, kindPluralName);
					break;
			}
		}
		catch (MissingParameterException | ApiException | IllegalArgumentException | IOException e) {
			log.error("Kubeapi task failed for operation " + operation, e);
			return new DefaultWorkReport(WorkStatus.FAILED, workContext, e);
		}

		return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
	}

	private void get(WorkContext ctx, ApiClient client, String apiGroup, String apiVersion, String kindPluralName)
			throws MissingParameterException, ApiException, IOException {
		String resourceName = getRequiredParameterValue(ctx, "resource-name");
		String resourceNamespace = getRequiredParameterValue(ctx, "resource-namespace");
		String workCtxKey = getRequiredParameterValue(ctx, "work-ctx-key");

		DynamicKubernetesObject obj = api.get(client, apiGroup, apiVersion, kindPluralName, resourceNamespace,
				resourceName);
		String resourceJson = obj.getRaw().toString();
		ctx.put(workCtxKey, resourceJson);
	}

	private void create(WorkContext ctx, ApiClient client, String apiGroup, String apiVersion, String kindPluralName)
			throws MissingParameterException, ApiException, IOException {
		String resourceJson = getRequiredParameterValue(ctx, "resource-json");
		DynamicKubernetesObject obj = Dynamics.newFromJson(resourceJson);
		api.create(client, apiGroup, apiVersion, kindPluralName, obj);
	}

	private void update(WorkContext ctx, ApiClient client, String apiGroup, String apiVersion, String kindPluralName)
			throws MissingParameterException, ApiException, IOException {
		String resourceJson = getRequiredParameterValue(ctx, "resource-json");
		DynamicKubernetesObject newObj = Dynamics.newFromJson(resourceJson);
		String resourceNamespace = newObj.getMetadata().getNamespace();
		String resourceName = newObj.getMetadata().getName();
		DynamicKubernetesObject currObj = api.get(client, apiGroup, apiVersion, kindPluralName, resourceNamespace,
				resourceName);
		newObj.setMetadata(currObj.getMetadata());
		api.update(client, apiGroup, apiVersion, kindPluralName, newObj);
	}

}

package com.redhat.parodos.examples.vmmigration.task;

import com.redhat.parodos.examples.vmmigration.constants.Constants;
import com.redhat.parodos.examples.vmmigration.dto.common.Metadata;
import com.redhat.parodos.examples.vmmigration.dto.plan.*;
import com.redhat.parodos.tasks.kubeapi.KubeapiWorkFlowTask;
import com.redhat.parodos.workflow.task.infrastructure.BaseInfrastructureWorkFlowTask;
import com.redhat.parodos.workflows.work.WorkContext;
import com.redhat.parodos.workflows.work.WorkReport;
import io.kubernetes.client.openapi.JSON;
import lombok.extern.slf4j.Slf4j;

import static com.redhat.parodos.examples.vmmigration.dto.common.Constants.*;
import static com.redhat.parodos.tasks.kubeapi.KubeapiWorkFlowTask.*;

@Slf4j
public class CreatePlanWorkfFlowTask extends BaseInfrastructureWorkFlowTask {

	private final static KubeapiWorkFlowTask kubeTask = new KubeapiWorkFlowTask();

	@Override
	public WorkReport execute(WorkContext workContext) {
		log.info("Start CreatePlanWorkfFlow...");

		// create Nework providers

		Network source = Network.builder().name("vmware").namespace(Constants.TEST_NAMESPACE).build();
		Network destination = Network.builder().name("openshift").namespace(Constants.TEST_NAMESPACE).build();

		// create provider
		SpecProvider provider = SpecProvider.builder().source(source).destination(destination).build();

		// create the VM slice
		SpecVM[] vms = new SpecVM[] { SpecVM.builder().hooks(new Object[0]).name("mtv-rhel8-sanity").build() };

		// create the map
		Network network = Network.builder().name("vmware-n9lxw").namespace(Constants.TEST_NAMESPACE).build();
		Network storage = Network.builder().name("vmware-qfs8w").namespace(Constants.TEST_NAMESPACE).build();
		SpecMap map = SpecMap.builder().network(network).storage(storage).build();

		// create the spec
		Spec spec = Spec.builder().targetNamespace("demo24").description("").archived(false).map(map).provider(provider)
				.vms(vms).warm(false).build();

		// create Plan
		Plan plan = Plan.builder().apiVersion(GROUP_VERSION).kind(PLAN_KIND_SINGULAR)
				.metadata(Metadata.builder().name("vmware-demo").namespace(Constants.TEST_NAMESPACE).build()).spec(spec)
				.build();
		JSON json = new JSON();
		var strPlan = json.serialize(plan);
		// create json for the vsphere provider
		var wc = new WorkContext();
		System.out.printf("json is %s\n", strPlan);
		wc.put(Constants.API_VERSION_PARAMETER_NAME, plan.getApiVersion());
		wc.put(Constants.API_GROUP_PARAMETER_NAME, "");
		wc.put(Constants.KIND_PLURAL_NAME_PARAMETER_NAME, PLAN_KIND_PLURAL);
		wc.put(Constants.OPERATION_PARAMETER_NAME, OperationType.CREATE.toString());
		wc.put(Constants.RESOURCE_NAME_PARAMETER_NAME, plan.getMetadata().getName());
		wc.put(Constants.RESOURCE_NAMESPACE_PARAMETER_NAME, plan.getMetadata().getNamespace());
		wc.put(Constants.RESOURCE_JSON_PARAMETER_NAME, strPlan);

		return kubeTask.execute(wc);
	}

}

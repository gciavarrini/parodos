package com.redhat.parodos.examples.vmmigration.task;

import com.redhat.parodos.examples.vmmigration.constants.Constants;
import com.redhat.parodos.examples.vmmigration.dto.migration.Migration;
import com.redhat.parodos.examples.vmmigration.dto.migration.Plan;
import com.redhat.parodos.examples.vmmigration.dto.migration.Spec;
import com.redhat.parodos.tasks.kubeapi.KubeapiWorkFlowTask;
import com.redhat.parodos.tasks.kubeapi.KubeapiWorkFlowTask.OperationType;
import com.redhat.parodos.workflow.task.infrastructure.BaseInfrastructureWorkFlowTask;
import com.redhat.parodos.workflows.work.WorkContext;
import com.redhat.parodos.workflows.work.WorkReport;
import io.kubernetes.client.openapi.JSON;
import lombok.extern.slf4j.Slf4j;

import static com.redhat.parodos.examples.vmmigration.dto.common.Constants.GROUP_VERSION;
import static com.redhat.parodos.examples.vmmigration.dto.common.Constants.MIGRATION_KIND_PLURAL;
import static com.redhat.parodos.examples.vmmigration.dto.common.Constants.MIGRATION_KIND_SINGULAR;

@Slf4j
public class CreateMigrationWorkFlowTask extends BaseInfrastructureWorkFlowTask {

	@Override
	public WorkReport execute(WorkContext workContext) {
		log.info("Start CreateMigrationWorkFlow...");

		Plan plan = Plan.builder().name("vmware-demo").namespace(Constants.TEST_NAMESPACE).build();

		Spec spec = Spec.builder().plan(plan).build();
		Migration migration = Migration.builder().apiVersion(GROUP_VERSION).kind(MIGRATION_KIND_SINGULAR).spec(spec)
				.build();

		JSON json = new JSON();
		String strMigration = json.serialize(migration);

		WorkContext wc = new WorkContext();
		wc.put(Constants.API_VERSION_PARAMETER_NAME, migration.getApiVersion());
		wc.put(Constants.API_GROUP_PARAMETER_NAME, migration.getKind());
		wc.put(Constants.KIND_PLURAL_NAME_PARAMETER_NAME, MIGRATION_KIND_PLURAL);
		wc.put(Constants.OPERATION_PARAMETER_NAME, OperationType.CREATE.toString());
		wc.put(Constants.RESOURCE_NAME_PARAMETER_NAME, migration.getMetadata().getName());
		wc.put(Constants.RESOURCE_NAMESPACE_PARAMETER_NAME, migration.getMetadata().getNamespace());
		wc.put(Constants.RESOURCE_JSON_PARAMETER_NAME, strMigration);

		KubeapiWorkFlowTask kubeTask = new KubeapiWorkFlowTask();
		return kubeTask.execute(wc);

	}

}

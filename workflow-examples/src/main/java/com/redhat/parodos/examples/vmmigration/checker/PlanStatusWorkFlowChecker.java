package com.redhat.parodos.examples.vmmigration.checker;

import com.google.gson.JsonParseException;
import com.redhat.parodos.examples.vmmigration.constants.Constants;
import com.redhat.parodos.examples.vmmigration.dto.plan.*;
import com.redhat.parodos.tasks.kubeapi.*;
import com.redhat.parodos.workflow.task.checker.BaseWorkFlowCheckerTask;
import com.redhat.parodos.workflows.work.DefaultWorkReport;
import com.redhat.parodos.workflows.work.WorkContext;
import com.redhat.parodos.workflows.work.WorkReport;
import com.redhat.parodos.workflows.work.WorkStatus;
import io.kubernetes.client.openapi.*;
import lombok.extern.slf4j.Slf4j;

import static com.redhat.parodos.examples.vmmigration.dto.common.Constants.*;
import static com.redhat.parodos.tasks.kubeapi.KubeapiWorkFlowTask.OperationType;

@Slf4j
public class PlanStatusWorkFlowChecker extends BaseWorkFlowCheckerTask {

	private String planName;

	private String planNamespace;

	private final static KubeapiWorkFlowTask kubeTask = new KubeapiWorkFlowTask();

	public PlanStatusWorkFlowChecker(String planName, String planNamespace) {
		super();
		this.planName = planName;
		this.planNamespace = planNamespace;
	}

	@Override
	public WorkReport checkWorkFlowStatus(WorkContext workContext) {
		log.info("Start PlanStatusWorkFlowChecker...");
		try {
			var wc = new WorkContext();
			wc.put(Constants.API_VERSION_PARAMETER_NAME, API_VERSION);
			wc.put(Constants.API_GROUP_PARAMETER_NAME, API_GROUP);
			wc.put(Constants.KIND_PLURAL_NAME_PARAMETER_NAME, PLAN_KIND_PLURAL);
			wc.put(Constants.OPERATION_PARAMETER_NAME, OperationType.GET.toString());
			wc.put(Constants.RESOURCE_NAME_PARAMETER_NAME, planName);
			wc.put(Constants.RESOURCE_NAMESPACE_PARAMETER_NAME, planNamespace);
			wc.put(Constants.RETURN_JSON_PARAMETER_NAME, "plan_json");
			var report = kubeTask.execute(wc);
			if (report.getStatus().equals(WorkStatus.COMPLETED)) {
				Object obj = wc.get("plan_json");
				JSON json = new JSON();
				Plan plan = json.deserialize((String) obj, Plan.class);
				for (Condition condition : plan.getStatus().getConditions()) {
					if (condition.getType() == "Ready" && condition.getStatus() == "True") {
						return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
					}
				}
			}
		}
		catch (JsonParseException ex) {
			log.error("Failed to unmarshal plan " + planNamespace + "/" + planName, ex.getMessage());
		}
		return new DefaultWorkReport(WorkStatus.FAILED, workContext);
	}

}

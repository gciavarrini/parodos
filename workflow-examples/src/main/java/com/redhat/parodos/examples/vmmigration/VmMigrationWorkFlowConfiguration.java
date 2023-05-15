package com.redhat.parodos.examples.vmmigration;

import java.util.List;

import com.redhat.parodos.examples.vmmigration.checker.MigrationStatusWorkFlowChecker;
import com.redhat.parodos.examples.vmmigration.checker.PlanStatusWorkFlowChecker;
import com.redhat.parodos.examples.vmmigration.task.*;
import com.redhat.parodos.workflow.annotation.Checker;
import com.redhat.parodos.workflow.annotation.Infrastructure;
import com.redhat.parodos.workflow.consts.WorkFlowConstants;
import com.redhat.parodos.workflows.workflow.SequentialFlow;
import com.redhat.parodos.workflows.workflow.WorkFlow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:git.properties")
public class VmMigrationWorkFlowConfiguration {

	@Bean
	public CreatePlanWorkfFlowTask createPlanFlow(
			@Qualifier("planStatusCheckerWorkflow") WorkFlow planStatusCheckerWorkflow) {
		CreatePlanWorkfFlowTask planWorkflow = new CreatePlanWorkfFlowTask();
		planWorkflow.setWorkFlowCheckers(List.of(planStatusCheckerWorkflow));
		return planWorkflow;
	}

	@Bean
	CreateMigrationWorkFlowTask createMigrationFlow(
			@Qualifier("migrationStatusCheckerWorkflow") WorkFlow migrationStatusCheckerWorkflow) {
		CreateMigrationWorkFlowTask migrationWorkflow = new CreateMigrationWorkFlowTask();
		migrationWorkflow.setWorkFlowCheckers(List.of(migrationStatusCheckerWorkflow));
		return migrationWorkflow;
	}

	@Bean
	PlanStatusWorkFlowChecker planStatusWorkFlowChecker() {
		return new PlanStatusWorkFlowChecker("vmware-demo", "demo24");
	}

	@Bean
	MigrationStatusWorkFlowChecker migrationStatusWorkFlowChecker() {
		return new MigrationStatusWorkFlowChecker("vmware-demo", "demo24");
	}

	@Bean
	@Checker(cronExpression = "*/5 * * * * ?")
	public WorkFlow planStatusCheckerWorkflow(
			@Qualifier("planStatusWorkFlowChecker") PlanStatusWorkFlowChecker planStatusWorkFlowChecker) {
		// @formatter:off
		return SequentialFlow.Builder
				.aNewSequentialFlow()
				.named("planStatusCheckerTask")
				.execute(planStatusWorkFlowChecker)
				.build();
		// @formatter:on
	}

	@Bean
	@Checker(cronExpression = "*/5 * * * * ?")
	public WorkFlow migrationStatusCheckerWorkflow(
			@Qualifier("migrationStatusWorkFlowChecker") MigrationStatusWorkFlowChecker migrationStatusWorkFlowChecker) {
		// @formatter:off
		return SequentialFlow.Builder
				.aNewSequentialFlow()
				.named("migrationStatusCheckerWorkflow")
				.execute(migrationStatusWorkFlowChecker)
				.build();
		// @formatter:on
	}

	@Bean(name = "vmMigrationWorkFlow" + WorkFlowConstants.INFRASTRUCTURE_WORKFLOW)
	@Infrastructure()
	WorkFlow vmMigrationWorkFlow(@Qualifier("createPlanFlow") CreatePlanWorkfFlowTask createPlanWorkFlow,
			@Qualifier("createMigrationFlow") CreateMigrationWorkFlowTask createMigrationWorkFlow) {
		return SequentialFlow.Builder.aNewSequentialFlow()
				.named("vmMigrationWorkFlow" + WorkFlowConstants.INFRASTRUCTURE_WORKFLOW).execute(createPlanWorkFlow)
				.then(createMigrationWorkFlow).build();
	}

}

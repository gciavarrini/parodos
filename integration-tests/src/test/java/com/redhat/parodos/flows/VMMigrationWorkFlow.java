package com.redhat.parodos.flows;

import java.util.Arrays;

import com.redhat.parodos.flows.base.BaseIntegrationTest;
import com.redhat.parodos.sdk.api.WorkflowApi;
import com.redhat.parodos.sdk.invoker.ApiException;
import com.redhat.parodos.sdk.model.ProjectResponseDTO;
import com.redhat.parodos.sdk.model.WorkFlowRequestDTO;
import com.redhat.parodos.sdk.model.WorkFlowResponseDTO;
import com.redhat.parodos.sdk.model.WorkFlowResponseDTO.WorkStatusEnum;
import com.redhat.parodos.sdk.model.WorkFlowStatusResponseDTO;
import com.redhat.parodos.sdk.model.WorkRequestDTO;
import com.redhat.parodos.sdkutils.SdkUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Gloria Ciavarrini (Github: gciavarrini)
 */
@Slf4j
public class VMMigrationWorkFlow extends BaseIntegrationTest {

	private static final String projectName = "vm-migration";

	private static final String projectDescription = "vm migration project";

	@Test
	public void runMigrationWorkFlow() throws ApiException, InterruptedException {
		log.info("Running vm migration flow");

		ProjectResponseDTO testProject = SdkUtils.getProjectAsync(apiClient, projectName, projectDescription);

		// // GET simpleSequentialWorkFlow DEFINITIONS
		// WorkflowDefinitionApi workflowDefinitionApi = new WorkflowDefinitionApi();
		// List<WorkFlowDefinitionResponseDTO> workFlowDefinitions = workflowDefinitionApi
		// .getWorkFlowDefinitions("vmMigrationWorkFlow" +
		// WorkFlowConstants.INFRASTRUCTURE_WORKFLOW);
		// assertEquals(1, workFlowDefinitions.size());

		// // GET WORKFLOW DEFINITION BY Id
		// WorkFlowDefinitionResponseDTO workFlowDefinition = workflowDefinitionApi
		// .getWorkFlowDefinitionById(workFlowDefinitions.get(0).getId());

		// assertNotNull(workFlowDefinition.getId());
		// assertEquals("vmMigrationWorkFlow" + WorkFlowConstants.INFRASTRUCTURE_WORKFLOW,
		// workFlowDefinition.getName());
		// assertEquals(WorkFlowDefinitionResponseDTO.ProcessingTypeEnum.SEQUENTIAL,
		// workFlowDefinition.getProcessingType());
		// assertEquals(WorkFlowType.INFRASTRUCTURE.toString(),
		// workFlowDefinition.getType().toString());

		// assertNotNull(workFlowDefinition.getWorks());
		// assertEquals(2, workFlowDefinition.getWorks().size());
		// assertEquals("createPlanFlow", workFlowDefinition.getWorks().get(0).getName());
		// assertEquals(WorkType.TASK.toString(),
		// workFlowDefinition.getWorks().get(0).getWorkType().toString());

		// assertEquals("createMigrationFlow",
		// workFlowDefinition.getWorks().get(1).getName());
		// assertEquals(WorkType.TASK.toString(),
		// workFlowDefinition.getWorks().get(1).getWorkType().toString());

		// Define WorkRequests
		WorkRequestDTO work1 = new WorkRequestDTO().workName("createPlanFlow");
		WorkRequestDTO work2 = new WorkRequestDTO().workName("createMigrationFlow");

		// Define WorkFlowRequest
		WorkFlowRequestDTO workFlowRequestDTO = new WorkFlowRequestDTO();
		workFlowRequestDTO.setProjectId(testProject.getId());
		workFlowRequestDTO.setWorkFlowName("vmMigrationWorkFlow_INFRASTRUCTURE_WORKFLOW");
		workFlowRequestDTO.setWorks(Arrays.asList(work1, work2));

		WorkflowApi workflowApi = new WorkflowApi();
		log.info("******** Running The Sequence Flow ********");

		WorkFlowResponseDTO workFlowResponseDTO = workflowApi.execute(workFlowRequestDTO);

		assertNotNull(workFlowResponseDTO.getWorkFlowExecutionId());
		assertNotNull(workFlowResponseDTO.getWorkStatus());
		assertEquals(WorkStatusEnum.IN_PROGRESS, workFlowResponseDTO.getWorkStatus());

		WorkFlowStatusResponseDTO workFlowStatusResponseDTO = SdkUtils.waitWorkflowStatusAsync(workflowApi,
				workFlowResponseDTO.getWorkFlowExecutionId());

		assertNotNull(workFlowStatusResponseDTO.getWorkFlowExecutionId());
		assertNotNull(workFlowStatusResponseDTO.getStatus());
		assertEquals(WorkStatusEnum.COMPLETED, workFlowStatusResponseDTO.getStatus());
		log.info("workflow finished successfully with response: {}", workFlowResponseDTO);
		log.info("******** Simple Sequence Flow Completed ********");
	}

}

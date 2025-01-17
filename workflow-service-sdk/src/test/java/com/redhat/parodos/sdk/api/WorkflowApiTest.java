/*
 * Parodos Workflow Service API
 * This is the API documentation for the Parodos Workflow Service. It provides operations to execute assessments to determine infrastructure options (tooling + environments). Also executes infrastructure task workflows to call downstream systems to stand-up an infrastructure option.
 *
 * The version of the OpenAPI document: v1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.redhat.parodos.sdk.api;

import com.redhat.parodos.sdk.invoker.ApiException;
import com.redhat.parodos.sdk.model.WorkFlowCheckerTaskRequestDTO;
import com.redhat.parodos.sdk.model.WorkFlowContextResponseDTO;
import com.redhat.parodos.sdk.model.WorkFlowRequestDTO;
import com.redhat.parodos.sdk.model.WorkFlowResponseDTO;
import com.redhat.parodos.sdk.model.WorkFlowStatusResponseDTO;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;
import java.util.UUID;

/**
 * API tests for WorkflowApi
 */
@Ignore
public class WorkflowApiTest {

	private final WorkflowApi api = new WorkflowApi();

	/**
	 * Executes a workflow
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void executeTest() throws ApiException {
		WorkFlowRequestDTO workFlowRequestDTO = null;
		WorkFlowResponseDTO response = api.execute(workFlowRequestDTO);
		// TODO: test validations
	}

	/**
	 * Returns a workflow status
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getStatusTest() throws ApiException {
		UUID workFlowExecutionId = null;
		WorkFlowStatusResponseDTO response = api.getStatus(workFlowExecutionId);
		// TODO: test validations
	}

	/**
	 * Returns workflow context parameters
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void getWorkflowParametersTest() throws ApiException {
		UUID workFlowExecutionId = null;
		List<String> param = null;
		WorkFlowContextResponseDTO response = api.getWorkflowParameters(workFlowExecutionId, param);
		// TODO: test validations
	}

	/**
	 * Updates a workflow checker task status
	 * @throws ApiException if the Api call fails
	 */
	@Test
	public void updateWorkFlowCheckerTaskStatusTest() throws ApiException {
		UUID workFlowExecutionId = null;
		String workFlowCheckerTaskName = null;
		WorkFlowCheckerTaskRequestDTO workFlowCheckerTaskRequestDTO = null;
		String response = api.updateWorkFlowCheckerTaskStatus(workFlowExecutionId, workFlowCheckerTaskName,
				workFlowCheckerTaskRequestDTO);
		// TODO: test validations
	}

}

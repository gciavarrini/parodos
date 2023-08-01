package com.redhat.parodos.tasks.migrationtoolkit;

import java.util.UUID;

import com.redhat.parodos.workflow.context.WorkContextDelegate;
import com.redhat.parodos.workflow.exception.MissingParameterException;
import com.redhat.parodos.workflows.work.WorkContext;
import com.redhat.parodos.workflows.work.WorkReport;
import com.redhat.parodos.workflows.work.WorkStatus;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.redhat.parodos.tasks.migrationtoolkit.TestConsts.APP_ID;
import static com.redhat.parodos.tasks.migrationtoolkit.TestConsts.APP_NAME;
import static com.redhat.parodos.tasks.migrationtoolkit.TestConsts.REPO_BRANCH;
import static com.redhat.parodos.tasks.migrationtoolkit.TestConsts.REPO_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
public class CreateApplicationTaskTest {

	CreateApplicationTask underTest;

	@Mock
	MTAApplicationClient mockClient;

	WorkContext ctx;

	@BeforeEach
	public void setUp() {
		underTest = new CreateApplicationTask();
		underTest.mtaClient = mockClient;
		ctx = new WorkContext();
	}

	@Test
	@SneakyThrows
	public void missingMandatoryParameters() {
		WorkContextDelegate.write(ctx, WorkContextDelegate.ProcessType.WORKFLOW_EXECUTION,
				WorkContextDelegate.Resource.ID, UUID.randomUUID());
		underTest.preExecute(ctx);
		WorkReport execute = underTest.execute(ctx);

		assertThat(execute.getError(), is(instanceOf(MissingParameterException.class)));
		assertThat(execute.getStatus(), equalTo(WorkStatus.FAILED));
		assertThat(execute.getWorkContext().get("application"), is(nullValue()));
		verify(mockClient, times(0)).create(any());
	}

	@Test
	@SneakyThrows
	public void createFails() {
		when(mockClient.create(any(App.class))).thenReturn(new Result.Failure<>(new Exception("some error from MTA")));
		ctx.put("applicationName", APP_NAME);
		ctx.put("repositoryURL", REPO_URL);
		WorkContextDelegate.write(ctx, WorkContextDelegate.ProcessType.WORKFLOW_EXECUTION,
				WorkContextDelegate.Resource.ID, UUID.randomUUID());
		underTest.preExecute(ctx);
		WorkReport execute = underTest.execute(ctx);

		assertThat(execute.getError(), is(instanceOf(Exception.class)));
		assertThat(execute.getError(), is(instanceOf(Exception.class)));
		assertThat(execute.getStatus(), equalTo(WorkStatus.FAILED));
		assertThat(execute.getWorkContext().get("application"), is(nullValue()));
		verify(mockClient, times(1)).create(any());
	}

	@Test
	@SneakyThrows
	public void createCompletes() {
		ctx.put("applicationName", APP_NAME);
		ctx.put("repositoryURL", REPO_URL);
		ctx.put("branch", REPO_BRANCH);

		when(mockClient.create(any())).thenReturn(
				new Result.Success<>(new App(APP_ID, APP_NAME, new Repository("git", REPO_URL, REPO_BRANCH), null)));
		WorkContextDelegate.write(ctx, WorkContextDelegate.ProcessType.WORKFLOW_EXECUTION,
				WorkContextDelegate.Resource.ID, UUID.randomUUID());
		underTest.preExecute(ctx);
		WorkReport execute = underTest.execute(ctx);

		assertThat(execute.getError(), is(nullValue()));
		assertThat(execute.getStatus(), equalTo(WorkStatus.COMPLETED));
		assertThat(execute.getWorkContext().get("application"),
				equalTo(new App(APP_ID, APP_NAME, new Repository("git", REPO_URL, REPO_BRANCH), null)));

		// 0 is wanted explicitly because it is an empty ID for the server request. (IDs
		// are generated by the server)
		verify(mockClient, times(1)).create(new App(0, APP_NAME, new Repository("git", REPO_URL, REPO_BRANCH), null));
	}

}

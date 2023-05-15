package com.redhat.parodos.examples.vmmigration.dto.migration;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Status {

	private Condition[] conditions;

	private long observedGeneration;

	private OffsetDateTime started;

}

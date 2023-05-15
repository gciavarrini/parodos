package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Status {

	private Condition[] conditions;

	private long observedGeneration;

}

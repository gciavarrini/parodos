package com.redhat.parodos.examples.vmmigration.dto.plan;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Condition {

	private String category;

	private OffsetDateTime lastTransitionTime;

	private String message;

	private String status;

	private String type;

	private Boolean durable;

}

package com.redhat.parodos.examples.vmmigration.dto.plan;

import com.redhat.parodos.examples.vmmigration.dto.common.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Plan {

	private String apiVersion;

	private String kind;

	private Metadata metadata;

	private Spec spec;

	private Status status;

}

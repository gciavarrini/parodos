package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpecProvider {

	private Network destination;

	private Network source;

}

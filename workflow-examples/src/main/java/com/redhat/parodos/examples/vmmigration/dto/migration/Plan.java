package com.redhat.parodos.examples.vmmigration.dto.migration;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Plan {

	private String name;

	private String namespace;

}
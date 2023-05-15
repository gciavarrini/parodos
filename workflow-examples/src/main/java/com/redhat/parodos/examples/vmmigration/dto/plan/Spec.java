package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Spec {

	private boolean archived;

	private String description;

	private SpecMap map;

	private SpecProvider provider;

	private String targetNamespace;

	private SpecVM[] vms;

	private boolean warm;

}

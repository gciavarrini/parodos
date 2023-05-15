package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Network {

	private String name;

	private String namespace;

}

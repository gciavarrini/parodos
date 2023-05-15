package com.redhat.parodos.examples.vmmigration.dto.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Metadata {

	private String name;

	private String namespace;

}

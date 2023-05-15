package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpecVM {

	private Object[] hooks;

	private String name;

}
